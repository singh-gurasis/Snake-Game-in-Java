package SnakeGame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame  = new JFrame("Snake Game");
        frame.setSize(915,700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        GamePanel gp = new GamePanel();
        gp.setBackground(Color.DARK_GRAY);
        frame.add(gp);
    }
}