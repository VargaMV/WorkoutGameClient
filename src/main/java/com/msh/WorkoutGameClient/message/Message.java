package com.msh.WorkoutGameClient.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter
public class Message {
    MessageType type;
    String from;
    String text;

    public Message() {

    }

    public Message(MessageType type, String from, String text) {
        this.type = type;
        this.from = from;
        this.text = text;
    }
}
