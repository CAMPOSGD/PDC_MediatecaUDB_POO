package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaDevolucion extends JFrame {
    private JTextField txtIdPrestamo = new JTextField(15);
    private JButton btnDevolver = new JButton("Procesar Devolución");

    public VentanaDevolucion() {
        setTitle("Módulo de Devoluciones - UDB");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        add(new JLabel("ID del Préstamo:"));
        add(txtIdPrestamo);
        add(btnDevolver);

        btnDevolver.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdPrestamo.getText());
                PrestamoDAO prestamoDAO = new PrestamoDAO();
                double mora = prestamoDAO.procesarDevolucion(id);

                if (mora >= 0) {
                    if (mora > 0) {
                        JOptionPane.showMessageDialog(this, "Devolución exitosa.\n EL USUARIO DEBE PAGAR: $" + mora);
                    } else {
                        JOptionPane.showMessageDialog(this, "Devolución exitosa sin mora.");
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el préstamo o ya fue devuelto.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID no válido. Ingrese solo números.");
            }
        });
    }
}
