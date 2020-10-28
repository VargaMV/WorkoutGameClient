package com.msh.WorkoutGameClient.message.response;

import com.msh.WorkoutGameClient.model.Player;
import lombok.Getter;

import java.util.Map;

@Getter
public class StockResponse extends SimpleResponse {

    private Map<String, Integer> stocks;

    public StockResponse() {

    }

    public StockResponse(String from, String text, String response, Map<String, Integer> stocks) {
        super(from, text, response);
        this.stocks = stocks;
    }
}
