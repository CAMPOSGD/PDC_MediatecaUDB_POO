package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaCambiarRol extends JFrame {

    private JTextField txtUser = new JTextField(15);

    private JComboBox<String> comboRol = new JComboBox<>(
            new String[]{"Administrador", "Profesor", "Alumno"}
    );

    private JButton btnCambiarRol = new JButton("Cambiar Rol");

    public VentanaCambiarRol() {
        setTitle("Cambiar Rol - Mediateca");
        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Usuario:"));
        add(txtUser);

        add(new JLabel("Nuevo rol:"));
        add(comboRol);

        add(new JLabel(""));
        add(btnCambiarRol);

        btnCambiarRol.addActionListener(e -> cambiarRol());
    }

    private void cambiarRol() {
        UsuarioCRUD dao = new UsuarioCRUD();

        String user = txtUser.getText();
        String rol = comboRol.getSelectedItem().toString();

        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el usuario.");
            return;
        }

        if (dao.cambiarRol(user, rol)) {
            JOptionPane.showMessageDialog(this, "Rol actualizado correctamente para " + user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cambiar el rol. Verifique que el usuario exista.");
        }
    }
}