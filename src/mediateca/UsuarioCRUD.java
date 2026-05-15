package mediateca;

import java.sql.*;

public class UsuarioCRUD {

    public void inicializarSistema() {
        try (Connection cn = Conexion.getConexionServidor()) {
            if (cn == null) {
                System.out.println("No se pudo conectar con el servidor de base de datos.");
                return;
            }

            try (Statement st = cn.createStatement()) {

                st.executeUpdate("CREATE DATABASE IF NOT EXISTS mediatecadb");

                st.executeUpdate("USE mediatecadb");

                // usuarios
                st.executeUpdate("CREATE TABLE IF NOT EXISTS usuarios ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "username VARCHAR(50) UNIQUE NOT NULL, "
                        + "password VARCHAR(50) NOT NULL, "
                        + "nombre_completo VARCHAR(100) NOT NULL, "
                        + "rol ENUM('Administrador', 'Profesor', 'Alumno') NOT NULL)");

                // libros
                st.executeUpdate("CREATE TABLE IF NOT EXISTS libros ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "codigo VARCHAR(20) UNIQUE, "
                        + "titulo VARCHAR(100) NOT NULL, "
                        + "autor VARCHAR(100) NOT NULL, "
                        + "paginas INT NOT NULL, "
                        + "editorial VARCHAR(100), "
                        + "isbn VARCHAR(50), "
                        + "ubicacion VARCHAR(100) NOT NULL, "
                        + "cantidad INT NOT NULL, "
                        + "disponibles INT NOT NULL)");

                // tesis
                st.executeUpdate("CREATE TABLE IF NOT EXISTS tesis ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "codigo VARCHAR(20) UNIQUE, "
                        + "titulo VARCHAR(100) NOT NULL, "
                        + "autor VARCHAR(100) NOT NULL, "
                        + "carrera VARCHAR(100), "
                        + "anio VARCHAR(20), "
                        + "ubicacion VARCHAR(100) NOT NULL, "
                        + "cantidad INT NOT NULL, "
                        + "disponibles INT NOT NULL)");

                // cd
                st.executeUpdate("CREATE TABLE IF NOT EXISTS cds ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "codigo VARCHAR(20) UNIQUE, "
                        + "titulo VARCHAR(100) NOT NULL, "
                        + "artista VARCHAR(100), "
                        + "genero VARCHAR(50), "
                        + "duracion VARCHAR(50), "
                        + "canciones INT, "
                        + "ubicacion VARCHAR(100) NOT NULL, "
                        + "cantidad INT NOT NULL, "
                        + "disponibles INT NOT NULL)");

                // revistas
                st.executeUpdate("CREATE TABLE IF NOT EXISTS revistas ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "codigo VARCHAR(20) UNIQUE, "
                        + "titulo VARCHAR(100) NOT NULL, "
                        + "editorial VARCHAR(100), "
                        + "periodicidad VARCHAR(50), "
                        + "fecha VARCHAR(50), "
                        + "ubicacion VARCHAR(100) NOT NULL, "
                        + "cantidad INT NOT NULL, "
                        + "disponibles INT NOT NULL)");

                // configuracion
                st.executeUpdate("CREATE TABLE IF NOT EXISTS configuracion ("
                        + "id INT PRIMARY KEY, "
                        + "mora_diaria DOUBLE NOT NULL, "
                        + "limite_prestamos INT NOT NULL)");

                // préstamos
                st.executeUpdate("CREATE TABLE IF NOT EXISTS prestamos ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "codigo_material VARCHAR(20) NOT NULL, "
                        + "username VARCHAR(50) NOT NULL, "
                        + "fecha_salida DATE NOT NULL, "
                        + "fecha_devolucion_esperada DATE NOT NULL, "
                        + "fecha_entrega_real DATE, "
                        + "mora_pagada DOUBLE DEFAULT 0, "
                        + "estado VARCHAR(20) DEFAULT 'Activo')");

                st.executeUpdate("INSERT IGNORE INTO configuracion "
                        + "(id, mora_diaria, limite_prestamos) "
                        + "VALUES (1, 0.25, 3)");

                st.executeUpdate("INSERT IGNORE INTO usuarios "
                        + "(username, password, nombre_completo, rol) "
                        + "VALUES ('admin', '1234', 'Administrador del Sistema', 'Administrador')");

                System.out.println("Usuario admin creado o ya existente.");
            }

        } catch (SQLException e) {
            System.out.println("Error en inicializacion: " + e.getMessage());
        }
    }

    public String login(String user, String pass) {
        String sql = "SELECT rol FROM usuarios WHERE username = ? AND password = ?";

        try (Connection cn = Conexion.getConexion()) {
            if (cn == null) {
                return null;
            }

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("rol");
            }

        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }

        return null;
    }

    public boolean restablecerPassword(String username, String nuevaPass) {
        String sql = "UPDATE usuarios SET password = ? WHERE username = ?";

        try (Connection cn = Conexion.getConexion()) {
            if (cn == null) {
                return false;
            }

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nuevaPass);
            ps.setString(2, username);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al restablecer contraseña: " + e.getMessage());
            return false;
        }
    }

    public boolean crearUsuario(String username, String password, String nombreCompleto, String rol) {
        String sql = "INSERT INTO usuarios (username, password, nombre_completo, rol) VALUES (?, ?, ?, ?)";

        try (Connection cn = Conexion.getConexion()) {
            if (cn == null) {
                return false;
            }

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, nombreCompleto);
            ps.setString(4, rol);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean cambiarRol(String username, String nuevoRol) {
        String sql = "UPDATE usuarios SET rol = ? WHERE username = ?";

        try (Connection cn = Conexion.getConexion()) {
            if (cn == null) {
                return false;
            }

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nuevoRol);
            ps.setString(2, username);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cambiar rol: " + e.getMessage());
            return false;
        }
    }
}