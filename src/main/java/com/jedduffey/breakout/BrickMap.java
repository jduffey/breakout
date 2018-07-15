package com.jedduffey.breakout;

import java.awt.*;

class BrickMap {

    BrickType brickMapArray[][];

    int brickWidth;
    int brickHeight;

    BrickMap(int row, int col) {

        brickMapArray = new BrickType[row][col];

        assignBrickStatuses(brickMapArray);

        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    private void assignBrickStatuses(BrickType map[][]) {

        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map[0].length; j++) {

                map[i][j] = BrickType.WHITE;

                double random = Math.random();

                if (random < 0.5 && random >= 0.25) {
                    map[i][j] = BrickType.YELLOW;
                }
                if (random < 0.25 && random >= 0.10) {
                    map[i][j] = BrickType.ORANGE;
                }
                if (random < 0.10) {
                    map[i][j] = BrickType.RED;
                }
            }
        }
    }

    void draw(Graphics2D g) {

        for (int i = 0; i < brickMapArray.length; i++) {

            for (int j = 0; j < brickMapArray[0].length; j++) {

                g.setColor(brickMapArray[i][j].color);
                g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                g.setStroke(new BasicStroke(3));
                g.setColor(Color.BLACK);
                g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

            }
        }
    }


    void setBrickValueToDead(int row, int col) {
        brickMapArray[row][col] = BrickType.DEAD;
    }
}
