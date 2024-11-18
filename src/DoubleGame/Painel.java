package DoubleGame;

import javax.swing.JFrame;

public class Painel extends JFrame {

    private final Jogo jogoMain;

    public Painel() {
        jogoMain = new Jogo();
        setTitle("Lucky Color");
        setResizable(false);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(jogoMain);
    }

    public static void main(String[] args) {
        new Painel();
    }
}
