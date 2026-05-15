package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaRevista extends JFrame {

    private JTextField txtTit = new JTextField(15);
    private JTextField txtEdi = new JTextField(15);
    private JTextField txtPer = new JTextField(15);
    private JTextField txtFec = new JTextField(15);
    private JTextField txtUbi = new JTextField(15);
    private JTextField txtCantidad = new JTextField(15);

    private JButton btnGuardar = new JButton("Guardar Revista");

    public VentanaRevista() {
        setTitle("Gestión de Revistas - UDB");
        setSize(420, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Título:"));
        add(txtTit);

        add(new JLabel("Editorial:"));
        add(txtEdi);

        add(new JLabel("Periodicidad:"));
        add(txtPer);

        add(new JLabel("Fecha:"));
        add(txtFec);

        add(new JLabel("Ubicación:"));
        add(txtUbi);

        add(new JLabel("Cantidad:"));
        add(txtCantidad);

        add(new JLabel(""));
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                if (txtTit.getText().isEmpty() ||
                        txtPer.getText().isEmpty() ||
                        txtFec.getText().isEmpty() ||
                        txtUbi.getText().isEmpty() ||
                        txtCantidad.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Debe llenar los campos obligatorios.");
                    return;
                }

                int cantidad = Integer.parseInt(txtCantidad.getText());

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
                    return;
                }

                Revista r = new Revista(
                        txtTit.getText(),
                        txtEdi.getText(),
                        txtPer.getText(),
                        txtFec.getText(),
                        txtUbi.getText(),
                        cantidad
                );

                if (new MaterialCRUD().insertarRevista(r)) {
                    JOptionPane.showMessageDialog(this, "Revista guardada correctamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo guardar la revista.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cantidad debe contener solo números.");
            }
        });
    }
}