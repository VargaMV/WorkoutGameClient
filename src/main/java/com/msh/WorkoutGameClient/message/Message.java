package com.msh.WorkoutGameClient.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter
public class Message {
    MessageType type;
    String from;
    String text;

    Message() {

    }

    public Message(MessageType type, String from, String text) {
        this.type = type;
        this.from = from;
        this.text = text;
    }

    @Override
    public String toString() {
        return from + ": " + text + ", " + type;
    }
}
