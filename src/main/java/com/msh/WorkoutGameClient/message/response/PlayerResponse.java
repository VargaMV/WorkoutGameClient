package com.msh.WorkoutGameClient.message.response;

import com.msh.WorkoutGameClient.message.response.SimpleResponse;
import com.msh.WorkoutGameClient.model.Player;
import lombok.Getter;

@Getter
public class PlayerResponse extends SimpleResponse {

    private Player player;

    public PlayerResponse() {

    }

    public PlayerResponse(String from, String text, String response, Player player) {
        super(from, text, response);
        this.player = player;
    }
}
