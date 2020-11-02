package com.msh.WorkoutGameClient.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeMessage extends Message {

    private int seconds;

    public TimeMessage() {
    }

    public TimeMessage(String from, String text, int seconds) {
        super(MessageType.TIME, from, text);
        this.seconds = seconds;
    }
}
