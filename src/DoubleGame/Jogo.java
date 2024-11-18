package DoubleGame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Jogo extends JPanel {
    private final JLabel labelPontos;
    private final JLabel labelResultado;
    private final JLabel labelResultadoRodada;
    private final JLabel labelPontuacaoAnterior;
    private final JComboBox<String> comboBoxCor;
    private final JTextField campoAposta;
    private final JButton botaoJogar;
    private final JButton botaoFinalizar;

    private int pontos;

    private final Color[] cores = { Color.BLACK, Color.RED, Color.WHITE };
    private final String[] nomesCores = { "PRETO", "VERMELHO", "BRANCO" };

    private final Color[] coresResultado = new Color[5];

    private boolean estaGirando = false;

    private final Image background;

    public Jogo() {
        pontos = 100;
        
        background = new ImageIcon("src//img//MesaCasino.png").getImage();
        setLayout(null);

        labelPontos = new JLabel("Pontos: " + pontos);
        labelPontos.setFont(new Font("Arial", Font.BOLD, 20));
        labelPontos.setHorizontalAlignment(JLabel.CENTER);
        labelPontos.setForeground(Color.WHITE);

        labelResultado = new JLabel("GOOD LUCKY !!!");
        labelResultado.setFont(new Font("Arial", Font.BOLD, 22));
        labelResultado.setHorizontalAlignment(JLabel.CENTER);
        labelResultado.setForeground(Color.WHITE);

        labelResultadoRodada = new JLabel("");
        labelResultadoRodada.setFont(new Font("Arial", Font.BOLD, 15));
        labelResultadoRodada.setHorizontalAlignment(JLabel.CENTER);
        labelResultadoRodada.setForeground(Color.WHITE);
        
        labelPontuacaoAnterior = new JLabel("Pontuação Anterior: ");
        labelPontuacaoAnterior.setFont(new Font("Arial", Font.BOLD, 16));;
        labelPontuacaoAnterior.setForeground(Color.WHITE);
        labelPontuacaoAnterior.setBounds(440, 10, 400, 30);
        int pontuacaoAnterior = carregarPontuacaoAnterior();
        labelPontuacaoAnterior.setText("Pontuação Anterior: " + pontuacaoAnterior);

        comboBoxCor = new JComboBox<>(nomesCores);
        ((JLabel) comboBoxCor.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

        campoAposta = new JTextField(10);
        campoAposta.setHorizontalAlignment(JTextField.CENTER);

        botaoJogar = new JButton("Jogar");
        botaoJogar.addActionListener(new BotaoJogarListener());
        botaoJogar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botaoFinalizar = new JButton("Salvar");
        botaoFinalizar.addActionListener(new BotaoFinalizarListener());
        botaoFinalizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        labelPontos.setBounds(10, 10, 150, 30);
        labelResultado.setBounds(217, 85, 200, 40);
        labelResultadoRodada.setBounds(217, 105, 200, 50);
        comboBoxCor.setBounds(220, 310, 200, 30);
        campoAposta.setBounds(220, 350, 200, 30);
        botaoJogar.setBounds(270, 390, 100, 30);
        botaoFinalizar.setBounds(280, 425, 80, 30);

        add(botaoFinalizar);
        add(labelPontos);
        add(labelResultado);
        add(labelResultadoRodada);
        add(comboBoxCor);
        add(campoAposta);
        add(botaoJogar);
        add(labelPontuacaoAnterior);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int largura = getWidth();
        int altura = getHeight();

        // Desenha o fundo
        g2.drawImage(background, 0, 0, largura, altura, this);

        int tamanhoQuadrado = 80;
        int tamanhoQuadradoMaior = (int) (tamanhoQuadrado * 1.1);
        int espacamento = 10;
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        int larguraTotal = 5 * tamanhoQuadrado + 4 * espacamento;
        int startX = centroX - (larguraTotal / 2);
        int startY = centroY - (tamanhoQuadradoMaior / 2);

        for (int i = 0; i < 5; i++) {
            int x = startX + i * (tamanhoQuadrado + espacamento);
            int y = startY;

            if (i == 2) {
                x -= (tamanhoQuadradoMaior - tamanhoQuadrado) / 2; // Centraliza o quadrado do meio
                y -= (tamanhoQuadradoMaior - tamanhoQuadrado) / 2; // Centraliza o quadrado do meio
                g2.setColor(coresResultado[i]);
                g2.fillRect(x, y, tamanhoQuadradoMaior, tamanhoQuadradoMaior);
            } else {
                g2.setColor(coresResultado[i]);
                g2.fillRect(x, y, tamanhoQuadrado, tamanhoQuadrado);
            }
        }

        g2.setColor(Color.GRAY);
        g2.drawLine(317, 170, 317, 280);
    }

    private class BotaoFinalizarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nomeArquivo = "pontuacao.txt";
            try (FileWriter writer = new FileWriter(nomeArquivo, true)) {
                writer.write("Pontuação: " + pontos + "\n");
                JOptionPane.showMessageDialog(null, "Pontuação salva com sucesso!");

                labelPontuacaoAnterior.setText("Pontuação Anterior: " + pontos);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar a pontuação.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        }
    }
    
    
    private int carregarPontuacaoAnterior() {
        String nomeArquivo = "pontuacao.txt";
        try (Scanner scanner = new Scanner(new File(nomeArquivo))) {
            int pontuacaoAnterior = 0;
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.startsWith("Pontuação: ")) {
                    String pontuacaoString = linha.substring(11);
                    pontuacaoAnterior = Integer.parseInt(pontuacaoString.trim());
                }
            }
            return pontuacaoAnterior;
        } catch (FileNotFoundException e) {
            return 0; // Retorna 0 se o arquivo não existir ou não puder ser lido
        }
    }


    private class BotaoJogarListener implements ActionListener {
        private final Timer temporizador;
        private int indiceAtual;
        private String corSelecionada;
        private int aposta;

        public BotaoJogarListener() {
            indiceAtual = 0;
            temporizador = new Timer(700, new TemporizadorListener());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            corSelecionada = (String) comboBoxCor.getSelectedItem();

            try {
                aposta = Integer.parseInt(campoAposta.getText());
            } catch (NumberFormatException ex) {
                mostrarDialogoErro("Digite um valor válido para fazer a aposta");
                return;
            }

            if (aposta <= 0 || aposta > pontos) {
                mostrarDialogoErro("Você não tem pontos suficientes");
                return;
            }

            if (estaGirando) {
                return;
            }

            pontos -= aposta;
            labelPontos.setText("Pontos: " + pontos);

            labelResultado.setText("...");
            labelResultadoRodada.setText("");
            labelResultadoRodada.setForeground(Color.BLACK);

            indiceAtual = 0;
            temporizador.start();
            estaGirando = true;
            botaoJogar.setEnabled(false);
        }

        private class TemporizadorListener implements ActionListener {
            private final Random aleatorio = new Random();

            @Override
            public void actionPerformed(ActionEvent e) {
                indiceAtual++;
                if (indiceAtual >= 10) {
                    temporizador.stop();

                    for (int i = 0; i < 5; i++) {
                        coresResultado[i] = obterCorAleatoria();
                    }

                    String nomeCorResultado = nomesCores[obterIndiceCor(coresResultado[2])];
                    labelResultado.setText(nomeCorResultado);

                    if (nomeCorResultado.equals(corSelecionada)) {
                        int multiplicador = nomeCorResultado.equals("BRANCO") ? 14 : 2;
                        pontos += aposta * multiplicador;
                        labelResultadoRodada.setText("GANHOU");
                        labelResultadoRodada.setForeground(Color.GREEN);
                    } else {
                        labelResultadoRodada.setText("PERDEU");
                        labelResultadoRodada.setForeground(Color.RED);
                    }

                    labelPontos.setText("Pontos: " + pontos);

                    estaGirando = false;
                    botaoJogar.setEnabled(true);
                } else {
                    for (int i = 0; i < 5; i++) {
                        coresResultado[i] = obterCorAleatoria();
                    }
                }

                repaint();
            }

            private Color obterCorAleatoria() {
                int indiceAleatorio = aleatorio.nextInt(55);
                if (indiceAleatorio < 25) {
                    return cores[0];
                } else if (indiceAleatorio < 50) {
                    return cores[1];
                } else {
                    return cores[2];
                }
            }

            private int obterIndiceCor(Color cor) {
                for (int i = 0; i < cores.length; i++) {
                    if (cores[i].equals(cor)) {
                        return i;
                    }
                }
                return -1;
            }
        }
    }

    private void mostrarDialogoErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
