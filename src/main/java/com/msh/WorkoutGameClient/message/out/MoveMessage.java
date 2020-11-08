package com.msh.WorkoutGameClient.message.out;


import com.msh.WorkoutGameClient.message.Message;
import com.msh.WorkoutGameClient.message.MessageType;
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

    public MoveMessage(String from, String text, Coordinate prevPos, Coordinate newPos) {
        super(MessageType.MOVE, from, text);
        this.prevPos = prevPos;
        this.newPos = newPos;
    }
}
