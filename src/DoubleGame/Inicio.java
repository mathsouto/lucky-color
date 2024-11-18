package DoubleGame;

import javax.swing.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio extends JFrame implements ActionListener {
    private JButton btnJogar;

    public Inicio() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Lucky Color");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(51, 51, 51)); // Definir a cor de fundo da JFrame

        JPanel panel = new JPanel();
        panel.setBackground(new Color(51, 51, 51));
        panel.setPreferredSize(new Dimension(300, 395));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        ImageIcon icon = new ImageIcon(("src/img/logo.png"));
        JLabel label= new JLabel(icon);
        panel.add(label);

        btnJogar = new JButton("JOGAR");
        btnJogar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnJogar.addActionListener(this);
        panel.add(btnJogar);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnJogar) {
            new Painel().setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Inicio().setVisible(true);
        });
    }
}
