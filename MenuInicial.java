import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInicial extends JFrame {
    public MenuInicial() {
        setTitle("Jogos");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 1, 10, 10)); // 2 linhas, 1 coluna, espaçamento de 10 pixels

        // Botão para o jogo de Damas
        JButton botaoDamas = new JButton("Jogo de Damas");
        botaoDamas.setFont(new Font("Serif", Font.BOLD, 18));
        botaoDamas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre o jogo de Damas
                new DamasGUI().setVisible(true);
                dispose(); // Fecha o menu inicial
            }
        });

        // Botão para o jogo de Bolas
        JButton botaoBolas = new JButton("Jogo de Bolas");
        botaoBolas.setFont(new Font("Serif", Font.BOLD, 18));
        botaoBolas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre o jogo de Bolas (CORRIGIDO)
                new Main().setVisible(true);
                dispose(); // Fecha o menu inicial
            }
        });

         // Botão para o jogo de Bolas
         JButton botaoPergunta = new JButton("Jogo de Perguntas");
         botaoPergunta.setFont(new Font("Serif", Font.BOLD, 18));
         botaoPergunta.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 // Abre o jogo de Bolas
                 new JogoPerguntasRespostasGUI().setVisible(true); // Inicia o jogo de Bolas
                 dispose(); // Fecha o menu inicial
             }
         });
 

        // Adiciona os botões ao painel
        painel.add(botaoDamas);
        painel.add(botaoBolas);
        painel.add(botaoPergunta);

        // Adiciona o painel à janela
        add(painel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuInicial().setVisible(true));
    }
}