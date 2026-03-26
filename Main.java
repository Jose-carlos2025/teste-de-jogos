import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main extends JFrame {

    public Main() {
        // Configuração da janela do jogo
        setTitle("Jogo das Bolas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        // Adicionando o painel de jogo
        GamePanel panel = new GamePanel();
        add(panel);

        // Botão para retornar ao menu inicial
        JButton botaoRetornar = new JButton("Retornar ao Menu Inicial");
        botaoRetornar.addActionListener(e -> {
            new MenuInicial().setVisible(true); // Abre o menu inicial
            dispose(); // Fecha a janela do jogo de Bolas
        });
        
        add(botaoRetornar, BorderLayout.SOUTH); // Adiciona o botão ao painel

        // Tornando a janela visível
        setVisible(true);
    }
}

class GamePanel extends JPanel implements MouseListener, ActionListener {

    private ArrayList<Ball> balls;
    private Random rand;
    private Map<Color, Integer> colorCount;
    private javax.swing.Timer timer;

    public GamePanel() {
        balls = new ArrayList<>();
        rand = new Random();
        colorCount = new HashMap<>();
        colorCount.put(Color.RED, 0);
        colorCount.put(Color.BLUE, 0);
        colorCount.put(Color.GREEN, 0);
        colorCount.put(Color.YELLOW, 0);
        colorCount.put(Color.MAGENTA, 0);
        colorCount.put(Color.CYAN, 0);

        // Adiciona 6 bolas de cada cor
        addBallsOfColor(Color.RED, 6);
        addBallsOfColor(Color.BLUE, 6);
        addBallsOfColor(Color.GREEN, 6);
        addBallsOfColor(Color.YELLOW, 6);
        addBallsOfColor(Color.MAGENTA, 6);
        addBallsOfColor(Color.CYAN, 6);

        addMouseListener(this); // Para detectar cliques
        setBackground(Color.white);

        // Timer para animar as bolas (aproximadamente 60 FPS)
        timer = new javax.swing.Timer(16, this);
        timer.start();
    }

    // Método para desenhar as bolas no painel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Ball ball : balls) {
            g.setColor(ball.color);
            g.fillOval(ball.x, ball.y, 30, 30);
        }
    }

    // Este método será chamado pelo Timer a cada 16ms
    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ball ball : balls) {
            ball.move();
            // Verifica colisão com as bordas do painel
            if (ball.x < 0 || ball.x > getWidth() - 30) ball.dx = -ball.dx;
            if (ball.y < 0 || ball.y > getHeight() - 30) ball.dy = -ball.dy;
        }
        repaint(); // ERRO 3 CORRIGIDO: Redesenha o painel apenas uma vez, após mover todas as bolas.
    }

    // Método para detecção de clique nas bolas
    @Override
    public void mouseClicked(MouseEvent e) {
        // ERRO 1 CORRIGIDO: A lista de remoção é criada fora do loop.
        ArrayList<Ball> toRemove = new ArrayList<>();

        for (Ball ball : balls) {
            if (e.getX() >= ball.x && e.getX() <= ball.x + 30 && e.getY() >= ball.y && e.getY() <= ball.y + 30) {
                // Simula a explosão (remover bola)
                toRemove.add(ball);
                colorCount.put(ball.color, colorCount.get(ball.color) + 1);
                
                // Verifica se 6 bolas da mesma cor foram removidas
                if (colorCount.get(ball.color) >= 6) {
                    resetGame();
                    return; // Sai do método para não tentar remover bolas que não existem mais
                }
            }
        }

        // ERRO 2 CORRIGIDO: A remoção acontece depois do loop e o 'break' foi removido.
        if (!toRemove.isEmpty()) {
            balls.removeAll(toRemove);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    // Método para adicionar 6 bolas de uma cor específica
    private void addBallsOfColor(Color color, int count) {
        for (int i = 0; i < count; i++) {
            int dx = (rand.nextBoolean() ? 1 : -1) * (1 + rand.nextInt(4)); // Velocidade entre 1 e 4, direção aleatória
            int dy = (rand.nextBoolean() ? 1 : -1) * (1 + rand.nextInt(4)); // Velocidade entre 1 e 4, direção aleatória

            balls.add(new Ball(rand.nextInt(500), rand.nextInt(500), dx, dy, color));
        }
    }

    private void resetGame() {
        balls.clear();
        colorCount.clear();
        addBallsOfColor(Color.RED, 6);
        addBallsOfColor(Color.BLUE, 6);
        addBallsOfColor(Color.GREEN, 6);
        addBallsOfColor(Color.YELLOW, 6);
        addBallsOfColor(Color.MAGENTA, 6);
        addBallsOfColor(Color.CYAN, 6);
        repaint();
    }
}

class Ball {
    int x, y, dx, dy;
    Color color;

    public Ball(int x, int y, int dx, int dy, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    public void move() {
        x += dx;
        y += dy;
    }
}
