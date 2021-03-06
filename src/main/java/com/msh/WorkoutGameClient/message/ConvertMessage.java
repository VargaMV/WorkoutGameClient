package com.msh.WorkoutGameClient.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvertMessage extends Message {

    private int amount;

    public ConvertMessage() {
    }

    public ConvertMessage(String from, String text, int amount) {
        super(MessageType.CONVERT, from, text);
        this.amount = amount;
    }
}
