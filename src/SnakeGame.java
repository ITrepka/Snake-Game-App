import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class SnakeGame extends JPanel implements KeyListener {
    final int BOARD_SIZE = 300;
    final int DOT_SIZE = 10;
    final int ALL_DOTS = (BOARD_SIZE * BOARD_SIZE) / (DOT_SIZE * DOT_SIZE);
    final int RAND_POS = BOARD_SIZE / DOT_SIZE;

    int[] x = new int[ALL_DOTS];
    int[] y = new int[ALL_DOTS];

    int dots;
    int appleX;
    int appleY;
    int score;

    boolean leftDirection = false;
    boolean rightDirection = true;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean inGame = true;

    public SnakeGame() {
        setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        initGame();

        Timer timer = new Timer(140, e -> {
            if (inGame) {
                move();
                checkApple();
                checkCollision();
            }
            repaint();
        });
        timer.start();
    }

    private void initGame() {
        dots = 3;
        score = 0;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * DOT_SIZE;
            y[z] = 50;
        }

        locateApple();
    }

    void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        appleX = r * DOT_SIZE;

        r = (int) (Math.random() * RAND_POS);
        appleY = r * DOT_SIZE;
    }

    void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            score++;
            locateApple();
        }
    }

    void checkCollision() {
        for (int z = dots - 1; z > 0; z--) {
            if (z > 4 && x[0] == x[z] && y[0] == y[z]) {
                inGame = false;
                break;
            }
        }

        if (y[0] >= BOARD_SIZE || y[0] < 0 || x[0] >= BOARD_SIZE || x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            // Game over logic here
            System.out.println("Game Over! Score: " + score);
        }
    }

    void move() {
        for (int z = dots - 1; z > 0; z--) {
            x[z] = x[z - 1];
            y[z] = y[z - 1];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            g.setColor(Color.green);
            g.fillOval(appleX, appleY, DOT_SIZE, DOT_SIZE);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.setColor(Color.white);
                } else {
                    g.setColor(Color.green);
                }
                g.fillRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
            }
        } else {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics = g.getFontMetrics();
            String resultRate = rateResult();
            String gameOverMessage = "Game Over! Score: " + score + ". " + resultRate;
            int messageWidth = metrics.stringWidth(gameOverMessage);
            int messageHeight = metrics.getHeight();
            int messageX = (BOARD_SIZE - messageWidth) / 2;
            int messageY = (BOARD_SIZE - messageHeight) / 2;
            g.drawString(gameOverMessage, messageX, messageY);
        }
    }

    private String rateResult() {
        if (score < 10) {
            return ":(";
        } else if (score < 20) {
            return "Good!";
        } else if (score < 50) {
            return "Great!";
        } else if (score < 100) {
            return "Amazing!";
        } else {
            return "No way! You are pro!";
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
            leftDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
            rightDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if ((key == KeyEvent.VK_UP) && (!downDirection)) {
            upDirection = true;
            rightDirection = false;
            leftDirection = false;
        }

        if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
            downDirection = true;
            rightDirection = false;
            leftDirection = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Pretchee Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new SnakeGame());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
