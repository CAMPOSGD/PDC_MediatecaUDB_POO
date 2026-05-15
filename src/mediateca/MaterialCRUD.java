package mediateca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    public boolean insertarLibro(Libro l) {
        String sql = "INSERT INTO libros "
                + "(titulo, autor, paginas, editorial, isbn, ubicacion, cantidad, disponibles) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return ejecutarInsertConCodigo(sql, "libros", "Lib",
                l.getTitulo(),
                l.getAutor(),
                l.getPaginas(),
                l.getEditorial(),
                l.getIsbn(),
                l.getUbicacion(),
                l.getCantidad(),
                l.getCantidad());
    }

    public boolean insertarRevista(Revista r) {
        String sql = "INSERT INTO revistas "
                + "(titulo, editorial, periodicidad, fecha, ubicacion, cantidad, disponibles) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        return ejecutarInsertConCodigo(sql, "revistas", "Rev",
                r.getTitulo(),
                r.getEditorial(),
                r.getPeriodicidad(),
                r.getFecha(),
                r.getUbicacion(),
                r.getCantidad(),
                r.getCantidad());
    }

    public boolean insertarCD(CD cd) {
        String sql = "INSERT INTO cds "
                + "(titulo, artista, genero, duracion, canciones, ubicacion, cantidad, disponibles) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return ejecutarInsertConCodigo(sql, "cds", "CD",
                cd.getTitulo(),
                cd.getArtista(),
                cd.getGenero(),
                cd.getDuracion(),
                cd.getCanciones(),
                cd.getUbicacion(),
                cd.getCantidad(),
                cd.getCantidad());
    }

    public boolean insertarTesis(Tesis t) {
        String sql = "INSERT INTO tesis "
                + "(titulo, autor, carrera, anio, ubicacion, cantidad, disponibles) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        return ejecutarInsertConCodigo(sql, "tesis", "Tes",
                t.getTitulo(),
                t.getAutor(),
                t.getCarrera(),
                t.getAnio(),
                t.getUbicacion(),
                t.getCantidad(),
                t.getCantidad());
    }

    private boolean ejecutarInsertConCodigo(String sql, String tabla, String prefijo, Object... params) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            int filasInsertadas = ps.executeUpdate();

            if (filasInsertadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    String codigoGenerado = prefijo + "-" + String.format("%05d", idGenerado);

                    String sqlUpdate = "UPDATE " + tabla + " SET codigo = ? WHERE id = ?";

                    try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                        psUpdate.setString(1, codigoGenerado);
                        psUpdate.setInt(2, idGenerado);

                        return psUpdate.executeUpdate() > 0;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en DB: " + e.getMessage());
            return false;
        }

        return false;
    }

    public List<Object[]> consultarTodo(String tabla) {
        List<Object[]> lista = new ArrayList<>();
        String sql;

        if (tabla.equals("libros")) {
            sql = "SELECT codigo, titulo, autor, paginas, editorial, isbn, ubicacion, cantidad, disponibles FROM libros";
        } else if (tabla.equals("revistas")) {
            sql = "SELECT codigo, titulo, editorial, periodicidad, fecha, ubicacion, cantidad, disponibles FROM revistas";
        } else if (tabla.equals("cds")) {
            sql = "SELECT codigo, titulo, artista, genero, duracion, canciones, ubicacion, cantidad, disponibles FROM cds";
        } else if (tabla.equals("tesis")) {
            sql = "SELECT codigo, titulo, autor, carrera, anio, ubicacion, cantidad, disponibles FROM tesis";
        } else {
            return lista;
        }

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            int columnas = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Object[] fila = new Object[columnas];

                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }

                lista.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar materiales: " + e.getMessage());
        }

        return lista;
    }
}