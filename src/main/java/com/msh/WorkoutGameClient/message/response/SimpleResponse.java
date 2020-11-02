package com.msh.WorkoutGameClient.message.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SimpleResponse implements Serializable {
    String from;
    String text;
    String response;

    public SimpleResponse() {

    }

    public SimpleResponse(String from, String text, String response) {
        this.from = from;
        this.text = text;
        this.response = response;
    }

    @Override
    public String toString() {
        return from + ": " + text + ", " + response;
    }
}
