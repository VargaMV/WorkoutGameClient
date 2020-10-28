package com.msh.WorkoutGameClient.model;

import com.msh.WorkoutGameClient.logic.ColorConverter;
import com.msh.WorkoutGameClient.logic.PriceCalculator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
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
    private int totalScore;
    private int rangeOfVision;
    private boolean maxRange;
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
        this.totalScore = 0;
        this.rangeOfVision = 1;

        /*File data = new File("data.csv");
        try (Scanner scanner = new Scanner(data)) {
            while (scanner.hasNextLine()) {
                String dataLine = scanner.nextLine();
                String exercise = dataLine.split(",")[0].trim();
                exerciseNumbers.put(exercise, 0);
                stockNumbers.put(exercise, 0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }

    public void incMoney(int earning) {
        money += earning;
    }

    public void decMoney(int cost) {
        money -= cost;
    }

    public boolean isAffordable(String exercise) {
        return money >= PriceCalculator.calculateNext(stockNumbers.get(exercise));
    }

    public int getNextPrice(String exercise) {
        return PriceCalculator.calculateNext(stockNumbers.get(exercise));
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
        return ColorConverter.convertToAWT(color);
    }

    @Override
    public String toString() {
        return name + " -> " + color + " -> pos:" + position;
    }
}
