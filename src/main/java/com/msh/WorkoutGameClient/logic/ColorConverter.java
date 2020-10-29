package com.msh.WorkoutGameClient.logic;

import com.msh.WorkoutGameClient.model.Color;

public class ColorConverter {
    public static java.awt.Color convertToAWT(Color color) {
        switch (color) {
            case GREEN:
                return java.awt.Color.GREEN;
            case RED:
                return java.awt.Color.RED;
            case ORANGE:
                return java.awt.Color.ORANGE;
            case YELLOW:
                return java.awt.Color.YELLOW;
            case BLUE:
                return java.awt.Color.BLUE;
            case CYAN:
                return java.awt.Color.CYAN;
            case BLACK:
                return java.awt.Color.BLACK;
            case WHITE:
                return java.awt.Color.WHITE;
            case BROWN:
                return new java.awt.Color(140, 90, 50);
            case PURPLE:
                return new java.awt.Color(128, 0, 128);
            case LIME:
                return new java.awt.Color(0, 255, 0);
            case NAVY:
                return new java.awt.Color(0, 0, 128);
            case OLIVE:
                return new java.awt.Color(128, 128, 0);
            case MAGENTA:
                return new java.awt.Color(255, 0, 255);
            case GOLD:
                return new java.awt.Color(218, 165, 32);
            case TEAL:
                return new java.awt.Color(0, 128, 128);
            case MAROON:
                return new java.awt.Color(128, 0, 0);
        }
        return java.awt.Color.PINK;
    }
}
