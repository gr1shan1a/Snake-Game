package snakeGame;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Snake-Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320, 345);
        add(new GameField());
        setLocation(400, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();

    }
}