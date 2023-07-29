/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SnakeTheGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int WIDTH_SCALE = 1400;
    static final int HEIGHT_SCALE = 700;
    static final int PIXEL_SCALE = 15;
    static final int PIXEL_UNITS = (WIDTH_SCALE * HEIGHT_SCALE) / (PIXEL_SCALE * PIXEL_SCALE);
    static final int DELAY = 30;
    final int snakeX[] = new int[PIXEL_UNITS];
    final int snakeY[] = new int[PIXEL_UNITS];
    int snakeBodyPart = 5;
    int score;
    int appleX;
    int appleY;
    char direction ;
    int gameStart = 0;
    Timer time;

    GamePanel() {

        this.setPreferredSize(new Dimension(WIDTH_SCALE, HEIGHT_SCALE));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myController());
        startGame();
    }

    public void startGame() {
        direction = 'R';
        spawnApple();
        gameStart = 1;
        time = new Timer(DELAY, this);
        time.start();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (gameStart == 1) {

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, PIXEL_SCALE, PIXEL_SCALE);

            for (int i = 0; i < snakeBodyPart; i++) {
                if (i == 0) {
                    g.setColor(Color.yellow);
                    g.fillRect(snakeX[0], snakeY[0], PIXEL_SCALE, PIXEL_SCALE);
                } else {
                    g.setColor(Color.green);
                    g.fillRect(snakeX[i], snakeY[i], PIXEL_SCALE, PIXEL_SCALE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("DeluxeFont", Font.PLAIN, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + score, (WIDTH_SCALE - metrics.stringWidth("Score: " + score)) / 2, g.getFont().getSize()+ 20);
        } else {
            gameOver(g);
        }

    }

    public void spawnApple() {

        appleX = new Random().nextInt(WIDTH_SCALE / PIXEL_SCALE) * PIXEL_SCALE;
        appleY = new Random().nextInt(HEIGHT_SCALE / PIXEL_SCALE) * PIXEL_SCALE;
    }

    public void move() {
        for (int i = snakeBodyPart; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (direction) {
            case 'U':
                snakeY[0] = snakeY[0] - PIXEL_SCALE;
                break;
            case 'D':
                snakeY[0] = snakeY[0] + PIXEL_SCALE;
                break;
            case 'L':
                snakeX[0] = snakeX[0] - PIXEL_SCALE;
                break;
            case 'R':
                snakeX[0] = snakeX[0] + PIXEL_SCALE;
                break;
        }

    }

    public void AppleEaten() {
        if ((snakeX[0] == appleX) && (snakeY[0] == appleY)) {
            snakeBodyPart++;
            score++;
            spawnApple();
        }
    }

    public void checkCollisions() {
        //checks if head collides with body
        for (int i = snakeBodyPart; i > 0; i--) {
            if ((snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
                gameStart = 0;
            }
        }
        //check if head touches left border
        if (snakeX[0] < 0) {
            gameStart = 0;
        }
        //check if head touches right border
        if (snakeX[0] > WIDTH_SCALE) {
            gameStart = 0;
        }
        //check if head touches top border
        if (snakeY[0] < 0) {
            gameStart = 0;
        }
        //check if head touches bottom border
        if (snakeY[0] > HEIGHT_SCALE) {
            gameStart = 0;
        }

        if (gameStart == 0) {
            time.stop();
        }
    }

    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.green);
        g.setFont(new Font("DeluxeFont", Font.BOLD, 35));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Your Score: " + score, (WIDTH_SCALE - metrics1.stringWidth("Your Score: " + score)) / 2, g.getFont().getSize() + 400);
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("DeluxeFont", Font.BOLD, 85));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (WIDTH_SCALE - metrics2.stringWidth("Game Over")) / 2, HEIGHT_SCALE / 2);
        //Restart game text
        g.setColor(Color.white);
        g.setFont(new Font("DeluxeFont", Font.BOLD, 15));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("(Press SPACE to play again :D)", (WIDTH_SCALE - metrics3.stringWidth("(Press SPACE to play again :D")) / 2, 600);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        move();
        AppleEaten();
        checkCollisions();
        repaint();

    }

    public class myController extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT,KeyEvent.VK_A:
                    if (direction != 'R' && snakeY[0] != snakeY[1]) {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT,KeyEvent.VK_D:
                    if (direction != 'L' && snakeY[0] != snakeY[1]) {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP,KeyEvent.VK_W:
                    if (direction != 'D' && snakeX[0] != snakeX[1]) {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN,KeyEvent.VK_S:
                    if (direction != 'U' && snakeX[0] != snakeX[1]) {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:

                    if (gameStart == 0) {
                        snakeBodyPart = 5;
                        score = 0;
                        snakeX[0] = 0;
                        snakeY[0] = 0;
                        for (int i = snakeBodyPart; i > 0; i--) {
                            snakeX[i] = snakeBodyPart * -1;
                            snakeY[i] = 0;
                        }
                        startGame();
                        
                        break;
                    }
            }
        }
    }
}
