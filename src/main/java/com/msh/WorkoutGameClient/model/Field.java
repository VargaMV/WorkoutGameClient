package com.msh.WorkoutGameClient.model;

import com.msh.WorkoutGameClient.logic.ColorConverter;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Field implements Serializable {
    private SimplePlayer owner;
    private int value;
    private List<SimplePlayer> playersOnField;

    public Field() {
        owner = new SimplePlayer("-", Color.WHITE);
        value = 0;
        playersOnField = new ArrayList<>();
    }

    public Color getColor() {
        return owner.getColor();
    }

    public java.awt.Color getAwtColor() {
        return ColorConverter.convertToAWT(getColor());
    }
}
