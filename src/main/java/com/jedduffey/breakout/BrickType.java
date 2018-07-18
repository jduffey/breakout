package com.jedduffey.breakout;

import java.awt.*;

public enum BrickType {

    DEAD(Gameplay.BACKGROUND_COLOR, 0),
    WHITE(Color.WHITE, 5),
    GREEN(Color.GREEN, 6),
    BLUE(Color.CYAN, 7),
    YELLOW(Color.YELLOW, 8),
    ORANGE(Color.ORANGE, 9),
    RED(Color.RED, 10);

    public Color color;
    public int pointValue;
    public BrickType afterHitGoesToThisBrickType;

    BrickType(Color color, int pointValue) {
        this.color = color;
        this.pointValue = pointValue;
    }

}