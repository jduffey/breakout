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
    public static final int MAX_PLAYER_RIGHT_X_POS = 600; // Tutorial value is 600
    public static final int MIN_PLAYER_LEFT_X_POS = 10; // Tutorial value is 10
    public static final int LEFTMOST_ALLOWED_BALL_X_POS = 0; // Tutorial value is 0
    public static final int RIGHTMOST_ALLOWED_BALL_X_POS = 670; // Tutorial value is 670
    public static final int TOPMOST_ALLOWED_BALL_Y_POS = 0; // Tutorial value is 0
    public static final int INITIAL_BRICKMAP_ROWS = 3; // Tutorial value is 3
    public static final int INITIAL_BRICKMAP_COLUMNS = 7; // Tutorial value is 7
    public static final int PLAYER_X_MOVEMENT_PER_CLICK = 20; // Tutorial value is 20
    public static final int PADDLE_Y_POSITION = 550; // Tutorial value is 550
    public static final int PADDLE_WIDTH = 100; // Tutorial value is 100
    public static final int PADDLE_HEIGHT = 8; // Tutorial value is 8
    public static final int BALL_WIDTH = 20; // Tutorial value is 20
    public static final int BALL_HEIGHT = 20; // Tutorial value is 20
    public static final int BOTTOMMOST_ALLOWED_BALL_POSITION_Y = 570; // Tutorial value is 570

    // Set initial play state, score, and remaining bricks
    private boolean play = false;
    private int score = INITIAL_SCORE;
    private int bricksRemaining;

    // Declare Timer and delay value
    private Timer timer;
    private int delay = TIMER_DELAY_VALUE;

    // Set initial player and ball positions, and ball velocities
    private int currentPlayerPositionX = INITIAL_PLAYER_X_POS;
    private int currentBallPositionX = INITIAL_BALL_X_POSITION;
    private int currentBallPositionY = INITIAL_BALL_Y_POSITION;
    private int ballVelocityX = INITIAL_BALL_X_VELOCITY;
    private int ballVelocityY = INITIAL_BALL_Y_VELOCITY;

    // Initialize BrickMapGenerator
    private BrickMapGenerator map;

    public Gameplay() {

        map = new BrickMapGenerator(INITIAL_BRICKMAP_ROWS, INITIAL_BRICKMAP_COLUMNS);
        setBricksRemaining(INITIAL_BRICKMAP_ROWS, INITIAL_BRICKMAP_COLUMNS);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    private void setBricksRemaining(int initialBrickmapRows, int initialBrickmapColumns) {
        bricksRemaining = initialBrickmapRows * initialBrickmapColumns;
    }

    public void paint(Graphics g) {

        drawActiveGameplayElements(g);

        // Once all bricks have been destroyed
        if (bricksRemaining <= 0) {

            play = false;

            displayWinningMessage(g);

            displayAskToRestartMessage(g);
        }

        // If ball falls below play zone
        if (currentBallPositionY > BOTTOMMOST_ALLOWED_BALL_POSITION_Y) {

            play = false;

            displayGameOverMessage(g);

            displayAskToRestartMessage(g);
        }

        g.dispose();

    }

    private void drawActiveGameplayElements(Graphics g) {
        // Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // Bricks
        map.draw((Graphics2D) g);

        // Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592); // Left border
        g.fillRect(0, 0, 692, 3); // Top border
        g.fillRect(691, 0, 3, 592); // Right border

        // Score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 592, 30);

        // Paddle
        g.setColor(Color.green);
        g.fillRect(currentPlayerPositionX, PADDLE_Y_POSITION, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Ball
        g.setColor(Color.yellow);
        g.fillOval(currentBallPositionX, currentBallPositionY, BALL_WIDTH, BALL_HEIGHT);
    }

    private void displayAskToRestartMessage(Graphics g) {
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Press Enter to restart ", 230, 350);
    }

    private void displayGameOverMessage(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("Game Over. Score: " + score, 190, 300);
    }

    private void displayWinningMessage(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("You Win! Score: " + score, 190, 300);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (currentPlayerPositionX >= MAX_PLAYER_RIGHT_X_POS) {
                currentPlayerPositionX = MAX_PLAYER_RIGHT_X_POS;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (currentPlayerPositionX < MIN_PLAYER_LEFT_X_POS) {
                currentPlayerPositionX = MIN_PLAYER_LEFT_X_POS;
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
        currentPlayerPositionX = INITIAL_PLAYER_X_POS;
        score = INITIAL_SCORE;
        bricksRemaining = INITIAL_BRICKS_REMAINING;
        map = new BrickMapGenerator(INITIAL_BRICKMAP_ROWS, INITIAL_BRICKMAP_COLUMNS);

        repaint();
    }

    private void moveRight() {
        play = true;
        currentPlayerPositionX += PLAYER_X_MOVEMENT_PER_CLICK;
    }

    private void moveLeft() {
        play = true;
        currentPlayerPositionX -= PLAYER_X_MOVEMENT_PER_CLICK;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();

        if (play) {

            // Detects collision of ball and paddle and reverses Y velocity if so
            if (new Rectangle(currentBallPositionX, currentBallPositionY, BALL_WIDTH, BALL_HEIGHT)
                    .intersects(new Rectangle(currentPlayerPositionX, PADDLE_Y_POSITION, PADDLE_WIDTH, PADDLE_HEIGHT))) {
                ballVelocityY = reverseBallVelocity(ballVelocityY);
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
                        Rectangle ballRect = new Rectangle(currentBallPositionX, currentBallPositionY, BALL_WIDTH, BALL_HEIGHT);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            bricksRemaining--;
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
