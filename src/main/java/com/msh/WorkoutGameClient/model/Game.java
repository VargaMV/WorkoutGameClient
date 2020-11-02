package com.msh.WorkoutGameClient.model;

import com.msh.WorkoutGameClient.logic.PriceCalculator;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Game {

    private Field[][] map = new Field[1][1];
    private Player me = new Player("DEFAULT", Color.BLUE);
    private Map<String, Integer> totalStockNumbers = new LinkedHashMap<>();
    private Map<String, Integer> exerciseValues = new LinkedHashMap<>();
    private boolean retrievedDataFromServer = false;

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

    public void exerciseDone(String exercise, int amount) {
        me.incExerciseValue(exercise, amount);
        me.incScore((int) Math.ceil(exerciseValues.get(exercise) * getSharePercentage(exercise) / 100.0) * amount);
    }

    public int getSharePercentage(String exercise) {
        return (int) Math.floor(me.getStockNumbers().get(exercise) * 100 / (double) totalStockNumbers.get(exercise));
    }

    public void buyStock(String exercise) {
        if (me.isStockAffordable(exercise)) {
            int prevValue = totalStockNumbers.get(exercise);
            totalStockNumbers.put(exercise, prevValue + 1);
            int myPrevValue = me.getStockNumbers().get(exercise);
            me.getStockNumbers().put(exercise, myPrevValue + 1);

            int price = PriceCalculator.calculate(totalStockNumbers.get(exercise));
            me.decMoney(price);
        }
    }

    public void resetTimer() {
        me.setSecondsUntilMove(120);
    }

    public void secondPast() {
        me.setSecondsUntilMove(Math.max(0, me.getSecondsUntilMove() - 1));
    }

    @Override
    public String toString() {
        return map.length + " " + me;
    }
}
