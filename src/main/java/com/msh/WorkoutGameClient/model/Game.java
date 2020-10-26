package com.msh.WorkoutGameClient.model;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class Game {

    private Field[][] map = new Field[1][1];
    private Player me = new Player("TEST", Color.BLUE);

    public Game() {

    }

    public Field getField(int i, int j) {
        return map[i][j];
    }

    public Field getField(Coordinate coordinate) {
        return map[coordinate.getX()][coordinate.getY()];
    }

    public boolean amIWorthy() {
        int x = me.getPosition().getX();
        int y = me.getPosition().getY();
        System.out.println("field value: " + map[x][y].getValue());
        System.out.println("score" + me.getCurrentScore());
        return (map[x][y].getValue() < me.getCurrentScore())
                || (getField(x, y).getColor() == me.getColor() && me.getCurrentScore() > 0);
    }

    public void occupyOrIncrease() {
        int x = me.getPosition().getX();
        int y = me.getPosition().getY();
        if (getField(x, y).getColor() == me.getColor()) {
            updateCurrentFieldValue(me.getCurrentScore(), true);
        } else {
            updateCurrentFieldValue(me.getCurrentScore(), false);
            updateCurrentFieldOwner();
        }
        me.setCurrentScore(0);
    }

    public void updateCurrentFieldOwner() {
        int x = me.getPosition().getX();
        int y = me.getPosition().getY();
        Field currentField = map[x][y];
        currentField.setOwner(new SimplePlayer(me));
    }

    public void updateCurrentFieldValue(int value, boolean add) {
        int x = me.getPosition().getX();
        int y = me.getPosition().getY();
        int prevValue = map[x][y].getValue();
        map[x][y].setValue(add ? (prevValue + value) : value);
    }

    @Override
    public String toString() {
        return map.length + " " + me;
    }
}
