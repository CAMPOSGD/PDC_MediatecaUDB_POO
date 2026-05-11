package mediateca;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class VentanaConfiguracion extends JFrame {
    private JTextField txtMora = new JTextField(10);
    private JTextField txtLimite = new JTextField(10);
    private JButton btnGuardar = new JButton("Actualizar Configuración");

    public VentanaConfiguracion() {
        setTitle("Configuración del Sistema - UDB");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel(" Mora Diaria ($):")); add(txtMora);
        add(new JLabel(" Límite de Préstamos:")); add(txtLimite);
        add(new JLabel("")); add(btnGuardar);

        cargarValores();

        btnGuardar.addActionListener(e -> {
            String sql = "UPDATE configuracion SET mora_diaria = ?, limite_prestamos = ? WHERE id = 1";
            try (Connection cn = Conexion.getConexion();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setDouble(1, Double.parseDouble(txtMora.getText()));
                ps.setInt(2, Integer.parseInt(txtLimite.getText()));

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Configuración actualizada.");
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: Verifique que sean números válidos.");
            }
        });
    }

    private void cargarValores() {
        try (Connection cn = Conexion.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM configuracion WHERE id = 1")) {
            if (rs.next()) {
                txtMora.setText(String.valueOf(rs.getDouble("mora_diaria")));
                txtLimite.setText(String.valueOf(rs.getInt("limite_prestamos")));
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
}
