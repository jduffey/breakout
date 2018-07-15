package com.jedduffey.breakout;

import java.awt.*;

public enum BrickType {

    DEAD(Color.BLACK, 0),
    WHITE(Color.WHITE, 5),
    YELLOW(Color.YELLOW, 6),
    ORANGE(Color.ORANGE, 7),
    RED(Color.RED, 8),
    GREEN(Color.GREEN, 9),
    BLUE(Color.CYAN, 10);

    public Color color;
    public int pointValue;

    BrickType(Color color, int pointValue) {
        this.color = color;
        this.pointValue = pointValue;
    }

}