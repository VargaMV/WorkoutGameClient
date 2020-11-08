package com.msh.WorkoutGameClient.message.out;

import com.msh.WorkoutGameClient.message.Message;
import com.msh.WorkoutGameClient.message.MessageType;
import com.msh.WorkoutGameClient.model.Coordinate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OccupationMessage extends Message {

    Coordinate occupiedField;

    public OccupationMessage() {
    }

    public OccupationMessage(String from, String text, Coordinate occupiedField) {
        super(MessageType.OCCUPY, from, text);
        this.occupiedField = occupiedField;
    }
}
