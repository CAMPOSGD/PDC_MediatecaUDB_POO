package mediateca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL_SERVIDOR = "jdbc:mysql://127.0.0.1:3306/?useSSL=false&serverTimezone=UTC";
    private static final String URL_BD = "jdbc:mysql://127.0.0.1:3306/mediatecadb?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConexionServidor() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL_SERVIDOR, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexión al servidor: " + e.getMessage());
            return null;
        }
    }

    public static Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL_BD, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexión a la base de datos: " + e.getMessage());
            return null;
        }
    }
}