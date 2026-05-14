package mediateca;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaDevolucion extends JFrame {

    private JComboBox<ItemAlumno> comboAlumno = new JComboBox<>();
    private JComboBox<ItemPrestamo> comboPrestamo = new JComboBox<>();

    private JButton btnDevolver = new JButton("Procesar Devolución");

    public VentanaDevolucion() {
        setTitle("Módulo de Devoluciones - UDB");
        setSize(560, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel(" Usuario Alumno:"));
        add(comboAlumno);

        add(new JLabel(" Material prestado:"));
        add(comboPrestamo);

        add(new JLabel(""));
        add(btnDevolver);

        cargarAlumnosConPrestamos();

        comboAlumno.addActionListener(e -> cargarPrestamosDelAlumno());

        btnDevolver.addActionListener(e -> procesarDevolucion());
    }

    private void cargarAlumnosConPrestamos() {
        comboAlumno.removeAllItems();

        PrestamoDAO dao = new PrestamoDAO();
        List<Object[]> alumnos = dao.obtenerAlumnosConPrestamosActivos();

        if (alumnos.isEmpty()) {
            comboAlumno.addItem(new ItemAlumno("", "No hay alumnos con préstamos activos"));
            comboPrestamo.removeAllItems();
            comboPrestamo.addItem(new ItemPrestamo(0, "No hay préstamos activos", null, null));
            return;
        }

        for (Object[] fila : alumnos) {
            String username = fila[0].toString();
            String nombre = fila[1].toString();

            comboAlumno.addItem(new ItemAlumno(username, nombre));
        }

        cargarPrestamosDelAlumno();
    }

    private void cargarPrestamosDelAlumno() {
        comboPrestamo.removeAllItems();

        ItemAlumno alumno = (ItemAlumno) comboAlumno.getSelectedItem();

        if (alumno == null || alumno.getUsername().isEmpty()) {
            comboPrestamo.addItem(new ItemPrestamo(0, "No hay préstamos activos", null, null));
            return;
        }

        PrestamoDAO dao = new PrestamoDAO();
        List<Object[]> prestamos = dao.obtenerPrestamosActivosPorAlumno(alumno.getUsername());

        if (prestamos.isEmpty()) {
            comboPrestamo.addItem(new ItemPrestamo(0, "No hay préstamos activos", null, null));
            return;
        }

        for (Object[] fila : prestamos) {
            int id = Integer.parseInt(fila[0].toString());
            String codigoMaterial = fila[1].toString();
            Object fechaSalida = fila[2];
            Object fechaEsperada = fila[3];

            comboPrestamo.addItem(new ItemPrestamo(id, codigoMaterial, fechaSalida, fechaEsperada));
        }
    }

    private void procesarDevolucion() {
        ItemPrestamo prestamo = (ItemPrestamo) comboPrestamo.getSelectedItem();

        if (prestamo == null || prestamo.getId() == 0) {
            JOptionPane.showMessageDialog(this, "No hay préstamo activo seleccionado.");
            return;
        }

        PrestamoDAO prestamoDAO = new PrestamoDAO();
        double mora = prestamoDAO.procesarDevolucion(prestamo.getId());

        if (mora >= 0) {
            if (mora > 0) {
                JOptionPane.showMessageDialog(this,
                        "Devolución exitosa.\nEl usuario debe pagar mora de: $" + mora);
            } else {
                JOptionPane.showMessageDialog(this, "Devolución exitosa sin mora.");
            }

            dispose();

        } else {
            JOptionPane.showMessageDialog(this, prestamoDAO.getMensajeError());
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

    private static class ItemPrestamo {
        private int id;
        private String codigoMaterial;
        private Object fechaSalida;
        private Object fechaEsperada;

        public ItemPrestamo(int id, String codigoMaterial, Object fechaSalida, Object fechaEsperada) {
            this.id = id;
            this.codigoMaterial = codigoMaterial;
            this.fechaSalida = fechaSalida;
            this.fechaEsperada = fechaEsperada;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            if (id == 0) {
                return codigoMaterial;
            }

            return "Préstamo #" + id + " - " + codigoMaterial
                    + " | Salida: " + fechaSalida
                    + " | Esperada: " + fechaEsperada;
        }
    }
}