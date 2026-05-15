package mediateca;

import javax.swing.*;
import java.awt.*;


public class VentanaLogin extends JFrame {
    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JButton btnLogin = new JButton("Ingresar");

    public VentanaLogin() {
        setTitle("Login - Mediateca Don Bosco");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Acceso al Sistema", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(25, 0, 20, 0));

        JPanel panelCentro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentro.add(lblUsuario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCentro.add(txtUser, gbc);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentro.add(lblPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCentro.add(txtPass, gbc);

        JPanel panelBoton = new JPanel(new BorderLayout());
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelBoton.add(btnLogin, BorderLayout.CENTER);

        add(lblTitulo, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            UsuarioCRUD dao = new UsuarioCRUD();
            String rolObtenido = dao.login(user, pass);

            if (rolObtenido != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido " + user);
                new MenuPrincipal(rolObtenido).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        new UsuarioCRUD().inicializarSistema();

        SwingUtilities.invokeLater(() -> {
            new VentanaLogin().setVisible(true);
        });
    }
}