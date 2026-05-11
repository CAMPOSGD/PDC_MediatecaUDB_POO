package mediateca;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private String rolUsuario;

    public MenuPrincipal(String rol) {
        this.rolUsuario = rol;

        setTitle("Sistema Mediateca UDB - Bienvenido: " + rol);
        setSize(700, 550); // Aumentamos un poco el tamaño para que quepa todo bien
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Panel de Control - Mediateca Don Bosco", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(5, 2, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnLibros = new JButton("Ingresar Libros");
        JButton btnTesis = new JButton("Ingresar Tesis");
        JButton btnCDs = new JButton("Ingresar CDs");
        JButton btnRevistas = new JButton("Ingresar Revistas");

        JButton btnConsulta = new JButton("🔎 CONSULTAR INVENTARIO COMPLETO");
        btnConsulta.setBackground(new Color(200, 230, 255)); // Un color azul claro para diferenciarlo

        JButton btnPrestamo = new JButton("Realizar Préstamo");
        JButton btnDevolucion = new JButton("Procesar Devolución");

        JButton btnConfig = new JButton("Configuración de Mora");
        JButton btnUsuarios = new JButton("Gestión de Usuarios");

        panelBotones.add(btnLibros);
        panelBotones.add(btnTesis);
        panelBotones.add(btnCDs);
        panelBotones.add(btnRevistas);

        panelBotones.add(btnConsulta);

        if (rolUsuario.equals("Administrador")) {
            panelBotones.add(btnPrestamo);
            panelBotones.add(btnDevolucion);
            panelBotones.add(btnConfig);
            panelBotones.add(btnUsuarios);
        }

        add(panelBotones, BorderLayout.CENTER);


        btnLibros.addActionListener(e -> new VentanaLibro().setVisible(true));
        btnTesis.addActionListener(e -> new VentanaTesis().setVisible(true));
        btnCDs.addActionListener(e -> new VentanaCD().setVisible(true));
        btnRevistas.addActionListener(e -> new VentanaRevista().setVisible(true));

        // ESTE ABRE LA TABLA QUE TE MOSTRARÁ EL LIBRO QUE INGRESASTE
        btnConsulta.addActionListener(e -> new VentanaConsulta().setVisible(true));

        btnPrestamo.addActionListener(e -> new VentanaPrestamo().setVisible(true));
        btnDevolucion.addActionListener(e -> new VentanaDevolucion().setVisible(true));
        btnConfig.addActionListener(e -> new VentanaConfiguracion().setVisible(true));
        btnUsuarios.addActionListener(e -> new VentanaGestionUsuarios().setVisible(true));

        JButton btnSalir = new JButton("Cerrar Sesión");
        btnSalir.addActionListener(e -> {
            dispose();
            new VentanaLogin().setVisible(true);
        });
        add(btnSalir, BorderLayout.SOUTH);
    }
}