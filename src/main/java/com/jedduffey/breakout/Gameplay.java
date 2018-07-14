package com.jedduffey.breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    // CONSTANTS
    public static final int INITIAL_BALL_X_VELOCITY = -1; // Tutorial value is -1
    public static final int INITIAL_BALL_Y_VELOCITY = -2; // Tutorial value is -2
    public static final int INITIAL_BALL_X_POSITION = 120; // Tutorial value is 120
    public static final int INITIAL_BALL_Y_POSITION = 350; // Tutorial value is 350
    public static final int INITIAL_PLAYER_X_POS = 310; // Tutorial value is 310
    public static final int INITIAL_BRICKS_REMAINING = 21; // Tutorial value is 21
    public static final int TIMER_DELAY_VALUE = 8; // Tutorial value is 8
    public static final int INITIAL_SCORE = 0; // Tutorial value is 0

    // Set initial play state, score, and remaining bricks
    private boolean play = false;
    private int score = INITIAL_SCORE;
    private int totalBricks = INITIAL_BRICKS_REMAINING;

    // Declare Timer and delay value
    private Timer timer;
    private int delay = TIMER_DELAY_VALUE;

    // Set initial player and ball positions, and ball velocities
    private int playerX = INITIAL_PLAYER_X_POS;
    private int ballPosX = INITIAL_BALL_X_POSITION;
    private int ballPosY = INITIAL_BALL_Y_POSITION;
    private int ballXDir = INITIAL_BALL_X_VELOCITY;
    private int ballYDir = INITIAL_BALL_Y_VELOCITY;

    // Initialize BrickMapGenerator
    private BrickMapGenerator map;

    public Gameplay() {

        map = new BrickMapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {

        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // bricks
        map.draw((Graphics2D) g);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 592, 30);

        // paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        if (totalBricks <= 0) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Win! Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to restart ", 230, 350);
        }

        if (ballPosY > 570) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over. Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to restart ", 230, 350);
        }

        g.dispose();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                resetGame();
            }
        }
    }

    private void resetGame() {
        play = true;
        ballPosX = 120;
        ballPosY = 350;
        ballXDir = -1;
        ballYDir = -2;
        playerX = 310;
        score = 0;
        totalBricks = 21;
        map = new BrickMapGenerator(3, 7);

        repaint();
    }

    private void moveRight() {
        play = true;
        playerX += 20;
    }

    private void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();

        if (play) {

            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDir = -ballYDir;
            }

            A:
            for (int i = 0; i < map.brickMap.length; i++) {
                for (int j = 0; j < map.brickMap[0].length; j++) {
                    if (map.brickMap[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballXDir = -ballXDir;
                            } else {
                                ballYDir = -ballYDir;
                            }

                            break A;
                        }
                    }
                }
            }

            ballPosX += ballXDir;
            ballPosY += ballYDir;
            if (ballPosX < 0) {
                ballXDir = -ballXDir;
            }
            if (ballPosY < 0) {
                ballYDir = -ballYDir;
            }
            if (ballPosX > 670) {
                ballXDir = -ballXDir;
            }
        }

        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
