package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaCD extends JFrame {

    private JTextField txtTit = new JTextField(15);
    private JTextField txtArt = new JTextField(15);
    private JTextField txtGen = new JTextField(15);
    private JTextField txtDur = new JTextField(15);
    private JTextField txtCan = new JTextField(15);
    private JTextField txtUbi = new JTextField(15);
    private JTextField txtCantidad = new JTextField(15);

    private JButton btnGuardar = new JButton("Guardar CD");

    public VentanaCD() {
        setTitle("Gestión de CDs - UDB");
        setSize(420, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new JLabel("Título:"));
        add(txtTit);

        add(new JLabel("Artista:"));
        add(txtArt);

        add(new JLabel("Género:"));
        add(txtGen);

        add(new JLabel("Duración:"));
        add(txtDur);

        add(new JLabel("Canciones:"));
        add(txtCan);

        add(new JLabel("Ubicación:"));
        add(txtUbi);

        add(new JLabel("Cantidad:"));
        add(txtCantidad);

        add(new JLabel(""));
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                if (txtTit.getText().isEmpty() ||
                        txtArt.getText().isEmpty() ||
                        txtGen.getText().isEmpty() ||
                        txtDur.getText().isEmpty() ||
                        txtCan.getText().isEmpty() ||
                        txtUbi.getText().isEmpty() ||
                        txtCantidad.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.");
                    return;
                }

                int canciones = Integer.parseInt(txtCan.getText());
                int cantidad = Integer.parseInt(txtCantidad.getText());

                if (canciones <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad de canciones debe ser mayor que cero.");
                    return;
                }

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
                    return;
                }

                CD cd = new CD(
                        txtTit.getText(),
                        txtArt.getText(),
                        txtGen.getText(),
                        txtDur.getText(),
                        canciones,
                        txtUbi.getText(),
                        cantidad
                );

                if (new MaterialDAO().insertarCD(cd)) {
                    JOptionPane.showMessageDialog(this, "CD guardado correctamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo guardar el CD.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Canciones y cantidad deben contener solo números.");
            }
        });
    }
}