package com.msh.WorkoutGameClient.message.in;

import com.msh.WorkoutGameClient.model.SimpleGame;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GamesResponse extends SimpleResponse {

    List<SimpleGame> games;

    public GamesResponse() {

    }

    public GamesResponse(String from, String text, String response, List<SimpleGame> games) {
        super(from, text, response);
        this.games = games;
    }
}
