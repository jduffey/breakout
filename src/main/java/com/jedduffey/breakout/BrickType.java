package com.jedduffey.breakout;

import java.awt.*;

public enum BrickType {

    B00(Gameplay.BACKGROUND_COLOR, 0),
    B01(Color.WHITE, 5),
    B02(Color.GREEN, 6),
    B03(Color.CYAN, 7),
    B04(Color.YELLOW, 8),
    B05(Color.ORANGE, 9),
    B06(Color.RED, 10);

    public Color color;
    public int pointValue;
    public BrickType afterHitGoesToThisBrickType;

    BrickType(Color color, int pointValue) {
        this.color = color;
        this.pointValue = pointValue;
    }

}