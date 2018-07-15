package com.jedduffey.breakout;

import java.awt.*;

class BrickMap {

    private static final double AVG_RATIO_OF_SPECIAL_BRICKS = .25;
    boolean brickMapArray[][];
    private boolean specialBricksArray[][];
    int brickWidth;
    int brickHeight;

    BrickMap(int row, int col) {

        brickMapArray = new boolean[row][col];
        specialBricksArray = new boolean[row][col];

        setAllBricksToTrue(brickMapArray);
        setSpecialBricks(specialBricksArray);

        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    private void setAllBricksToTrue(boolean map[][]) {

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = true;
            }
        }
    }

    private void setSpecialBricks(boolean map[][]) {

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (Math.random() < AVG_RATIO_OF_SPECIAL_BRICKS) {
                    map[i][j] = true;
                }
            }
        }
    }

    void draw(Graphics2D g) {

        for (int i = 0; i < brickMapArray.length; i++) {

            for (int j = 0; j < brickMapArray[0].length; j++) {

                if (brickMapArray[i][j]) {

                    if (specialBricksArray[i][j]) {
                        g.setColor(Color.yellow);
                    } else
                        g.setColor(Color.white);

                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.gray);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    void setBrickValueToFalse(int row, int col) {
        brickMapArray[row][col] = false;
    }

}
