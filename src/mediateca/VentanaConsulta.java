package mediateca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaConsulta extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<String> comboCategorias;

    public VentanaConsulta() {
        setTitle("Consulta de Inventario - Mediateca UDB");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelFiltro = new JPanel();
        panelFiltro.add(new JLabel("Seleccione Categoría:"));
        String[] categorias = {"libros", "revistas", "cds", "tesis"};
        comboCategorias = new JComboBox<>(categorias);
        panelFiltro.add(comboCategorias);

        JButton btnCargar = new JButton("Ver Inventario");
        panelFiltro.add(btnCargar);
        add(panelFiltro, BorderLayout.NORTH);

        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnCargar.addActionListener(e -> cargarDatos());
    }

    private void cargarDatos() {
        String tablaSeleccionada = comboCategorias.getSelectedItem().toString();
        MaterialDAO dao = new MaterialDAO();
        List<Object[]> datos = dao.consultarTodo(tablaSeleccionada);

        modelo.setRowCount(0);
        modelo.setColumnCount(0);

        if (datos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros en " + tablaSeleccionada);
            return;
        }

        if (tablaSeleccionada.equals("libros")) {
            modelo.setColumnIdentifiers(new String[]{"Código", "Título", "Autor", "Páginas", "Editorial", "ISBN", "Ubicación"});
        } else if (tablaSeleccionada.equals("revistas")) {
            modelo.setColumnIdentifiers(new String[]{"Código", "Título", "Editorial", "Periodicidad", "Fecha", "Ubicación"});
        } else if (tablaSeleccionada.equals("cds")) {
            modelo.setColumnIdentifiers(new String[]{"Código", "Título", "Artista", "Género", "Duración", "Canciones", "Ubicación"});
        } else if (tablaSeleccionada.equals("tesis")) {
            modelo.setColumnIdentifiers(new String[]{"Código", "Título", "Autor", "Carrera", "Año", "Ubicación"});
        }

        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
    }
}
