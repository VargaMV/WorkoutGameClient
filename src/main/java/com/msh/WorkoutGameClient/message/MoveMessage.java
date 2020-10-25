package com.msh.WorkoutGameClient.message;


import com.msh.WorkoutGameClient.model.Coordinate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveMessage extends Message {

    private Coordinate prevPos;
    private Coordinate newPos;

    public MoveMessage() {
    }

    public MoveMessage(MessageType type, String from, String text, Coordinate prevPos, Coordinate newPos) {
        super(type, from, text);
        this.prevPos = prevPos;
        this.newPos = newPos;
    }
}
