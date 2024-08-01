package snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    final private int SIZE = 320;
    final private int DOT_SIZE = 16;
    final private int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    private JButton playAgainButton;

    public GameField() {
        setBackground(Color.black);
        LoadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

        playAgainButton = new JButton("Play Again");
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        this.add(playAgainButton);
    }


    public void LoadImages() {
        ImageIcon iia = new ImageIcon("images/apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("images/dot.png");
        dot = iid.getImage();
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();

    }

    public void createApple() {
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void move() {
        for (int i = dots; i > 0; i-- ) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0 ; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i] ) {
                inGame = false;
            }
        }

        if (x[0] > SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] > SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }

        repaint();
    }

    private void showGameOver(Graphics g) {
        String str = "Game Over";
        g.setColor(Color.white);
        g.drawString(str, 125, SIZE / 2);
        String scoreStr = "Your score: " + dots;
        g.drawString(scoreStr, 119, SIZE / 2 + 20);
        String instructions = "Press ENTER to play again";
        g.drawString(instructions, 85, SIZE / 2 + 40);
        String exitInstructions = "Press ESC to exit";
        g.drawString(exitInstructions, 105, SIZE / 2 + 60);
    }

    private void restartGame() {
        inGame = true;
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        createApple();
        timer.start();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            showGameOver(g);
        }
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT && !right) {
                    left = true;
                    up = false;
                    down = false;
                }
                if (key == KeyEvent.VK_RIGHT && !left) {
                    right = true;
                    up = false;
                    down = false;
                }
                if (key == KeyEvent.VK_UP && !down) {
                    up = true;
                    left = false;
                    right = false;
                }
                if (key == KeyEvent.VK_DOWN && !up) {
                    down = true;
                    left = false;
                    right = false;
                }
            } else {
                if (key == KeyEvent.VK_ENTER) {
                    restartGame();
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        }
    }
}
