package com.msh.WorkoutGameClient.config;

import com.msh.WorkoutGameClient.message.Message;
import com.msh.WorkoutGameClient.message.MsgType;
import com.msh.WorkoutGameClient.message.SimpleMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private final Logger logger = LogManager.getLogger(MyStompSessionHandler.class);
    private String name;

    public MyStompSessionHandler(String name) {
        this.name = name;
    }

    @Override
    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
        logger.info("Connected");
        stompSession.subscribe("/public", this);
        stompSession.subscribe("/private/" + name,this);
        logger.info("Subscribed to /public");
        stompSession.send("/app/action", joinMsg());
        logger.info("Join message sent to webSocket server");
    }

    @Override
    public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
        logger.error("Exception: ", throwable);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {

    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return SimpleMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object payload) {
        SimpleMessage msg = (SimpleMessage) payload;
        logger.info("Received : " + msg.getText() + " from : " + msg.getFrom());
    }

    Message joinMsg() {
        return new Message(MsgType.JOIN, name, "I want in!");
    }

}

