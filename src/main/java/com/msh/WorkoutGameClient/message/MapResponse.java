package com.msh.WorkoutGameClient.message;

import com.msh.WorkoutGameClient.model.Field;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

@Getter
@Setter
@JsonComponent
public class MapResponse extends SimpleResponse {

    private Field[][] map;

    public MapResponse() {

    }

    public MapResponse(String from, String text, String response, Field[][] map) {
        super(from, text, response);
        this.map = map;
    }

    @Override
    public String toString() {
        if (map == null) {
            return super.toString() + ", no map is available";
        }
        return super.toString() + ", map size: " + map.length;
    }
}
