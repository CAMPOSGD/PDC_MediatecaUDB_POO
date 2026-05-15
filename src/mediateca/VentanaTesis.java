package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaTesis extends JFrame {

    private JTextField txtTit = new JTextField(15);
    private JTextField txtAut = new JTextField(15);
    private JTextField txtCar = new JTextField(15);
    private JTextField txtAnio = new JTextField(15);
    private JTextField txtUbi = new JTextField(15);
    private JTextField txtCantidad = new JTextField(15);

    private JButton btnGuardar = new JButton("Guardar Tesis");

    public VentanaTesis() {
        setTitle("Gestión de Tesis - UDB");
        setSize(420, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Título:"));
        add(txtTit);

        add(new JLabel("Autor:"));
        add(txtAut);

        add(new JLabel("Carrera:"));
        add(txtCar);

        add(new JLabel("Año:"));
        add(txtAnio);

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
                        txtCar.getText().isEmpty() ||
                        txtAnio.getText().isEmpty() ||
                        txtUbi.getText().isEmpty() ||
                        txtCantidad.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.");
                    return;
                }

                int cantidad = Integer.parseInt(txtCantidad.getText());

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
                    return;
                }

                Tesis t = new Tesis(
                        txtTit.getText(),
                        txtAut.getText(),
                        txtCar.getText(),
                        txtAnio.getText(),
                        txtUbi.getText(),
                        cantidad
                );

                if (new MaterialCRUD().insertarTesis(t)) {
                    JOptionPane.showMessageDialog(this, "Tesis guardada correctamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo guardar la tesis.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cantidad debe contener solo números.");
            }
        });
    }
}