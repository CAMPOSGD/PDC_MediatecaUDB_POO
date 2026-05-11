package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaCD extends JFrame {
    private JTextField txtCod = new JTextField(15), txtTit = new JTextField(15),
            txtArt = new JTextField(15), txtGen = new JTextField(15),
            txtDur = new JTextField(15), txtCan = new JTextField(15),
            txtUbi = new JTextField(15);
    private JButton btnGuardar = new JButton("Guardar CD");

    public VentanaCD() {
        setTitle("Gestión de CDs - UDB");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new JLabel(" Código:")); add(txtCod);
        add(new JLabel(" Título:")); add(txtTit);
        add(new JLabel(" Artista:")); add(txtArt);
        add(new JLabel(" Género:")); add(txtGen);
        add(new JLabel(" Duración:")); add(txtDur);
        add(new JLabel(" Canciones:")); add(txtCan);
        add(new JLabel(" Ubicación:")); add(txtUbi);
        add(new JLabel("")); add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            // Enviamos los 7 parámetros exactos que pide CD.java
            CD cd = new CD(
                    txtCod.getText(),
                    txtTit.getText(),
                    txtArt.getText(),
                    txtGen.getText(),
                    txtDur.getText(),
                    Integer.parseInt(txtCan.getText()),
                    txtUbi.getText()
            );
            if (new MaterialDAO().insertarCD(cd)) {
                JOptionPane.showMessageDialog(this, "✅ Guardado");
                dispose();
            }
        });
    }
}
