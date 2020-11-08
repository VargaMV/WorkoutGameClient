package com.msh.WorkoutGameClient.message.out;

import com.msh.WorkoutGameClient.message.Message;
import com.msh.WorkoutGameClient.message.MessageType;
import com.msh.WorkoutGameClient.model.LoginUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthMessage extends Message {

    private LoginUser user;

    public AuthMessage() {
    }

    public AuthMessage(MessageType type, String from, String text, LoginUser user) {
        super(type, from, text);
        this.user = user;
    }
}
