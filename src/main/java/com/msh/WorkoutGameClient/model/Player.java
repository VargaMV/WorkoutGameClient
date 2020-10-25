package com.msh.WorkoutGameClient.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Player implements Comparable<Player>, Serializable {
    String name;
    Color color;
    Coordinate position;
    int money;
    int currentScore;
    int totalScore;
    int rangeOfVision;
    boolean maxRange;

    public Player() {

    }

    Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.position = new Coordinate(0, 0);
        this.money = 0;
        this.currentScore = 0;
        this.totalScore = 0;
        this.rangeOfVision = 1;
    }

    @Override
    public int compareTo(Player o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public java.awt.Color getAwtColor() {
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
                return new java.awt.Color(100, 10, 100);
        }
        return java.awt.Color.GRAY;
    }

    @Override
    public String toString() {
        return name + " -> " + color + " -> pos:" + position;
    }
}
