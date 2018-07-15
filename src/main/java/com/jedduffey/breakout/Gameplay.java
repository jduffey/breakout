package com.jedduffey.breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private static final int TIMER_DELAY_VALUE = 8; // Tutorial value is 8

    private static final int INITIAL_BALL_X_VEL = -1; // Tutorial value is -1
    private static final int INITIAL_BALL_Y_VEL = -2; // Tutorial value is -2
    private static final int INITIAL_BALL_X_POS = 120; // Tutorial value is 120
    private static final int INITIAL_BALL_Y_POS = 350; // Tutorial value is 350
    private static final int INITIAL_PADDLE_X_POS = 310; // Tutorial value is 310

    private static final int INITIAL_SCORE = 0; // Tutorial value is 0
    private static final int INITIAL_BRICKMAP_ROWS = 3; // Tutorial value is 3
    private static final int INITIAL_BRICKMAP_COLUMNS = 7; // Tutorial value is 7
    private static final int SCORE_PER_BRICK_DESTROYED = 5; // Tutorial value is 5
    private static final int PADDLE_X_MOVEMENT_PER_CLICK = 20; // Tutorial value is 20

    private static final int PADDLE_Y_POS = 550; // Tutorial value is 550
    private static final int MIN_PADDLE_LEFT_X_POS = 10; // Tutorial value is 10
    private static final int MAX_PADDLE_RIGHT_X_POS = 600; // Tutorial value is 600
    private static final int LEFTMOST_ALLOWED_BALL_X_POS = 0; // Tutorial value is 0
    private static final int RIGHTMOST_ALLOWED_BALL_X_POS = 670; // Tutorial value is 670
    private static final int TOPMOST_ALLOWED_BALL_Y_POS = 0; // Tutorial value is 0
    private static final int BOTTOMMOST_ALLOWED_BALL_Y_POS = 570; // Tutorial value is 570

    private static final int PADDLE_WIDTH = 100; // Tutorial value is 100
    private static final int PADDLE_HEIGHT = 8; // Tutorial value is 8
    private static final int BALL_WIDTH = 20; // Tutorial value is 20
    private static final int BALL_HEIGHT = 20; // Tutorial value is 20

    private boolean playState;
    private int currentScore;
    private int bricksRemaining;

    private Timer timer;

    private int currentPaddlePositionX;
    private int currentBallPositionX;
    private int currentBallPositionY;
    private int ballVelocityX;
    private int ballVelocityY;

    private BrickMap gameBrickMap;

    Gameplay() {

        resetGameplayVariables();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(TIMER_DELAY_VALUE, this);
        timer.start();
    }

    private void resetGameplayVariables() {

        playState = false;
        currentBallPositionX = INITIAL_BALL_X_POS;
        currentBallPositionY = INITIAL_BALL_Y_POS;
        ballVelocityX = INITIAL_BALL_X_VEL;
        ballVelocityY = INITIAL_BALL_Y_VEL;
        currentPaddlePositionX = INITIAL_PADDLE_X_POS;
        currentScore = INITIAL_SCORE;
        bricksRemaining = INITIAL_BRICKMAP_ROWS * INITIAL_BRICKMAP_COLUMNS;
        gameBrickMap = new BrickMap(INITIAL_BRICKMAP_ROWS, INITIAL_BRICKMAP_COLUMNS);
        repaint();
    }

    public void paint(Graphics g) {

        drawActiveGameplayElements(g);

        boolean allBricksHaveBeenDestroyed = bricksRemaining <= 0;
        boolean ballFallsBelowPlayZone = currentBallPositionY > BOTTOMMOST_ALLOWED_BALL_Y_POS;

        if (allBricksHaveBeenDestroyed || ballFallsBelowPlayZone) {
            playState = false;
            displayAskToRestartMessage(g);
        }

        if (ballFallsBelowPlayZone) {
            displayGameOverMessage(g);
        }

        if (allBricksHaveBeenDestroyed) {
            displayWinningMessage(g);
        }

        g.dispose();
    }

    private void drawActiveGameplayElements(Graphics g) {

        drawBackground(g);

        gameBrickMap.draw((Graphics2D) g);

        drawBorders(g);

        drawScore(g);

        drawPaddle(g);

        drawBall(g);

        drawPositionCounters(g);
    }

    private void drawPositionCounters(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 12));
        g.drawString("PaddleX: " + currentPaddlePositionX + "      BallX: " +
                currentBallPositionX + "      BallY: " + currentBallPositionY, 10, 570);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(currentBallPositionX, currentBallPositionY, BALL_WIDTH, BALL_HEIGHT);
    }

    private void drawPaddle(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(currentPaddlePositionX, PADDLE_Y_POS, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + currentScore, 592, 30);
    }

    private void drawBorders(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592); // Left border
        g.fillRect(0, 0, 692, 3); // Top border
        g.fillRect(691, 0, 3, 592); // Right border
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
    }

    private void displayAskToRestartMessage(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Press Enter to restart", 230, 350);
    }

    private void displayGameOverMessage(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 30));
        int highestPossibleScore = INITIAL_BRICKMAP_ROWS * INITIAL_BRICKMAP_COLUMNS * SCORE_PER_BRICK_DESTROYED;
        g.drawString("Game Over! Score: " + currentScore + " / " +
                highestPossibleScore, 190, 300);
    }

    private void displayWinningMessage(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("You Win! Score: " + currentScore, 190, 300);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (currentPaddlePositionX >= MAX_PADDLE_RIGHT_X_POS) {
                currentPaddlePositionX = MAX_PADDLE_RIGHT_X_POS;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (currentPaddlePositionX < MIN_PADDLE_LEFT_X_POS) {
                currentPaddlePositionX = MIN_PADDLE_LEFT_X_POS;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!playState) {
                resetGameplayVariables();
                playState = true;
            }
        }
    }

    private void moveRight() {
        playState = true;
        currentPaddlePositionX += PADDLE_X_MOVEMENT_PER_CLICK;
    }

    private void moveLeft() {
        playState = true;
        currentPaddlePositionX -= PADDLE_X_MOVEMENT_PER_CLICK;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();

        if (playState) {

            // Detects collision of ball and paddle and reverses Y velocity if so
            if (new Rectangle(currentBallPositionX, currentBallPositionY, BALL_WIDTH, BALL_HEIGHT)
                    .intersects(new Rectangle(currentPaddlePositionX, PADDLE_Y_POS, PADDLE_WIDTH, PADDLE_HEIGHT))) {
                ballVelocityY = reverseBallVelocity(ballVelocityY);
            }

            A:
            for (int i = 0; i < gameBrickMap.brickMapArray.length; i++) {
                for (int j = 0; j < gameBrickMap.brickMapArray[0].length; j++) {
                    if (gameBrickMap.brickMapArray[i][j]) {
                        int brickX = j * gameBrickMap.brickWidth + 80;
                        int brickY = i * gameBrickMap.brickHeight + 50;
                        int brickWidth = gameBrickMap.brickWidth;
                        int brickHeight = gameBrickMap.brickHeight;

                        Rectangle brickRectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRectangle = new Rectangle(currentBallPositionX, currentBallPositionY, BALL_WIDTH, BALL_HEIGHT);

                        if (ballRectangle.intersects(brickRectangle)) {

                            gameBrickMap.setBrickValueToFalse(i, j);

                            bricksRemaining--;
                            currentScore += SCORE_PER_BRICK_DESTROYED;

                            if (currentBallPositionX + 19 <= brickRectangle.x || currentBallPositionX + 1 >= brickRectangle.x + brickRectangle.width) {
                                ballVelocityX = -ballVelocityX;
                            } else {
                                ballVelocityY = -ballVelocityY;
                            }

                            break A;
                        }
                    }
                }
            }

            ballMovementLogic();

        }

        repaint();

    }

    private void ballMovementLogic() {

        currentBallPositionX += ballVelocityX;
        currentBallPositionY += ballVelocityY;

        boolean ballIsLeftOfPlayZone = currentBallPositionX < LEFTMOST_ALLOWED_BALL_X_POS;
        boolean ballIsRightOfPlayZone = currentBallPositionX > RIGHTMOST_ALLOWED_BALL_X_POS;

        if (ballIsLeftOfPlayZone || ballIsRightOfPlayZone) {
            ballVelocityX = reverseBallVelocity(ballVelocityX);
        }

        if (currentBallPositionY < TOPMOST_ALLOWED_BALL_Y_POS) {
            ballVelocityY = reverseBallVelocity(ballVelocityY);
        }
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
