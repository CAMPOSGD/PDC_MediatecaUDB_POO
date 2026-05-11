package mediateca;

import javax.swing.*;
import java.awt.*;

public class VentanaTesis extends JFrame {
    private JTextField txtCod = new JTextField(15);
    private JTextField txtTit = new JTextField(15);
    private JTextField txtAut = new JTextField(15);
    private JTextField txtCar = new JTextField(15);
    private JTextField txtAnio = new JTextField(15);
    private JTextField txtUbi = new JTextField(15);
    private JButton btnGuardar = new JButton("Guardar Tesis");

    public VentanaTesis() {
        setTitle("Gestión de Tesis - UDB");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel(" Código:")); add(txtCod);
        add(new JLabel(" Título:")); add(txtTit);
        add(new JLabel(" Autor:")); add(txtAut);
        add(new JLabel(" Carrera:")); add(txtCar);
        add(new JLabel(" Año:")); add(txtAnio);
        add(new JLabel(" Ubicación:")); add(txtUbi);
        add(new JLabel("")); add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            Tesis t = new Tesis(txtCod.getText(), txtTit.getText(), txtAut.getText(),
                    txtCar.getText(), txtAnio.getText(), txtUbi.getText());
            MaterialDAO dao = new MaterialDAO();
            if (dao.insertarTesis(t)) {
                JOptionPane.showMessageDialog(this, "Guardado");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });
    }
}