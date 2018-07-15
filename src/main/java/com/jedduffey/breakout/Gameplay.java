package com.jedduffey.breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private static final int TIMER_DELAY_VALUE = 6; // Tutorial value is 8

    private static final int INITIAL_BALL_X_VEL = -1; // Tutorial value is -1
    private static final int INITIAL_BALL_Y_VEL = -2; // Tutorial value is -2
    private static final int INITIAL_BALL_Y_POS = 350; // Tutorial value is 350

    private static final int INITIAL_BRICKMAP_ROWS = 3; // Tutorial value is 3
    private static final int INITIAL_BRICKMAP_COLUMNS = 7; // Tutorial value is 7
    private static final int PADDLE_X_MOVEMENT_PER_CLICK = 20; // Tutorial value is 20

    private static final int PADDLE_Y_POS = 550; // Tutorial value is 550
    private static final int MIN_PADDLE_LEFT_X_POS = 0; // Tutorial value is 10
    private static final int MAX_PADDLE_RIGHT_X_POS = 700; // Tutorial value is 600
    private static final int LEFTMOST_ALLOWED_BALL_X_POS = 0; // Tutorial value is 0
    private static final int RIGHTMOST_ALLOWED_BALL_X_POS = 670; // Tutorial value is 670
    private static final int TOPMOST_ALLOWED_BALL_Y_POS = 0; // Tutorial value is 0
    private static final int BOTTOMMOST_ALLOWED_BALL_Y_POS = 570; // Tutorial value is 570

    private static final int INITIAL_PADDLE_WIDTH = 100; // Tutorial value is 100
    private static final int PADDLE_HEIGHT = 8; // Tutorial value is 8
    private static final int BALL_WIDTH = 20; // Tutorial value is 20
    private static final int BALL_HEIGHT = 20; // Tutorial value is 20

    static final Color BACKGROUND_COLOR = Color.DARK_GRAY;

    private boolean playState;
    private int currentScore;
    private int bricksRemaining;
    private int possiblePoints;

    private Timer timer;

    private int currentPaddlePositionX;
    private int currentBallPositionX;
    private int currentBallPositionY;
    private int ballVelocityX;
    private int ballVelocityY;

    private int paddleWidth;

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
        currentScore = 0;
        paddleWidth = INITIAL_PADDLE_WIDTH;

        currentPaddlePositionX = Main.FRAME_WIDTH / 2 - INITIAL_PADDLE_WIDTH / 2;

        currentBallPositionX = (int) (50 + Math.random() * 600);

        currentBallPositionY = INITIAL_BALL_Y_POS;

        if (Math.random() < 0.5) {
            ballVelocityX = INITIAL_BALL_X_VEL;
        } else ballVelocityX = reverseBallVelocity(INITIAL_BALL_X_VEL);

        ballVelocityY = INITIAL_BALL_Y_VEL;

        bricksRemaining = INITIAL_BRICKMAP_ROWS * INITIAL_BRICKMAP_COLUMNS;
        gameBrickMap = new BrickMap(INITIAL_BRICKMAP_ROWS, INITIAL_BRICKMAP_COLUMNS);
        repaint();

        possiblePoints = 0;
        for (int i = 0; i < gameBrickMap.brickMapArray.length; i++) {
            for (int j = 0; j < gameBrickMap.brickMapArray[0].length; j++) {
                possiblePoints += gameBrickMap.brickMapArray[i][j].pointValue;
            }
        }
    }

    public void paint(Graphics g) {

        drawActiveGameplayElements(g);

        boolean allBricksHaveBeenDestroyed = bricksRemaining <= 0;
        boolean ballFallsBelowPlayZone = currentBallPositionY > BOTTOMMOST_ALLOWED_BALL_Y_POS;

        if (allBricksHaveBeenDestroyed || ballFallsBelowPlayZone) {
            playState = false;
            freezeBallMovement();
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

    private void freezeBallMovement() {
        ballVelocityX = 0;
        ballVelocityY = 0;
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
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 12));
        g.drawString("PaddleX: " + currentPaddlePositionX, 10, 570);
        g.drawString("BallX: " + currentBallPositionX, 110, 570);
        g.drawString("BallY: " + currentBallPositionY, 210, 570);
        g.drawString("XVel: " + ballVelocityX, 310, 570);
        g.drawString("YVel: " + ballVelocityY, 410, 570);
        g.drawString("PaddleWidth: " + paddleWidth, 510, 570);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(currentBallPositionX, currentBallPositionY, BALL_WIDTH, BALL_HEIGHT);
    }

    private void drawPaddle(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(currentPaddlePositionX, PADDLE_Y_POS, paddleWidth, PADDLE_HEIGHT);
        g.setColor(Color.RED);
        g.fillRect(currentPaddlePositionX, PADDLE_Y_POS, 1, PADDLE_HEIGHT);
        g.fillRect(currentPaddlePositionX + paddleWidth, PADDLE_Y_POS, 1, PADDLE_HEIGHT);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + currentScore + " / " + possiblePoints, 592, 30);
    }

    private void drawBorders(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 3, 592); // Left border
        g.fillRect(0, 0, 692, 3); // Top border
        g.fillRect(691, 0, 3, 592); // Right border
    }

    private void drawBackground(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(1, 1, 692, 592);
    }

    private void displayAskToRestartMessage(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Press Enter to restart", 230, 350);
    }

    private void displayGameOverMessage(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("Game Over! Score: " + currentScore, 190, 300);
    }

    private void displayWinningMessage(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("You Win! Score: " + currentScore, 190, 300);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (currentPaddlePositionX + paddleWidth >= MAX_PADDLE_RIGHT_X_POS) {
                currentPaddlePositionX = MAX_PADDLE_RIGHT_X_POS - paddleWidth;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (currentPaddlePositionX <= MIN_PADDLE_LEFT_X_POS) {
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

            logicIfBallCollidesWithPaddle();

            A:
            for (int i = 0; i < gameBrickMap.brickMapArray.length; i++) {

                for (int j = 0; j < gameBrickMap.brickMapArray[0].length; j++) {

                    if (gameBrickMap.brickMapArray[i][j] != BrickType.DEAD) {

                        int brickX = j * gameBrickMap.brickWidth + 80;
                        int brickY = i * gameBrickMap.brickHeight + 50;
                        int brickWidth = gameBrickMap.brickWidth;
                        int brickHeight = gameBrickMap.brickHeight;

                        Rectangle brickRectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRectangle = new Rectangle(currentBallPositionX, currentBallPositionY, BALL_WIDTH, BALL_HEIGHT);

                        if (ballRectangle.intersects(brickRectangle)) {

                            if (gameBrickMap.brickMapArray[i][j] == BrickType.RED || gameBrickMap.brickMapArray[i][j] ==
                                    BrickType.ORANGE || gameBrickMap.brickMapArray[i][j] == BrickType.YELLOW) {
                                this.paddleWidth += 20;
                            } else
                                this.paddleWidth = INITIAL_PADDLE_WIDTH;

                            if (currentBallPositionX + 19 <= brickRectangle.x || currentBallPositionX + 1 >= brickRectangle.x + brickRectangle.width) {
                                ballVelocityX = -ballVelocityX;
                            } else {
                                ballVelocityY = -ballVelocityY;
                            }

                            if (gameBrickMap.brickMapArray[i][j] == BrickType.GREEN || gameBrickMap.brickMapArray[i][j] == BrickType.BLUE) {
                                gameBrickMap.brickMapArray[i][j] = BrickType.WHITE;
                            } else{
                                gameBrickMap.setBrickValueToDead(i, j);
                                bricksRemaining--;
                                currentScore += gameBrickMap.brickMapArray[i][j].pointValue;
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

    private void logicIfBallCollidesWithPaddle() {

        if (new Rectangle(currentBallPositionX, currentBallPositionY, BALL_WIDTH, BALL_HEIGHT)
                .intersects(new Rectangle(currentPaddlePositionX, PADDLE_Y_POS, paddleWidth, PADDLE_HEIGHT))) {
            ballVelocityY = reverseBallVelocity(ballVelocityY);
        }
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
