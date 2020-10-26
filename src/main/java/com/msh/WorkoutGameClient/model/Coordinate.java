package com.msh.WorkoutGameClient.model;

import lombok.Getter;

@Getter
public class Coordinate {
    private int x;
    private int y;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int distance(int x, int y, boolean max) {
        if (max) {
            return Math.max(Math.abs(this.x - x), Math.abs(this.y - y));
        }
        return Math.abs(this.x - x) + Math.abs(this.y - y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
