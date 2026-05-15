package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaRestablecerPassword extends JFrame {

    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JButton btnReset = new JButton("Restablecer Contraseña");

    public VentanaRestablecerPassword() {
        setTitle("Restablecer Contraseña - Mediateca");
        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Usuario:"));
        add(txtUser);

        add(new JLabel("Nueva contraseña:"));
        add(txtPass);

        add(new JLabel(""));
        add(btnReset);

        btnReset.addActionListener(e -> restablecerPassword());
    }

    private void restablecerPassword() {
        UsuarioCRUD dao = new UsuarioCRUD();

        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese usuario y nueva contraseña.");
            return;
        }

        if (dao.restablecerPassword(user, pass)) {
            JOptionPane.showMessageDialog(this, "Contraseña restablecida correctamente para " + user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "El usuario no existe.");
        }
    }
}