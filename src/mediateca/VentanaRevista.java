package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaRevista extends JFrame {
    private JTextField txtCod = new JTextField(15), txtTit = new JTextField(15),
            txtEdi = new JTextField(15), txtPer = new JTextField(15),
            txtFec = new JTextField(15), txtUbi = new JTextField(15);
    private JButton btnGuardar = new JButton("Guardar Revista");

    public VentanaRevista() {
        setTitle("Gestión de Revistas - UDB");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel(" Código:")); add(txtCod);
        add(new JLabel(" Título:")); add(txtTit);
        add(new JLabel(" Editorial:")); add(txtEdi);
        add(new JLabel(" Periodicidad:")); add(txtPer);
        add(new JLabel(" Fecha:")); add(txtFec);
        add(new JLabel(" Ubicación:")); add(txtUbi);
        add(new JLabel("")); add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            Revista r = new Revista(
                    txtCod.getText(),
                    txtTit.getText(),
                    txtEdi.getText(),
                    txtPer.getText(),
                    txtFec.getText(),
                    txtUbi.getText()
            );
            if (new MaterialDAO().insertarRevista(r)) {
                JOptionPane.showMessageDialog(this, "✅ Guardado");
                dispose();
            }
        });
    }
}