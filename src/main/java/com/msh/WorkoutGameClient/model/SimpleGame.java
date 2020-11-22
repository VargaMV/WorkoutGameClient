package com.msh.WorkoutGameClient.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleGame {

    private String id;
    private String title;
    private boolean running;
    private boolean subOn;
    private int playerNumber;

    public SimpleGame() {
    }

    public SimpleGame(String id, String title, boolean running, boolean subOn, int playerNumber) {
        this.id = id;
        this.title = title;
        this.running = running;
        this.subOn = subOn;
        this.playerNumber = playerNumber;
    }

    @Override
    public String toString() {
        return "SimpleGame{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", running=" + running +
                ", subOn=" + subOn +
                ", playerNumber=" + playerNumber +
                '}';
    }
}
