package mediateca;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PrestamoCRUD {

    private String mensajeError = "";

    public String getMensajeError() {
        return mensajeError;
    }

    public List<Object[]> obtenerMaterialesDisponibles(String tipoContenido) {
        List<Object[]> materiales = new ArrayList<>();

        String tabla = "";

        if (tipoContenido.equals("Libro")) {
            tabla = "libros";
        } else if (tipoContenido.equals("Revista")) {
            tabla = "revistas";
        } else if (tipoContenido.equals("CD")) {
            tabla = "cds";
        } else if (tipoContenido.equals("Tesis")) {
            tabla = "tesis";
        } else {
            return materiales;
        }

        String sql = "SELECT codigo, titulo, disponibles FROM " + tabla
                + " WHERE disponibles > 0 ORDER BY codigo";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[3];

                fila[0] = rs.getString("codigo");
                fila[1] = rs.getString("titulo");
                fila[2] = rs.getInt("disponibles");

                materiales.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar materiales disponibles: " + e.getMessage());
        }

        return materiales;
    }

    public List<Object[]> obtenerAlumnos() {
        List<Object[]> alumnos = new ArrayList<>();

        String sql = "SELECT username, nombre_completo FROM usuarios "
                + "WHERE rol = 'Alumno' "
                + "ORDER BY username";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[2];

                fila[0] = rs.getString("username");
                fila[1] = rs.getString("nombre_completo");

                alumnos.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar alumnos: " + e.getMessage());
        }

        return alumnos;
    }

    public boolean usuarioExiste(String username) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        }

        return false;
    }

    public boolean tieneMora(String username) {
        String sql = "SELECT COUNT(*) FROM prestamos "
                + "WHERE username = ? "
                + "AND estado = 'Activo' "
                + "AND fecha_devolucion_esperada < CURDATE()";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al validar mora: " + e.getMessage());
        }

        return false;
    }

    private int obtenerLimitePrestamos() {
        String sql = "SELECT limite_prestamos FROM configuracion WHERE id = 1";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("limite_prestamos");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener límite de préstamos: " + e.getMessage());
        }

        return 3;
    }

    private double obtenerMoraDiaria() {
        String sql = "SELECT mora_diaria FROM configuracion WHERE id = 1";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("mora_diaria");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener mora diaria: " + e.getMessage());
        }

        return 0.25;
    }

    private int contarPrestamosActivos(String username) {
        String sql = "SELECT COUNT(*) FROM prestamos "
                + "WHERE username = ? "
                + "AND estado = 'Activo'";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al contar préstamos activos: " + e.getMessage());
        }

        return 0;
    }

    public boolean registrarPrestamo(String codigo, String username) {
        mensajeError = "";

        codigo = codigo.trim();
        username = username.trim();

        if (!usuarioExiste(username)) {
            mensajeError = "No se pudo registrar el préstamo.\nEl alumno seleccionado no existe.";
            return false;
        }

        if (tieneMora(username)) {
            mensajeError = "No se pudo registrar el préstamo.\nEl alumno tiene mora pendiente.";
            return false;
        }

        int limitePrestamos = obtenerLimitePrestamos();
        int prestamosActivos = contarPrestamosActivos(username);

        if (prestamosActivos >= limitePrestamos) {
            mensajeError = "No se pudo registrar el préstamo.\n"
                    + "El alumno ya alcanzó el límite de préstamos permitidos.\n\n"
                    + "Préstamos activos: " + prestamosActivos + "\n"
                    + "Límite permitido: " + limitePrestamos;
            return false;
        }

        String tabla = obtenerTablaPorCodigo(codigo);

        if (tabla == null) {
            mensajeError = "No se pudo registrar el préstamo.\nEl código del material no es válido.";
            return false;
        }

        try (Connection cn = Conexion.getConexion()) {
            if (cn == null) {
                mensajeError = "No se pudo registrar el préstamo.\nNo hay conexión con la base de datos.";
                return false;
            }

            cn.setAutoCommit(false);

            try {
                if (!materialExiste(cn, tabla, codigo)) {
                    mensajeError = "No se pudo registrar el préstamo.\nEl material seleccionado no existe.";
                    cn.rollback();
                    return false;
                }

                if (!hayDisponible(cn, tabla, codigo)) {
                    mensajeError = "No se pudo registrar el préstamo.\nEl material seleccionado no tiene ejemplares disponibles.";
                    cn.rollback();
                    return false;
                }

                String sqlPrestamo = "INSERT INTO prestamos "
                        + "(codigo_material, username, fecha_salida, fecha_devolucion_esperada) "
                        + "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 3 DAY))";

                try (PreparedStatement ps = cn.prepareStatement(sqlPrestamo)) {
                    ps.setString(1, codigo);
                    ps.setString(2, username);

                    int filasPrestamo = ps.executeUpdate();

                    if (filasPrestamo == 0) {
                        mensajeError = "No se pudo registrar el préstamo.\nNo se insertó el registro en la tabla de préstamos.";
                        cn.rollback();
                        return false;
                    }
                }

                String sqlActualizarDisponibles = "UPDATE " + tabla + " "
                        + "SET disponibles = disponibles - 1 "
                        + "WHERE codigo = ? AND disponibles > 0";

                try (PreparedStatement ps = cn.prepareStatement(sqlActualizarDisponibles)) {
                    ps.setString(1, codigo);

                    int filasActualizadas = ps.executeUpdate();

                    if (filasActualizadas == 0) {
                        mensajeError = "No se pudo registrar el préstamo.\nNo se pudo descontar el ejemplar disponible.";
                        cn.rollback();
                        return false;
                    }
                }

                cn.commit();
                return true;

            } catch (SQLException e) {
                cn.rollback();
                mensajeError = "No se pudo registrar el préstamo.\nError en base de datos: " + e.getMessage();
                return false;

            } finally {
                cn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            mensajeError = "No se pudo registrar el préstamo.\nError de conexión: " + e.getMessage();
            return false;
        }
    }

    public double procesarDevolucion(int idPrestamo) {
        mensajeError = "";
        double moraTotal = 0;

        String sqlSelect = "SELECT codigo_material, fecha_devolucion_esperada "
                + "FROM prestamos "
                + "WHERE id = ? AND estado = 'Activo'";

        String sqlUpdatePrestamo = "UPDATE prestamos "
                + "SET fecha_entrega_real = CURDATE(), "
                + "mora_pagada = ?, "
                + "estado = 'Devuelto' "
                + "WHERE id = ?";

        try (Connection cn = Conexion.getConexion()) {
            if (cn == null) {
                mensajeError = "No se pudo procesar la devolución.\nNo hay conexión con la base de datos.";
                return -1;
            }

            cn.setAutoCommit(false);

            try {
                String codigoMaterial;
                Date fechaEsperada;

                try (PreparedStatement psSel = cn.prepareStatement(sqlSelect)) {
                    psSel.setInt(1, idPrestamo);

                    ResultSet rs = psSel.executeQuery();

                    if (rs.next()) {
                        codigoMaterial = rs.getString("codigo_material");
                        fechaEsperada = rs.getDate("fecha_devolucion_esperada");
                    } else {
                        mensajeError = "No se encontró un préstamo activo con ese ID.";
                        cn.rollback();
                        return -1;
                    }
                }

                LocalDate fEsperada = fechaEsperada.toLocalDate();
                LocalDate fHoy = LocalDate.now();

                long diasRetraso = ChronoUnit.DAYS.between(fEsperada, fHoy);

                if (diasRetraso > 0) {
                    double moraDiaria = obtenerMoraDiaria();
                    moraTotal = diasRetraso * moraDiaria;
                }

                try (PreparedStatement psUpd = cn.prepareStatement(sqlUpdatePrestamo)) {
                    psUpd.setDouble(1, moraTotal);
                    psUpd.setInt(2, idPrestamo);

                    int filas = psUpd.executeUpdate();

                    if (filas == 0) {
                        mensajeError = "No se pudo actualizar el préstamo.";
                        cn.rollback();
                        return -1;
                    }
                }

                String tabla = obtenerTablaPorCodigo(codigoMaterial);

                if (tabla == null) {
                    mensajeError = "El código del material no corresponde a una tabla válida.";
                    cn.rollback();
                    return -1;
                }

                String sqlUpdateDisponible = "UPDATE " + tabla + " "
                        + "SET disponibles = disponibles + 1 "
                        + "WHERE codigo = ?";

                try (PreparedStatement psDisp = cn.prepareStatement(sqlUpdateDisponible)) {
                    psDisp.setString(1, codigoMaterial);

                    int filas = psDisp.executeUpdate();

                    if (filas == 0) {
                        mensajeError = "No se pudo aumentar el disponible del material.";
                        cn.rollback();
                        return -1;
                    }
                }

                cn.commit();
                return moraTotal;

            } catch (SQLException e) {
                cn.rollback();
                mensajeError = "No se pudo procesar la devolución.\nError en base de datos: " + e.getMessage();
                return -1;

            } finally {
                cn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            mensajeError = "No se pudo procesar la devolución.\nError de conexión: " + e.getMessage();
            return -1;
        }
    }

    private boolean materialExiste(Connection cn, String tabla, String codigo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE codigo = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;
    }

    private boolean hayDisponible(Connection cn, String tabla, String codigo) throws SQLException {
        String sql = "SELECT disponibles FROM " + tabla + " WHERE codigo = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("disponibles") > 0;
            }
        }

        return false;
    }

    private String obtenerTablaPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        codigo = codigo.trim();

        if (codigo.startsWith("Lib-")) {
            return "libros";
        } else if (codigo.startsWith("Tes-")) {
            return "tesis";
        } else if (codigo.startsWith("CD-")) {
            return "cds";
        } else if (codigo.startsWith("Rev-")) {
            return "revistas";
        }

        return null;
    }

    public java.util.List<Object[]> obtenerAlumnosConPrestamosActivos() {
        java.util.List<Object[]> alumnos = new java.util.ArrayList<>();

        String sql = "SELECT DISTINCT u.username, u.nombre_completo "
                + "FROM usuarios u "
                + "INNER JOIN prestamos p ON u.username = p.username "
                + "WHERE p.estado = 'Activo' "
                + "ORDER BY u.username";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getString("username");
                fila[1] = rs.getString("nombre_completo");
                alumnos.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar alumnos con préstamos activos: " + e.getMessage());
        }

        return alumnos;
    }

    public java.util.List<Object[]> obtenerPrestamosActivosPorAlumno(String username) {
        java.util.List<Object[]> prestamos = new java.util.ArrayList<>();

        String sql = "SELECT id, codigo_material, fecha_salida, fecha_devolucion_esperada "
                + "FROM prestamos "
                + "WHERE username = ? "
                + "AND estado = 'Activo' "
                + "ORDER BY id";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("codigo_material");
                fila[2] = rs.getDate("fecha_salida");
                fila[3] = rs.getDate("fecha_devolucion_esperada");
                prestamos.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar préstamos activos: " + e.getMessage());
        }

        return prestamos;
    }
}