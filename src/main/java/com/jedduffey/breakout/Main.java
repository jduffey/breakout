package com.jedduffey.breakout;

import javax.swing.*;

public class Main {

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_TOP_LEFT_X_POS = 100;
    private static final int FRAME_TOP_LEFT_Y_POS = 100;
    private static final String FRAME_TITLE = "Breakout Ball";

    public static void main(String[] args) {

        JFrame jFrame = new JFrame();
        Gameplay gamePlay = new Gameplay();
        jFrame.setBounds(FRAME_TOP_LEFT_X_POS, FRAME_TOP_LEFT_Y_POS, FRAME_WIDTH, FRAME_HEIGHT);
        jFrame.setTitle(FRAME_TITLE);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePlay);
    }
}
