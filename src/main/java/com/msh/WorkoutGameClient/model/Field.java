package com.msh.WorkoutGameClient.model;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Field implements Serializable {
    private Player owner;
    private int value;
    private List<Player> playersOnField;

    public Field() {
        owner = new Player("Mr.Nobody", Color.WHITE);
        value = 0;
        playersOnField = new ArrayList<>();
    }

    public void addPlayerToField(Player player) {
        playersOnField.add(player);
    }

    public Player removePlayerFromField(Player player) {
        int ind = playersOnField.indexOf(player);
        return playersOnField.remove(ind);
    }

    public Color getColor() {
        return owner.getColor();
    }

    public java.awt.Color getAwtColor() {
        switch (getColor()) {
            case RED:
                return java.awt.Color.RED;
            case GREEN:
                return java.awt.Color.GREEN;
            case WHITE:
                return java.awt.Color.WHITE;
        }
        return java.awt.Color.WHITE;
    }
}
