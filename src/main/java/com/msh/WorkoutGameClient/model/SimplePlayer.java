package com.msh.WorkoutGameClient.model;

import com.msh.WorkoutGameClient.logic.ColorConverter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplePlayer {

    private String name;
    private Color color;

    public SimplePlayer() {

    }

    public SimplePlayer(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public SimplePlayer(Player player) {
        this.name = player.getName();
        this.color = player.getColor();
    }

    public java.awt.Color getAwtColor() {
        return ColorConverter.convertToAWT(color);
    }
}
