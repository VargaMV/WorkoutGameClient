package com.msh.WorkoutGameClient.message.in;

import com.msh.WorkoutGameClient.model.Game;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResponse extends SimpleResponse {
    private Game game;

    public GameResponse() {

    }

    public GameResponse(String from, String text, String response, Game game) {
        super(from, text, response);
        this.game = game;
    }
}
