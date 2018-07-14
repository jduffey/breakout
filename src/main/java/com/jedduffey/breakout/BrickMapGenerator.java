package com.jedduffey.breakout;

import java.awt.*;

public class BrickMapGenerator {

    public int brickMap[][];
    public int brickWidth;
    public int brickHeight;

    public BrickMapGenerator(int row, int col) {

        brickMap = new int[row][col];

        for (int i = 0; i < brickMap.length; i++) {
            for (int j = 0; j < brickMap[0].length; j++) {
                brickMap[i][j] = 1;
            }
        }

        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    public void draw(Graphics2D g) {

        for (int i = 0; i < brickMap.length; i++) {
            for (int j = 0; j < brickMap[0].length; j++) {
                if (brickMap[i][j] > 0) {
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        brickMap[row][col] = value;
    }

}
