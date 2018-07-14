package com.jedduffey.breakout;

import java.awt.*;

public class BrickMapGenerator {

    public boolean brickMapArray[][];
    public int brickWidth;
    public int brickHeight;

    public BrickMapGenerator(int row, int col) {

        brickMapArray = new boolean[row][col];

        setAllBricksToTrue(brickMapArray);

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

    public void draw(Graphics2D g) {

        for (int i = 0; i < brickMapArray.length; i++) {
            for (int j = 0; j < brickMapArray[0].length; j++) {
                if (brickMapArray[i][j] == true) {
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(boolean value, int row, int col) {
        brickMapArray[row][col] = value;
    }

}
