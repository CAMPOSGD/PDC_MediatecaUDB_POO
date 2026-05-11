package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {
    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JButton btnLogin = new JButton("Ingresar");

    public VentanaLogin() {
        setTitle("Login - Mediateca Don Bosco");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JPanel p1 = new JPanel(); p1.add(new JLabel("Usuario:")); p1.add(txtUser);
        JPanel p2 = new JPanel(); p2.add(new JLabel("Contraseña:")); p2.add(txtPass);

        add(new JLabel("Acceso al Sistema", SwingConstants.CENTER));
        add(p1);
        add(p2);
        add(btnLogin);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            UsuarioDAO dao = new UsuarioDAO();
            String rolObtenido = dao.login(user, pass);

            if (rolObtenido != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido " + user + " (" + rolObtenido + ")");
                new MenuPrincipal(rolObtenido).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        new UsuarioDAO().inicializarSistema();

        SwingUtilities.invokeLater(() -> {
            new VentanaLogin().setVisible(true);
        });
    }
}