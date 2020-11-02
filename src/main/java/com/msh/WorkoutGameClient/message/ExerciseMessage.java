package com.msh.WorkoutGameClient.message;

import com.msh.WorkoutGameClient.model.Coordinate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseMessage extends Message {

    private String exercise;
    private int amount;

    public ExerciseMessage() {
    }

    public ExerciseMessage(String from, String text, String exercise, int amount) {
        super(MessageType.EXERCISE, from, text);
        this.exercise = exercise;
        this.amount = amount;
    }
}
