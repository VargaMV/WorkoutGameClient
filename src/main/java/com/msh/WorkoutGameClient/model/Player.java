package com.msh.WorkoutGameClient.model;

import com.msh.WorkoutGameClient.logic.ColorConverter;
import com.msh.WorkoutGameClient.logic.PriceCalculator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Player implements Comparable<Player>, Serializable {
    private String name;
    private Color color;
    private Coordinate position;
    private int money;
    private int currentScore;
    private int fieldsOwned;
    private int totalScore;
    private int rangeOfVision;
    private boolean sqrRange;
    private int visionIncPrice;
    private int secondsUntilMove;
    private Map<String, Integer> exerciseNumbers = new LinkedHashMap<>();
    private Map<String, Integer> stockNumbers = new LinkedHashMap<>();

    public Player() {

    }

    Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.position = new Coordinate(0, 0);
        this.money = 0;
        this.currentScore = 0;
        this.fieldsOwned = 0;
        this.totalScore = 0;
        this.rangeOfVision = 1;
        this.secondsUntilMove = 0;
    }

    public void incMoney(int earning) {
        money += earning;
    }

    public void decMoney(int cost) {
        money -= cost;
    }

    public void incScore(int score) {
        currentScore += score;
    }

    public void decScore(int score) {
        currentScore -= score;
    }

    public void incFieldsOwned() {
        fieldsOwned++;
    }

    public boolean isStockAffordable(String exercise) {
        return money >= PriceCalculator.calculateNext(stockNumbers.get(exercise));
    }

    public boolean isVisionIncAffordable() {
        return money >= visionIncPrice;
    }

    public int getNextPrice(String exercise) {
        return PriceCalculator.calculateNext(stockNumbers.get(exercise));
    }

    public void incExerciseValue(String exercise, int newValue) {
        int prevValue = exerciseNumbers.get(exercise);
        exerciseNumbers.put(exercise, prevValue + newValue);
    }

    public void incRangeOfVision() {
        if (!isVisionMax() && isVisionIncAffordable()) {
            if (sqrRange) {
                rangeOfVision++;
                sqrRange = false;
            } else {
                sqrRange = true;
            }
            money -= visionIncPrice;
        }
    }

    public java.awt.Color getAwtColor() {
        return ColorConverter.convertToAWT(color);
    }

    public boolean isVisionMax() {
        return (sqrRange && rangeOfVision == 2);
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

    @Override
    public String toString() {
        return name + " -> " + color + " -> pos:" + position;
    }
}
