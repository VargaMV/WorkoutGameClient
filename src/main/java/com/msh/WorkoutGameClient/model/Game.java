package com.msh.WorkoutGameClient.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {

    private Field[][] map = new Field[1][1];
    private Player me = new Player("TEST", Color.BLUE);

    public Game() {

    }

    @Override
    public String toString() {
        return map.length + " " + me;
    }
}
