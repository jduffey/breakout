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
    public static final int MAX_PLAYER_RIGHT_Y_POS = 600; // Tutorial value is 600
    public static final int MIN_PLAYER_LEFT_Y_POS = 10; // Tutorial value is 10
    public static final int LEFTMOST_ALLOWED_BALL_X_POS = 0;
    public static final int TOPMOST_ALLOWED_BALL_Y_POS = 0;
    public static final int RIGHTMOST_ALLOWED_BALL_X_POS = 670;

    // Set initial play state, score, and remaining bricks
    private boolean play = false;
    private int score = INITIAL_SCORE;
    private int totalBricks = INITIAL_BRICKS_REMAINING;

    // Declare Timer and delay value
    private Timer timer;
    private int delay = TIMER_DELAY_VALUE;

    // Set initial player and ball positions, and ball velocities
    private int playerX = INITIAL_PLAYER_X_POS;
    private int currentBallPositionX = INITIAL_BALL_X_POSITION;
    private int currentBallPositionY = INITIAL_BALL_Y_POSITION;
    private int ballVelocityX = INITIAL_BALL_X_VELOCITY;
    private int ballVelocityY = INITIAL_BALL_Y_VELOCITY;

    // Initialize BrickMapGenerator
    private BrickMapGenerator map;

    public Gameplay() {

        // Tutorial value is 3 * 7
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
        g.fillOval(currentBallPositionX, currentBallPositionY, 20, 20);

        if (totalBricks <= 0) {
            play = false;
            ballVelocityX = 0;
            ballVelocityY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Win! Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to restart ", 230, 350);
        }

        if (currentBallPositionY > 570) {
            play = false;
            ballVelocityX = 0;
            ballVelocityY = 0;
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
            if (playerX >= MAX_PLAYER_RIGHT_Y_POS) {
                playerX = MAX_PLAYER_RIGHT_Y_POS;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < MIN_PLAYER_LEFT_Y_POS) {
                playerX = MIN_PLAYER_LEFT_Y_POS;
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
        currentBallPositionX = INITIAL_BALL_X_POSITION;
        currentBallPositionY = INITIAL_BALL_Y_POSITION;
        ballVelocityX = INITIAL_BALL_X_VELOCITY;
        ballVelocityY = INITIAL_BALL_Y_VELOCITY;
        playerX = INITIAL_PLAYER_X_POS;
        score = INITIAL_SCORE;
        totalBricks = INITIAL_BRICKS_REMAINING;
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

            if (new Rectangle(currentBallPositionX, currentBallPositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballVelocityY = -ballVelocityY;
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
                        Rectangle ballRect = new Rectangle(currentBallPositionX, currentBallPositionY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (currentBallPositionX + 19 <= brickRect.x || currentBallPositionX + 1 >= brickRect.x + brickRect.width) {
                                ballVelocityX = -ballVelocityX;
                            } else {
                                ballVelocityY = -ballVelocityY;
                            }

                            break A;
                        }
                    }
                }
            }

            currentBallPositionX += ballVelocityX;
            currentBallPositionY += ballVelocityY;

            if (currentBallPositionX < LEFTMOST_ALLOWED_BALL_X_POS) {
                ballVelocityX = reverseBallVelocity(ballVelocityX);
            }
            if (currentBallPositionX > RIGHTMOST_ALLOWED_BALL_X_POS) {
                ballVelocityX = reverseBallVelocity(ballVelocityX);
            }
            if (currentBallPositionY < TOPMOST_ALLOWED_BALL_Y_POS) {
                ballVelocityY = reverseBallVelocity(ballVelocityY);
            }

        }

        repaint();

    }

    private int reverseBallVelocity(int ballVelocity) {
        return -ballVelocity;
    }

    // Not used in this program
    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Not used in this program
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
