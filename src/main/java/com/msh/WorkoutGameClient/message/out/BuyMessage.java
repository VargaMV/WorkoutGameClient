package com.msh.WorkoutGameClient.message.out;

import com.msh.WorkoutGameClient.message.Message;
import com.msh.WorkoutGameClient.message.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyMessage extends Message {

    private String exercise;
    private int amount;

    public BuyMessage() {
    }

    public BuyMessage(String from, String text, String exercise, int amount) {
        super(MessageType.STOCK, from, text);
        this.exercise = exercise;
        this.amount = amount;
    }
}
