package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaGestionUsuarios extends JFrame {
    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JButton btnReset = new JButton("Restablecer Contraseña");

    public VentanaGestionUsuarios() {
        setTitle("Gestión de Usuarios - Mediateca");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel(" Usuario:")); add(txtUser);
        add(new JLabel(" Nueva Clave:")); add(txtPass);
        add(new JLabel("")); add(btnReset);

        btnReset.addActionListener(e -> {
            UsuarioDAO dao = new UsuarioDAO();
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            if (dao.restablecerPassword(user, pass)) {
                JOptionPane.showMessageDialog(this, "Contraseña restablecida para " + user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "El usuario no existe.");
            }
        });
    }
}
