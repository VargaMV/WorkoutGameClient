package com.msh.WorkoutGameClient.message.out;

import com.msh.WorkoutGameClient.message.Message;
import com.msh.WorkoutGameClient.message.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseMessage extends Message {

    private String exercise;
    private double amount;

    public ExerciseMessage() {
    }

    public ExerciseMessage(String from, String text, String exercise, double amount) {
        super(MessageType.EXERCISE, from, text);
        this.exercise = exercise;
        this.amount = amount;
    }
}
