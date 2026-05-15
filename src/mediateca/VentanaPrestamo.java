package mediateca;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrestamo extends JFrame {

    private JComboBox<String> comboTipo = new JComboBox<>(
            new String[]{"Libro", "Revista", "CD", "Tesis"}
    );

    private JComboBox<ItemMaterial> comboMaterial = new JComboBox<>();
    private JTextField txtDisponibles = new JTextField(15);

    private JComboBox<ItemAlumno> comboAlumno = new JComboBox<>();

    private JButton btnPrestar = new JButton("Procesar Préstamo");

    public VentanaPrestamo() {
        setTitle("Módulo de Préstamos - UDB");
        setSize(560, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        txtDisponibles.setEditable(false);

        add(new JLabel(" Tipo de contenido:"));
        add(comboTipo);

        add(new JLabel(" Material:"));
        add(comboMaterial);

        add(new JLabel(" Disponibles:"));
        add(txtDisponibles);

        add(new JLabel(" Usuario Alumno:"));
        add(comboAlumno);

        add(new JLabel(""));
        add(btnPrestar);

        cargarMaterialesDisponibles();
        cargarAlumnos();

        comboTipo.addActionListener(e -> cargarMaterialesDisponibles());
        comboMaterial.addActionListener(e -> mostrarDisponibles());
        btnPrestar.addActionListener(e -> procesarPrestamo());
    }

    private void cargarMaterialesDisponibles() {
        comboMaterial.removeAllItems();
        txtDisponibles.setText("");

        String tipoSeleccionado = comboTipo.getSelectedItem().toString();

        PrestamoCRUD dao = new PrestamoCRUD();
        List<Object[]> materiales = dao.obtenerMaterialesDisponibles(tipoSeleccionado);

        if (materiales.isEmpty()) {
            comboMaterial.addItem(new ItemMaterial("", "No hay materiales disponibles", 0));
            txtDisponibles.setText("0");
            return;
        }

        for (Object[] fila : materiales) {
            String codigo = fila[0].toString();
            String titulo = fila[1].toString();
            int disponibles = Integer.parseInt(fila[2].toString());

            comboMaterial.addItem(new ItemMaterial(codigo, titulo, disponibles));
        }

        mostrarDisponibles();
    }

    private void cargarAlumnos() {
        comboAlumno.removeAllItems();

        PrestamoCRUD dao = new PrestamoCRUD();
        List<Object[]> alumnos = dao.obtenerAlumnos();

        if (alumnos.isEmpty()) {
            comboAlumno.addItem(new ItemAlumno("", "No hay alumnos registrados"));
            return;
        }

        for (Object[] fila : alumnos) {
            String username = fila[0].toString();
            String nombre = fila[1].toString();

            comboAlumno.addItem(new ItemAlumno(username, nombre));
        }
    }

    private void mostrarDisponibles() {
        ItemMaterial item = (ItemMaterial) comboMaterial.getSelectedItem();

        if (item != null) {
            txtDisponibles.setText(String.valueOf(item.getDisponibles()));
        }
    }

    private void procesarPrestamo() {
        ItemMaterial material = (ItemMaterial) comboMaterial.getSelectedItem();
        ItemAlumno alumno = (ItemAlumno) comboAlumno.getSelectedItem();

        if (material == null) {
            JOptionPane.showMessageDialog(this, "No se seleccionó ningún material.");
            return;
        }

        if (material.getCodigo().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay materiales disponibles para prestar.");
            return;
        }

        if (alumno == null || alumno.getUsername().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay alumnos disponibles para seleccionar.");
            return;
        }

        PrestamoCRUD dao = new PrestamoCRUD();

        if (dao.registrarPrestamo(material.getCodigo(), alumno.getUsername())) {
            JOptionPane.showMessageDialog(this, "Préstamo registrado con éxito.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, dao.getMensajeError());
        }
    }

    private static class ItemMaterial {
        private String codigo;
        private String titulo;
        private int disponibles;

        public ItemMaterial(String codigo, String titulo, int disponibles) {
            this.codigo = codigo;
            this.titulo = titulo;
            this.disponibles = disponibles;
        }

        public String getCodigo() {
            return codigo;
        }

        public int getDisponibles() {
            return disponibles;
        }

        @Override
        public String toString() {
            if (codigo.isEmpty()) {
                return titulo;
            }

            return codigo + " - " + titulo;
        }
    }

    private static class ItemAlumno {
        private String username;
        private String nombre;

        public ItemAlumno(String username, String nombre) {
            this.username = username;
            this.nombre = nombre;
        }

        public String getUsername() {
            return username;
        }

        @Override
        public String toString() {
            if (username.isEmpty()) {
                return nombre;
            }

            return username + " - " + nombre;
        }
    }
}