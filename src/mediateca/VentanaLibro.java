package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaLibro extends JFrame {

    private JTextField txtTit = new JTextField(15);
    private JTextField txtAut = new JTextField(15);
    private JTextField txtPag = new JTextField(15);
    private JTextField txtEdi = new JTextField(15);
    private JTextField txtIsbn = new JTextField(15);
    private JTextField txtUbi = new JTextField(15);
    private JTextField txtCantidad = new JTextField(15);

    private JButton btnGuardar = new JButton("Guardar Libro");

    public VentanaLibro() {
        setTitle("Gestión de Libros - UDB");
        setSize(420, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new JLabel("Título:"));
        add(txtTit);

        add(new JLabel("Autor:"));
        add(txtAut);

        add(new JLabel("Páginas:"));
        add(txtPag);

        add(new JLabel("Editorial:"));
        add(txtEdi);

        add(new JLabel("ISBN:"));
        add(txtIsbn);

        add(new JLabel("Ubicación:"));
        add(txtUbi);

        add(new JLabel("Cantidad:"));
        add(txtCantidad);

        add(new JLabel(""));
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                if (txtTit.getText().isEmpty() ||
                        txtAut.getText().isEmpty() ||
                        txtPag.getText().isEmpty() ||
                        txtUbi.getText().isEmpty() ||
                        txtCantidad.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Debe llenar los campos obligatorios.");
                    return;
                }

                int paginas = Integer.parseInt(txtPag.getText());
                int cantidad = Integer.parseInt(txtCantidad.getText());

                if (paginas <= 0) {
                    JOptionPane.showMessageDialog(this, "Las páginas deben ser mayores que cero.");
                    return;
                }

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
                    return;
                }

                Libro l = new Libro(
                        txtTit.getText(),
                        txtAut.getText(),
                        paginas,
                        txtEdi.getText(),
                        txtIsbn.getText(),
                        txtUbi.getText(),
                        cantidad
                );

                if (new MaterialCRUD().insertarLibro(l)) {
                    JOptionPane.showMessageDialog(this, "Libro guardado correctamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo guardar el libro.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Páginas y cantidad deben contener solo números.");
            }
        });
    }
}