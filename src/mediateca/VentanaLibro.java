package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaLibro extends JFrame {
    private JTextField txtCod = new JTextField(15), txtTit = new JTextField(15),
            txtAut = new JTextField(15), txtPag = new JTextField(15),
            txtEdi = new JTextField(15), txtIsbn = new JTextField(15),
            txtUbi = new JTextField(15);
    private JButton btnGuardar = new JButton("Guardar Libro");

    public VentanaLibro() {
        setTitle("Gestión de Libros - UDB");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new JLabel(" Código:")); add(txtCod);
        add(new JLabel(" Título:")); add(txtTit);
        add(new JLabel(" Autor:")); add(txtAut);
        add(new JLabel(" Páginas:")); add(txtPag);
        add(new JLabel(" Editorial:")); add(txtEdi);
        add(new JLabel(" ISBN:")); add(txtIsbn);
        add(new JLabel(" Ubicación:")); add(txtUbi);
        add(new JLabel("")); add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            Libro l = new Libro(
                    txtCod.getText(),
                    txtTit.getText(),
                    txtAut.getText(),
                    Integer.parseInt(txtPag.getText()),
                    txtEdi.getText(),
                    txtIsbn.getText(),
                    txtUbi.getText()
            );
            if (new MaterialDAO().insertarLibro(l)) {
                JOptionPane.showMessageDialog(this, "✅ Guardado");
                dispose();
            }
        });
    }
}
