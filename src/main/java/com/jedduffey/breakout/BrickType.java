package com.jedduffey.breakout;

import java.awt.*;

public enum BrickType {

    B00(Gameplay.BACKGROUND_COLOR, PointValue.B00_PTS),
    B01(Color.WHITE, PointValue.B01_PTS),
    B02(Color.GREEN, PointValue.B02_PTS),
    B03(Color.CYAN, PointValue.B03_PTS),
    B04(Color.YELLOW, PointValue.B04_PTS),
    B05(Color.ORANGE, PointValue.B05_PTS),
    B06(Color.RED, PointValue.B06_PTS);

    public Color color;
    public int pointValue;

    BrickType(Color color, int pointValue) {
        this.color = color;
        this.pointValue = pointValue;
    }

    private static class PointValue {
        public static final int B00_PTS = 0;
        public static final int B01_PTS = 5;
        public static final int B02_PTS = 6;
        public static final int B03_PTS = 7;
        public static final int B04_PTS = 8;
        public static final int B05_PTS = 9;
        public static final int B06_PTS = 10;
    }

}