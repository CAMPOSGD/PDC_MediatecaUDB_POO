package mediateca;

import java.sql.*;

public class UsuarioDAO {

    public void inicializarSistema() {
        try (Connection cn = Conexion.getConexion()) {
            if (cn == null) return;
            try (Statement st = cn.createStatement()) {
                st.executeUpdate("USE MediatecaDB");

                st.executeUpdate("CREATE TABLE IF NOT EXISTS usuarios ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "username VARCHAR(50) UNIQUE NOT NULL, "
                        + "password VARCHAR(50) NOT NULL, "
                        + "nombre_completo VARCHAR(100) NOT NULL, "
                        + "rol ENUM('Administrador', 'Profesor', 'Alumno') NOT NULL)");

                // 2. Tabla Libros
                st.executeUpdate("CREATE TABLE IF NOT EXISTS libros ("
                        + "codigo VARCHAR(50) PRIMARY KEY, titulo VARCHAR(100), autor VARCHAR(100), "
                        + "paginas INT, editorial VARCHAR(100), isbn VARCHAR(50), ubicacion VARCHAR(100))");


                st.executeUpdate("INSERT IGNORE INTO usuarios (username, password, nombre_completo, rol) "
                        + "VALUES ('admin', '1234', 'Jose Benjamin', 'Administrador')");

                // Profesor
                st.executeUpdate("INSERT IGNORE INTO usuarios (username, password, nombre_completo, rol) "
                        + "VALUES ('profe01', '1234', 'Catedratico UDB', 'Profesor')");

                // Alumno
                st.executeUpdate("INSERT IGNORE INTO usuarios (username, password, nombre_completo, rol) "
                        + "VALUES ('estudiante01', '1234', 'Alumno de Ingenieria', 'Alumno')");

                System.out.println("✅ Sistema sincronizado: Usuarios Base Listos.");
            }
        } catch (SQLException e) {
            System.out.println("Error en inicializacion: " + e.getMessage());
        }
    }

    public String login(String user, String pass) {
        String sql = "SELECT rol FROM usuarios WHERE username = ? AND password = ?";
        try (Connection cn = Conexion.getConexion()) {
            if (cn == null) return null;
            Statement st = cn.createStatement();
            st.executeUpdate("USE MediatecaDB");

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("rol");
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return null;
    }

    public boolean restablecerPassword(String username, String nuevaPass) {
        String sql = "UPDATE usuarios SET password = ? WHERE username = ?";
        try (Connection cn = Conexion.getConexion()) {
            if (cn == null) return false;
            Statement st = cn.createStatement();
            st.executeUpdate("USE MediatecaDB");

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nuevaPass);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}

