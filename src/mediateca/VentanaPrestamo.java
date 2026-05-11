package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaPrestamo extends JFrame {
    private JTextField txtCodMaterial = new JTextField(15);
    private JTextField txtUserAlumno = new JTextField(15);
    private JButton btnPrestar = new JButton("Procesar Préstamo");

    public VentanaPrestamo() {
        setTitle("Módulo de Préstamos - UDB");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel(" Código Material:")); add(txtCodMaterial);
        add(new JLabel(" Usuario Alumno:")); add(txtUserAlumno);
        add(new JLabel("")); add(btnPrestar);

        btnPrestar.addActionListener(e -> {
            PrestamoDAO dao = new PrestamoDAO();
            if (dao.registrarPrestamo(txtCodMaterial.getText(), txtUserAlumno.getText())) {
                JOptionPane.showMessageDialog(this, "Préstamo registrado con éxito.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Bloqueado: El alumno tiene MORA o los datos son incorrectos.");
            }
        });
    }
}
