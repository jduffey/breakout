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

                if (Math.random() < 0.2) {
                    map[i][j] = BrickType.YELLOW;
                }
                if (Math.random() < 0.1) {
                    map[i][j] = BrickType.ORANGE;
                }
                if (Math.random() < 0.05) {
                    map[i][j] = BrickType.RED;
                }
            }
        }
    }

    void draw(Graphics2D g) {

        for (int i = 0; i < brickMapArray.length; i++) {

            for (int j = 0; j < brickMapArray[0].length; j++) {

                if (brickMapArray[i][j] == BrickType.WHITE) {

                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.gray);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                } else if (brickMapArray[i][j] == BrickType.YELLOW) {

                    g.setColor(Color.yellow);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.gray);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                } else if (brickMapArray[i][j] == BrickType.ORANGE) {

                    g.setColor(Color.orange);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.gray);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                } else if (brickMapArray[i][j] == BrickType.RED) {

                    g.setColor(Color.RED);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.gray);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    void setBrickValueToDead(int row, int col) {
        brickMapArray[row][col] = BrickType.DEAD;
    }
}
