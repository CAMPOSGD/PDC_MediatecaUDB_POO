package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaGestionUsuarios extends JFrame {

    private JTextField txtNombre = new JTextField(15);
    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);

    private JComboBox<String> comboRol = new JComboBox<>(
            new String[]{"Administrador", "Profesor", "Alumno"}
    );

    private JButton btnCrear = new JButton("Crear Usuario");

    public VentanaGestionUsuarios() {
        setTitle("Crear Usuario - Mediateca");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Nombre completo:"));
        add(txtNombre);

        add(new JLabel("Usuario:"));
        add(txtUser);

        add(new JLabel("Contraseña:"));
        add(txtPass);

        add(new JLabel("Rol:"));
        add(comboRol);

        add(new JLabel(""));
        add(btnCrear);

        btnCrear.addActionListener(e -> crearUsuario());
    }

    private void crearUsuario() {
        UsuarioDAO dao = new UsuarioDAO();

        String nombre = txtNombre.getText();
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());
        String rol = comboRol.getSelectedItem().toString();

        if (nombre.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.");
            return;
        }

        if (dao.crearUsuario(user, pass, nombre, rol)) {
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente con rol: " + rol);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error: El usuario ya existe o los datos son inválidos.");
        }
    }
}