package com.msh.WorkoutGameClient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Game {

    private String id;
    private Field[][] map = new Field[1][1];
    @JsonProperty("player")
    private Player me = new Player("DEFAULT", Color.BLUE);
    private Map<String, Integer> totalStockNumbers = new LinkedHashMap<>();
    private Map<String, Exercise> exerciseValues = new LinkedHashMap<>();
    private int waitingTime;
    private double priceIncExponent;
    private boolean retrievedDataFromServer = false;

    private int lastConvert = 0;
    private NamedAmount lastSave = new NamedAmount("", 0);

    public Game() {

    }

    public void setServerGameState(Game game) {
        id = game.getId();
        map = game.getMap();
        me = game.getMe();
        totalStockNumbers = game.getTotalStockNumbers();
        exerciseValues = game.getExerciseValues();
        waitingTime = game.getWaitingTime();
        priceIncExponent = game.getPriceIncExponent();
        retrievedDataFromServer = true;

    }

    public Field getField(int i, int j) {
        return map[i][j];
    }

    public Field getField(Coordinate coordinate) {
        return map[coordinate.getX()][coordinate.getY()];
    }

    public boolean isCurrentFieldMine() {
        return getField(me.getPosition()).getOwner().getName().equals(me.getName());
    }

    public boolean amIWorthy() {
        int x = me.getPosition().getX();
        int y = me.getPosition().getY();
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
            me.incFieldsOwned();
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

    public int getTotalStockNumber(String exercise) {
        return totalStockNumbers.get(exercise);
    }

    public void exerciseDone(String exercise, double amount) {
        int roundedAmount = (int) Math.round(amount - 0.25);
        me.incExerciseValue(exercise, roundedAmount);
        me.incScore((int) Math.ceil(exerciseValues.get(exercise).getValue() * getSharePercentage(exercise) / 100.0) * roundedAmount);
    }

    public int getSharePercentage(String exercise) {
        return (int) Math.floor(me.getStockNumbers().get(exercise) * 100 / (double) totalStockNumbers.get(exercise));
    }

    public void convertScoreToMoney(int score) {
        me.decScore(score);
        me.incMoney(score);
    }

    public boolean isFieldMine(int i, int j) {
        return map[i][j].getOwner().getName().equals(me.getName());
    }

    public void resetTimer() {
        me.setSecondsUntilMove(waitingTime);
    }

    public void resetTimer(int seconds) {
        me.setSecondsUntilMove(seconds);
    }

    public void secondPast() {
        me.setSecondsUntilMove(Math.max(0, me.getSecondsUntilMove() - 1));
    }

    @Override
    public String toString() {
        return map.length + " " + me;
    }
}
