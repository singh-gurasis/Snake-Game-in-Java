package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    int[] Sx_Length = new int[200];
    int[] Sy_Length = new int[200];
    private int LengthOfSnake = 3;

    int[] xPos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,
            525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] yPos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,
            525,550,575,600,625};

    private Random rnd = new Random();
    private int enemyX, enemyY;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private boolean gameOver = false;

    private int moves = 0;
    private int score = 0;

    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));

    private Timer timer;
    private int delay = 100;

    public GamePanel(){

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay, this);
        timer.start();

        newEnemy();
    }
    public void paint(Graphics g){
        super.paint(g);

        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);

        g.drawRect(24,74,851,576);
        snaketitle.paintIcon(this, g, 25,11);

        g.setColor(Color.BLACK);
        g.fillRect(25,75,850,575);

        if(moves == 0){
            Sx_Length[0] = 100;
            Sx_Length[1] = 75;
            Sx_Length[2] = 50;

            Sy_Length[0] = 100;
            Sy_Length[1] = 100;
            Sy_Length[2] = 100;
        }

        if(left){
            leftmouth.paintIcon(this, g, Sx_Length[0], Sy_Length[0]);
        }
        if(right){
            rightmouth.paintIcon(this, g, Sx_Length[0], Sy_Length[0]);
        }
        if(up){
            upmouth.paintIcon(this, g, Sx_Length[0], Sy_Length[0]);
        }
        if(down){
            downmouth.paintIcon(this, g, Sx_Length[0], Sy_Length[0]);
        }

        for(int i=0; i<LengthOfSnake; i++){
            snakeimage.paintIcon(this, g, Sx_Length[i], Sy_Length[i]);
        }

        enemy.paintIcon(this, g, enemyX, enemyY);

        if(gameOver){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 300, 300);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press SPACE to Restart", 320, 350);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("SCORE: " + score, 750, 30);
        g.drawString("LENGTH: " + LengthOfSnake, 750, 50);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i=LengthOfSnake-1; i>0; i--){
            Sx_Length[i] = Sx_Length[i-1];

            Sy_Length[i] = Sy_Length[i-1];
        }

        if(left){
            int x = Sx_Length[0];
            x -= 25;
            Sx_Length[0] = x;
        }
        if(right){
            int x = Sx_Length[0];
            x += 25;
            Sx_Length[0] = x;
        }
        if(up){
            int y = Sy_Length[0];
            y -= 25;
            Sy_Length[0] = y;
        }
        if(down){
            int y = Sy_Length[0];
            y += 25;
            Sy_Length[0] = y;
        }

        if(Sx_Length[0]>850) Sx_Length[0] = 25;
        if(Sx_Length[0]<25) Sx_Length[0] = 850;

        if(Sy_Length[0]>625) Sy_Length[0] = 75;
        if(Sy_Length[0]<75) Sy_Length[0] = 625;

        collidesWithEnemy();
        collidesWithBody();

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver && e.getKeyCode() == KeyEvent.VK_SPACE){
            repaint();
            Restart();
        }
        if(e.getKeyCode() == KeyEvent.VK_A && !right){
            left = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_D && !left){
            right = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_W && !down){
            left = false;
            right = false;
            up = true;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_S && !up){
            left = false;
            right = false;
            down = true;
            moves++;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void newEnemy(){
        enemyX = xPos[rnd.nextInt(34)];
        enemyY = yPos[rnd.nextInt(23)];
        for(int i=LengthOfSnake-1; i>=0; i--){
            if(Sx_Length[i] == enemyX && Sy_Length[i] == enemyY){
                newEnemy();
            }
        }
    }

    private void collidesWithEnemy() {
        if(Sx_Length[0] == enemyX && Sy_Length[0] == enemyY){
            newEnemy();
            LengthOfSnake++;
            score += 5;
        }
    }
    private void collidesWithBody(){
        for(int i=LengthOfSnake-1; i>0; i--){
            if((Sx_Length[0] == Sx_Length[i]) && (Sy_Length[0] == Sy_Length[i])){
                timer.stop();
                gameOver = true;
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
        }
    }
    private void Restart(){
        newEnemy();
        gameOver = false;
        moves = 0;
        score = 0;
        LengthOfSnake = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
    }
}
