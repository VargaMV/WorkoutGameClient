package com.msh.WorkoutGameClient.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SimpleMessage {
    String from;
    String text;

    public SimpleMessage() {

    }

    public SimpleMessage(String from, String text) {
        this.from = from;
        this.text = text;
    }
}
