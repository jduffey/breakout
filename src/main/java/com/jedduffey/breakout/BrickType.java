package com.jedduffey.breakout;

import java.awt.*;

public enum BrickType {

    B00(Gameplay.BACKGROUND_COLOR, 0),
    B01(Color.WHITE, BrickTypePointValueConstants.B01_PTS),
    B02(Color.GREEN, BrickTypePointValueConstants.B02_PTS),
    B03(Color.CYAN, BrickTypePointValueConstants.B03_PTS),
    B04(Color.YELLOW, BrickTypePointValueConstants.B04_PTS),
    B05(Color.ORANGE, BrickTypePointValueConstants.B05_PTS),
    B06(Color.RED, BrickTypePointValueConstants.B06_PTS);

    public Color color;
    public int pointValue;
    public BrickType afterHitGoesToThisBrickType;

    BrickType(Color color, int pointValue) {
        this.color = color;
        this.pointValue = pointValue;
    }

    private static class BrickTypePointValueConstants {
        public static final int B01_PTS = 5;
        public static final int B02_PTS = 6;
        public static final int B03_PTS = 7;
        public static final int B04_PTS = 8;
        public static final int B05_PTS = 9;
        public static final int B06_PTS = 10;
    }
}