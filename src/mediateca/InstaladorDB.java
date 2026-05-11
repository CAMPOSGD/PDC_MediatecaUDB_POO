package mediateca;

import java.sql.*;

public class InstaladorDB {
    public static void main(String[] args) {
        try (Connection cn = Conexion.getConexion();
             Statement st = cn.createStatement()) {

            System.out.println("Creando tablas para la Mediateca UDB...");

            // 1. Libros
            st.executeUpdate("CREATE TABLE IF NOT EXISTS libros ("
                    + "codigoId VARCHAR(20) PRIMARY KEY, titulo VARCHAR(150), "
                    + "ubicacionFisica VARCHAR(100), ejemplaresTotales INT, "
                    + "autor VARCHAR(100), numPaginas INT, editorial VARCHAR(100), "
                    + "isbn VARCHAR(20), anioPublicacion INT)");

            // 2. Revistas
            st.executeUpdate("CREATE TABLE IF NOT EXISTS revistas ("
                    + "codigoId VARCHAR(20) PRIMARY KEY, titulo VARCHAR(150), "
                    + "issn VARCHAR(20), editorial VARCHAR(100), periodicidad VARCHAR(50), "
                    + "numEdicion INT, fechaPublicacion INT, tematica VARCHAR(100), "
                    + "idioma VARCHAR(50), paisOrigen VARCHAR(50))");

            // 3. Tesis
            st.executeUpdate("CREATE TABLE IF NOT EXISTS tesis ("
                    + "codigoId VARCHAR(20) PRIMARY KEY, titulo VARCHAR(200), "
                    + "autor VARCHAR(100), facultad VARCHAR(100), carrera VARCHAR(100), "
                    + "anioGraduacion INT)");

            // 4. Cds
            st.executeUpdate("CREATE TABLE IF NOT EXISTS cds ("
                    + "codigoId VARCHAR(20) PRIMARY KEY, titulo VARCHAR(150), "
                    + "artista VARCHAR(100), genero VARCHAR(50), duracion VARCHAR(20), "
                    + "numCanciones INT)");

            System.out.println("¡Todas las tablas creadas con éxito!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
