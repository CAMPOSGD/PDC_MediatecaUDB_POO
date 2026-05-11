package mediateca;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PrestamoDAO {

    public boolean tieneMora(String username) {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE username = ? AND estado = 'Activo' AND fecha_devolucion_esperada < CURDATE()";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return false;
    }

    public boolean registrarPrestamo(String codigo, String username) {
        if (tieneMora(username)) return false;
        String sql = "INSERT INTO prestamos (codigo_material, username, fecha_salida, fecha_devolucion_esperada) "
                + "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 3 DAY))";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public double procesarDevolucion(int idPrestamo) {
        double moraTotal = 0;
        String sqlSelect = "SELECT fecha_devolucion_esperada FROM prestamos WHERE id = ? AND estado = 'Activo'";
        String sqlUpdate = "UPDATE prestamos SET fecha_entrega_real = CURDATE(), mora_pagada = ?, estado = 'Devuelto' WHERE id = ?";

        try (Connection cn = Conexion.getConexion()) {
            // 1. Calcular días de retraso
            PreparedStatement psSel = cn.prepareStatement(sqlSelect);
            psSel.setInt(1, idPrestamo);
            ResultSet rs = psSel.executeQuery();

            if (rs.next()) {
                Date fechaEsperada = rs.getDate("fecha_devolucion_esperada");
                LocalDate fEsperada = fechaEsperada.toLocalDate();
                LocalDate fHoy = LocalDate.now();

                long diasRetraso = ChronoUnit.DAYS.between(fEsperada, fHoy);

                if (diasRetraso > 0) {
                    moraTotal = diasRetraso * 0.25;
                }

                PreparedStatement psUpd = cn.prepareStatement(sqlUpdate);
                psUpd.setDouble(1, moraTotal);
                psUpd.setInt(2, idPrestamo);
                psUpd.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error en devolución: " + e.getMessage());
            return -1;
        }
        return moraTotal;
    }
}
