package mediateca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MenuPrincipal extends JFrame {

    private String rolUsuario;

    private JTable tablaMateriales;
    private DefaultTableModel modeloMateriales;
    private JComboBox<String> comboCategorias;

    public MenuPrincipal(String rol) {
        this.rolUsuario = rol;

        setTitle("Sistema Mediateca UDB - Bienvenido: " + rol);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Colegio Amigos De Don Bosco", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JTabbedPane pestañas = new JTabbedPane();

        if (rolUsuario.equals("Administrador")) {
            pestañas.addTab("Ingresos", crearPanelIngresos());
            pestañas.addTab("Búsqueda", crearPanelBusqueda());
            pestañas.addTab("Préstamos", crearPanelPrestamos());
            pestañas.addTab("Usuarios", crearPanelGestionUsuarios());

        } else if (rolUsuario.equals("Profesor")) {
            pestañas.addTab("Ingresos", crearPanelIngresos());
            pestañas.addTab("Búsqueda", crearPanelBusqueda());
            pestañas.addTab("Préstamos", crearPanelPrestamos());
            pestañas.addTab("Usuarios", crearPanelUsuariosProfesor());

        } else if (rolUsuario.equals("Alumno")) {
            pestañas.addTab("Búsqueda", crearPanelBusqueda());
        }

        add(pestañas, BorderLayout.CENTER);

        JButton btnSalir = new JButton("Cerrar Sesión");
        btnSalir.addActionListener(e -> {
            dispose();
            new VentanaLogin().setVisible(true);
        });

        add(btnSalir, BorderLayout.SOUTH);
    }

    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(220, 65));
        boton.setFont(new Font("Arial", Font.BOLD, 15));
        boton.setFocusPainted(false);
        return boton;
    }

    private JPanel crearPanelIngresos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JButton btnLibros = crearBotonMenu("Ingresar Libros");
        JButton btnTesis = crearBotonMenu("Ingresar Tesis");
        JButton btnCDs = crearBotonMenu("Ingresar CDs");
        JButton btnRevistas = crearBotonMenu("Ingresar Revistas");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnLibros, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(btnTesis, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(btnCDs, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(btnRevistas, gbc);

        btnLibros.addActionListener(e -> new VentanaLibro().setVisible(true));
        btnTesis.addActionListener(e -> new VentanaTesis().setVisible(true));
        btnCDs.addActionListener(e -> new VentanaCD().setVisible(true));
        btnRevistas.addActionListener(e -> new VentanaRevista().setVisible(true));

        return panel;
    }

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelFiltro = new JPanel();

        JLabel lblCategoria = new JLabel("Seleccione Categoría:");
        String[] categorias = {"libros", "revistas", "cds", "tesis"};
        comboCategorias = new JComboBox<>(categorias);

        JButton btnCargar = new JButton("Ver Inventario");

        panelFiltro.add(lblCategoria);
        panelFiltro.add(comboCategorias);
        panelFiltro.add(btnCargar);

        modeloMateriales = new DefaultTableModel();
        tablaMateriales = new JTable(modeloMateriales);

        panel.add(panelFiltro, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaMateriales), BorderLayout.CENTER);

        btnCargar.addActionListener(e -> cargarDatosMateriales());

        return panel;
    }

    private void cargarDatosMateriales() {
        String tablaSeleccionada = comboCategorias.getSelectedItem().toString();

        MaterialDAO dao = new MaterialDAO();
        List<Object[]> datos = dao.consultarTodo(tablaSeleccionada);

        modeloMateriales.setRowCount(0);
        modeloMateriales.setColumnCount(0);

        if (tablaSeleccionada.equals("libros")) {
            modeloMateriales.setColumnIdentifiers(new String[]{
                    "Código", "Título", "Autor", "Páginas", "Editorial", "ISBN", "Ubicación", "Cantidad", "Disponibles"
            });

        } else if (tablaSeleccionada.equals("revistas")) {
            modeloMateriales.setColumnIdentifiers(new String[]{
                    "Código", "Título", "Editorial", "Periodicidad", "Fecha", "Ubicación", "Cantidad", "Disponibles"
            });

        } else if (tablaSeleccionada.equals("cds")) {
            modeloMateriales.setColumnIdentifiers(new String[]{
                    "Código", "Título", "Artista", "Género", "Duración", "Canciones", "Ubicación", "Cantidad", "Disponibles"
            });

        } else if (tablaSeleccionada.equals("tesis")) {
            modeloMateriales.setColumnIdentifiers(new String[]{
                    "Código", "Título", "Autor", "Carrera", "Año", "Ubicación", "Cantidad", "Disponibles"
            });
        }

        if (datos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros en " + tablaSeleccionada);
            return;
        }

        for (Object[] fila : datos) {
            modeloMateriales.addRow(fila);
        }
    }

    private JPanel crearPanelPrestamos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JButton btnPrestamo = crearBotonMenu("Realizar Préstamo");
        JButton btnDevolucion = crearBotonMenu("Procesar Devolución");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(btnPrestamo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(btnDevolucion, gbc);

        btnPrestamo.addActionListener(e -> new VentanaPrestamo().setVisible(true));
        btnDevolucion.addActionListener(e -> new VentanaDevolucion().setVisible(true));

        if (rolUsuario.equals("Administrador")) {
            JButton btnConfig = crearBotonMenu("Configuración de Mora");

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            panel.add(btnConfig, gbc);

            btnConfig.addActionListener(e -> new VentanaConfiguracion().setVisible(true));
        }

        return panel;
    }

    private JPanel crearPanelGestionUsuarios() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JButton btnCrearUsuario = crearBotonMenu("Crear Usuario");
        JButton btnResetPassword = crearBotonMenu("Restablecer Contraseña");
        JButton btnCambiarRol = crearBotonMenu("Cambiar Rol");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnCrearUsuario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(btnResetPassword, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(btnCambiarRol, gbc);

        btnCrearUsuario.addActionListener(e -> new VentanaGestionUsuarios().setVisible(true));
        btnResetPassword.addActionListener(e -> new VentanaRestablecerPassword().setVisible(true));
        btnCambiarRol.addActionListener(e -> new VentanaCambiarRol().setVisible(true));

        return panel;
    }

    private JPanel crearPanelUsuariosProfesor() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JButton btnResetPassword = crearBotonMenu("Restablecer Contraseña");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnResetPassword, gbc);

        btnResetPassword.addActionListener(e -> new VentanaRestablecerPassword().setVisible(true));

        return panel;
    }
}