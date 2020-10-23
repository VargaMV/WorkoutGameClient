package com.msh.WorkoutGameClient.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleResponse<T> {
    String from;
    String text;
    T response;

    public SimpleResponse() {

    }

    public SimpleResponse(String from, String text, T response) {
        this.from = from;
        this.text = text;
        this.response = response;
    }
}
