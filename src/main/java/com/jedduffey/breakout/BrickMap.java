package com.jedduffey.breakout;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;

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

        ArrayList allMyEnums = new ArrayList<>(Arrays.asList(BrickType.values()));
        allMyEnums.remove(BrickType.DEAD);

        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map[0].length; j++) {

                Collections.shuffle(allMyEnums);

                map[i][j] = (BrickType) allMyEnums.get(0);

            }
        }
    }

    void draw(Graphics2D g) {

        for (int i = 0; i < brickMapArray.length; i++) {

            for (int j = 0; j < brickMapArray[0].length; j++) {

                g.setColor(brickMapArray[i][j].color);
                g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                g.setStroke(new BasicStroke(3));
                g.setColor(Gameplay.BACKGROUND_COLOR);
                g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

            }
        }
    }


    void setBrickValueToDead(int row, int col) {
        brickMapArray[row][col] = BrickType.DEAD;
    }
}
