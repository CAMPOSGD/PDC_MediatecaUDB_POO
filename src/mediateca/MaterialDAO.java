package mediateca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {


    public boolean insertarLibro(Libro l) {
        String sql = "INSERT INTO libros (codigo, titulo, autor, paginas, editorial, isbn, ubicacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return ejecutarUpdate(sql, l.getCodigo(), l.getTitulo(), l.getAutor(), l.getPaginas(), l.getEditorial(), l.getIsbn(), l.getUbicacion());
    }

    public boolean insertarRevista(Revista r) {
        String sql = "INSERT INTO revistas (codigo, titulo, editorial, periodicidad, fecha, ubicacion) VALUES (?, ?, ?, ?, ?, ?)";
        return ejecutarUpdate(sql, r.getCodigo(), r.getTitulo(), r.getEditorial(), r.getPeriodicidad(), r.getFecha(), r.getUbicacion());
    }

    public boolean insertarCD(CD cd) {
        String sql = "INSERT INTO cds (codigo, titulo, artista, genero, duracion, canciones, ubicacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return ejecutarUpdate(sql, cd.getCodigo(), cd.getTitulo(), cd.getArtista(), cd.getGenero(), cd.getDuracion(), cd.getCanciones(), cd.getUbicacion());
    }

    public boolean insertarTesis(Tesis t) {
        String sql = "INSERT INTO tesis (codigo, titulo, autor, carrera, anio, ubicacion) VALUES (?, ?, ?, ?, ?, ?)";
        return ejecutarUpdate(sql, t.getCodigo(), t.getTitulo(), t.getAutor(), t.getCarrera(), t.getAnio(), t.getUbicacion());
    }

    private boolean ejecutarUpdate(String sql, Object... params) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en DB: " + e.getMessage());
            return false;
        }
    }

    public List<Object[]> consultarTodo(String tabla) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + tabla;
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            int col = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[col];
                for (int i = 0; i < col; i++) fila[i] = rs.getObject(i + 1);
                lista.add(fila);
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return lista;
    }
}