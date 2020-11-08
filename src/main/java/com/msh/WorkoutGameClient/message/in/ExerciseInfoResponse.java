package com.msh.WorkoutGameClient.message.in;

import lombok.Getter;

import java.util.Map;

@Getter
public class ExerciseInfoResponse extends SimpleResponse {

    private Map<String, Integer> information;

    public ExerciseInfoResponse() {

    }

    public ExerciseInfoResponse(String from, String text, String response, Map<String, Integer> information) {
        super(from, text, response);
        this.information = information;
    }
}
