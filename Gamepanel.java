import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gamepanel extends JPanel implements ActionListener, KeyListener {

    private int[] snakexlength = new int[720];
    private int[] snakeylength = new int[720];
    private int lengthOfSnake = 3;

    // Arrays for location of the food
    private int[] xloc = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475,
            500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850 };
    private int[] yloc = { 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525,
            550, 575, 600, 625 };
    private Random random = new Random();
    private int foodx, foody;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean pause = false;

    private int moves = 0;
    private int score = 0;
    private boolean gameOver = false;

    // private ImageIcon gamespace = new ImageIcon("rsz_art.png");
    private ImageIcon snaketitle = new ImageIcon("snaketitle.jpg");
    private ImageIcon leftmouth = new ImageIcon("leftmouth.png");
    private ImageIcon rightmouth = new ImageIcon("rightmouth.png");
    private ImageIcon upmouth = new ImageIcon("upmouth.png");
    private ImageIcon downmouth = new ImageIcon("downmouth.png");
    private ImageIcon snakeimage = new ImageIcon("snakeimage.png");
    private ImageIcon food = new ImageIcon("food.png");

    private Timer timer;
    private int delay = 90;

    Gamepanel() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay, this);
        timer.start();

        newfood();
    }

    private void newfood() {
        foodx = xloc[random.nextInt(34)];
        foody = yloc[random.nextInt(23)];

        for (int i = lengthOfSnake - 1; i >= 0; i--) {
            if (snakexlength[i] == foodx && snakeylength[i] == foody) {
                newfood();
            }
        }
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 852, 55);
        g.drawRect(24, 74, 852, 576);
        snaketitle.paintIcon(this, g, 25, 11);
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);
        // gamespace.paintIcon(this, g, 25, 75);

        if (moves == 0) {
            snakexlength[0] = 100;
            snakexlength[1] = 75;
            snakexlength[2] = 50;

            snakeylength[0] = 100;
            snakeylength[1] = 100;
            snakeylength[2] = 100;

            // moves++;
        }

        if (left) {
            leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (right) {
            rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (up) {
            upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (down) {
            downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }

        for (int i = 1; i < lengthOfSnake; i++) {
            snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
        }
        food.paintIcon(this, g, foodx, foody);

        if (gameOver) {
            g.setColor(Color.white);
            g.setFont(new Font("ARIAL", Font.BOLD, 50));
            g.drawString("GAME OVER", 300, 300);
            g.setFont(new Font("ARIAL", Font.PLAIN, 20));
            g.drawString("Press ENTER to Restart ", 320, 350);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("ARIAL", Font.PLAIN, 14));
        g.drawString("SCORE : " + score, 750, 30);
        g.drawString("LENGTH : " + lengthOfSnake, 750, 50);

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // it will be called always after 100 ms

        for (int i = lengthOfSnake - 1; i > 0; i--) {
            snakexlength[i] = snakexlength[i - 1];
            snakeylength[i] = snakeylength[i - 1];
        }

        if (left) {
            snakexlength[0] = snakexlength[0] - 25;
        }
        if (right) {
            snakexlength[0] = snakexlength[0] + 25;
        }
        if (up) {
            snakeylength[0] = snakeylength[0] - 25;
        }
        if (down) {
            snakeylength[0] = snakeylength[0] + 25;
        }

        if (snakexlength[0] > 850)
            snakexlength[0] = 25;
        if (snakexlength[0] < 25)
            snakexlength[0] = 850;

        if (snakeylength[0] > 625)
            snakeylength[0] = 75;
        if (snakeylength[0] < 75)
            snakeylength[0] = 625;

        collidesWithFood();
        collidesWithbody();
        repaint();
    }

    private void collidesWithbody() {
        for (int i = lengthOfSnake - 1; i > 0; i--) {
            if (snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]) {
                timer.stop();
                gameOver = true;
            }
        }
    }

    private void collidesWithFood() {
        if (snakexlength[0] == foodx && snakeylength[0] == foody) {
            newfood();
            lengthOfSnake++;
            score++;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT&& !right) {
            left = true;
            right = false;
            up = false;
            down = false;
            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !left) {
            left = false;
            right = true;
            up = false;
            down = false;
            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && !down) {
            left = false;
            right = false;
            up = true;
            down = false;
            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !up) {
            left = false;
            right = false;
            up = false;
            down = true;
            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameOver = false;
            moves = 0;
            score = 0;
            left = false;
            right = true;
            up = false;
            down = false;
            lengthOfSnake = 3;
            newfood();
            timer.start();
            repaint();
            snakexlength[0] = 25;
            snakeylength[0] = 100;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            pause = !pause;
            if (pause) {
                timer.stop();
            } else {
                timer.start();
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
