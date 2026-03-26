import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class JogoPerguntasRespostasGUI extends JFrame {
    private int pontuacao = 0;
    private int perguntaAtual = 0;
    private List<Pergunta> perguntas;
    private JLabel labelPergunta;
    private JButton[] botoesResposta;
    private JLabel labelPontuacao;
    
    public JogoPerguntasRespostasGUI() {
        setTitle("Jogo de Perguntas e Respostas");
        setSize(600, 400); // Tamanho reduzido
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializa os componentes
        inicializarComponentes();
        // Configura as perguntas
        inicializarPerguntas();
        
        // Mostra a primeira pergunta
        mostrarPergunta();
    }
    
    private void inicializarComponentes() {
        // Configura o layout principal
        setLayout(new BorderLayout(10, 10));
        
        // Painel superior com a pergunta - MELHORADO
        JPanel painelPergunta = new JPanel(new BorderLayout());
        painelPergunta.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        labelPergunta = new JLabel("", JLabel.CENTER);
        labelPergunta.setFont(new Font("Arial", Font.BOLD, 14)); // Tamanho da fonte reduzido
        labelPergunta.setVerticalAlignment(SwingConstants.TOP);
        
        // Adiciona JScrollPane para perguntas longas
        JScrollPane scrollPergunta = new JScrollPane(labelPergunta);
        scrollPergunta.setPreferredSize(new Dimension(400, 100)); // Diminuindo a largura do JScrollPane
        scrollPergunta.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPergunta.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        painelPergunta.add(scrollPergunta, BorderLayout.CENTER);
        add(painelPergunta, BorderLayout.NORTH);
        
        // Painel central com as respostas - MELHORADO
        JPanel painelRespostas = new JPanel(new GridLayout(2, 2, 15, 15));
        painelRespostas.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        botoesResposta = new JButton[4];
        
        for (int i = 0; i < 4; i++) {
            botoesResposta[i] = new JButton();
            botoesResposta[i].setFont(new Font("Arial", Font.PLAIN, 12)); // Tamanho da fonte reduzido
            botoesResposta[i].setHorizontalAlignment(SwingConstants.LEFT);
            botoesResposta[i].setContentAreaFilled(false);
            botoesResposta[i].setOpaque(true);
            botoesResposta[i].setBackground(new Color(240, 240, 240));
            botoesResposta[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            
            final int index = i;
            botoesResposta[i].addActionListener(e -> verificarResposta(index));
            painelRespostas.add(botoesResposta[i]);
        }
        
        // Adiciona JScrollPane também para as respostas
        JScrollPane scrollRespostas = new JScrollPane(painelRespostas);
        scrollRespostas.setBorder(null);
        add(scrollRespostas, BorderLayout.CENTER);
        
        // Painel inferior - MELHORADO
        JPanel painelInferior = new JPanel(new BorderLayout());
        
        // Label de pontuação
        labelPontuacao = new JLabel("Pontuação: 0/0", JLabel.CENTER);
        labelPontuacao.setFont(new Font("Arial", Font.BOLD, 12)); // Tamanho da fonte reduzido
        labelPontuacao.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Botão de retornar
        JButton botaoRetornar = new JButton("Retornar ao Menu");
        botaoRetornar.addActionListener(e -> dispose());
        
        painelInferior.add(labelPontuacao, BorderLayout.CENTER);
        painelInferior.add(botaoRetornar, BorderLayout.EAST);
        add(painelInferior, BorderLayout.SOUTH);
    }
    
    private void inicializarPerguntas() {
        perguntas = new ArrayList<>();
        
        // Adicione suas perguntas aqui
        perguntas.add(new Pergunta(
            "Qual das seguintes opções representa corretamente o conceito de encapsulamento em Java?",
            new String[]{
                "Tornar todos os atributos e métodos públicos para facilitar o acesso ao código",
                "Tornar os atributos privados e fornecer métodos get e set públicos",
                "Utilizar static em métodos para garantir acesso global",
                "Esconder o código-fonte num ficheiro .jar"
            },
            1
        ));
        
        perguntas.add(new Pergunta(
            "Dado o seguinte código, qual será a saída?\nclass Programmer {\npublic void code() {\nSystem.out.println(\"Programando em C++\");\n}\n}\nclass JavaProgrammer extends Programmer {\npublic void code() {\nSystem.out.println(\"Programando em Java\");\n}\n}\npublic class Main {\npublic static void main(String[] args) {\nProgrammer p = new JavaProgrammer();\np.code();\n}\n}",
            new String[]{
                "Programando em C++",
                "Erro de compilação",
                "Programando em Java",
                "Nenhuma das anteriores"
            },
            2
        ));
        // Pergunta 3 - Interfaces
    perguntas.add(new Pergunta(
        "Em Java, qual das afirmações sobre interfaces está correta?",
        new String[]{
            "Interfaces podem conter métodos privados com corpo",
            "Interfaces podem ter métodos default e static",
            "Interfaces não podem conter variáveis",
            "Interfaces permitem herança múltipla entre classes"
        },
        1
    ));
    
    // Pergunta 4 - Polimorfismo
    perguntas.add(new Pergunta(
        "O que é verdadeiro sobre polimorfismo em Java?",
        new String[]{
            "Polimorfismo só é possível com métodos static",
            "Permite que uma superclasse referencie objetos de subclasses",
            "Um objeto pode ter múltiplas classes-mãe diretas",
            "Só é utilizado em interfaces"
        },
        1
    ));
    
    // Pergunta 5 - Pilares POO
    perguntas.add(new Pergunta(
        "Qual dos seguintes conceitos é considerado um dos 4 pilares da Programação Orientada a Objetos em Java?",
        new String[]{"Loops", "Herança", "Variáveis", "Comentários"},
        1
    ));
        // Adicione mais perguntas conforme necessário
    }
    
    private void mostrarPergunta() {
        if (perguntaAtual < perguntas.size()) {
            Pergunta p = perguntas.get(perguntaAtual);
            labelPergunta.setText("<html><div style='width:550px;padding:10px;'>" + 
                                p.getTexto().replace("\n", "<br/>") + "</div></html>");
            
            for (int i = 0; i < 4; i++) {
                botoesResposta[i].setText("<html><div style='width:250px;padding:5px;'>" + 
                                        p.getRespostas()[i] + "</div></html>");
                resetarAparenciaBotao(i); // Reseta a aparência para a nova pergunta
            }
            
            labelPontuacao.setText("Pontuação: " + pontuacao + "/" + perguntas.size());
        } else {
            mostrarResultadoFinal();
        }
    }
    
    private void verificarResposta(int respostaIndex) {
        Pergunta p = perguntas.get(perguntaAtual);

        // Desabilita os botões para evitar cliques múltiplos
        for (JButton botao : botoesResposta) {
            botao.setEnabled(false);
        }
        
        if (respostaIndex == p.getRespostaCorreta()) {
            pontuacao++;
            botoesResposta[respostaIndex].setBackground(new Color(144, 238, 144)); // Verde claro
        } else {
            botoesResposta[respostaIndex].setBackground(new Color(255, 182, 193)); // Vermelho claro
            botoesResposta[p.getRespostaCorreta()].setBackground(new Color(144, 238, 144)); // Mostra a correta em verde
        }

        // Usa um Timer para pausar antes de ir para a próxima pergunta
        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                perguntaAtual++;
                mostrarPergunta();
            }
        });
        timer.setRepeats(false); // O timer executa apenas uma vez
        timer.start();
    }

    private void resetarAparenciaBotao(int index) {
        botoesResposta[index].setEnabled(true);
        botoesResposta[index].setBackground(new Color(240, 240, 240));
    }
    
    private void mostrarResultadoFinal() {
        double percentual = (double) pontuacao / perguntas.size() * 100;
        String mensagem = String.format(
            "Fim do Jogo!\n\n" +
            "Você acertou %d de %d perguntas.\n" +
            "Percentual de acertos: %.0f%%\n\n" +
            "%s",
            pontuacao, perguntas.size(), percentual,
            (percentual >= 70) ? "Parabéns! Você passou no teste!" : "Você não atingiu a pontuação mínima. Estude mais!"
        );
        
        JOptionPane.showMessageDialog(this, mensagem, "Resultado Final", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
    
    // Classe interna para representar uma pergunta
    private class Pergunta {
        private String texto;
        private String[] respostas;
        private int respostaCorreta;
        
        public Pergunta(String texto, String[] respostas, int respostaCorreta) {
            this.texto = texto;
            this.respostas = respostas;
            this.respostaCorreta = respostaCorreta;
        }
        
        public String getTexto() {
            return texto;
        }        
        public String[] getRespostas() {
            return respostas;
        }
        
        public int getRespostaCorreta() {
            return respostaCorreta;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JogoPerguntasRespostasGUI().setVisible(true));
    }
}
