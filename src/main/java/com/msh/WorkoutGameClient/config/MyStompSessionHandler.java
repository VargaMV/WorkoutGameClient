package com.msh.WorkoutGameClient.config;

import com.msh.WorkoutGameClient.gui.MainFrame;
import com.msh.WorkoutGameClient.message.*;
import com.msh.WorkoutGameClient.model.Field;
import com.msh.WorkoutGameClient.model.Game;
import com.msh.WorkoutGameClient.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.*;

import javax.swing.*;
import java.lang.reflect.Type;
import java.util.Arrays;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private Game game;
    private final Logger logger = LogManager.getLogger(MyStompSessionHandler.class);
    private final String name;
    private JFrame gui;
    private boolean gameSet = false;
    private boolean playerSet = false;

    public MyStompSessionHandler(Game game, String name, JFrame gui) {
        this.game = game;
        this.name = name;
        this.gui = gui;
    }

    @Override
    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
        logger.info("Connected" + stompHeaders);
        stompSession.subscribe("/public", this);
        stompSession.subscribe("/public/map", this);
        stompSession.subscribe("/private/map/" + name, this);
        stompSession.subscribe("/private/player/" + name, this);
        logger.info("Subscribed to /public");
        stompSession.send("/app/action", joinMsg());
        logger.info("Join message sent to webSocket server");
    }

    @Override
    public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
        logger.error("Exception at " + stompHeaders.getDestination(), throwable);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        logger.error("Transport exception: ", throwable);
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        String[] destination = stompHeaders.getDestination().split("/");
        if (destination.length > 2) {
            if (destination[2].equals("map")) {
                return MapResponse.class;
            } else if (destination[2].equals("player")) {
                return PlayerResponse.class;
            }
        }
        return SimpleResponse.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object payload) {
        SimpleResponse msg = (SimpleResponse) payload;
        if (msg.getResponse().equals("MAP")) {
            MapResponse mapMsg = (MapResponse) payload;
            logger.info(mapMsg.getFrom() + " : " + mapMsg.getText());
            Field[][] map = mapMsg.getMap();
            game.setMap(map);
            System.out.println(game.toString());
            gameSet = true;
        } else if (msg.getResponse().equals("PLAYER")) {
            PlayerResponse playerMsg = (PlayerResponse) payload;
            logger.info(playerMsg.getFrom() + " : " + playerMsg.getText());
            Player player = playerMsg.getPlayer();
            game.setMe(player);
            System.out.println(game.toString());
            playerSet = true;
        } else {
            logger.info(msg.getFrom() + " : " + msg.getText());
            logger.info("Response:" + msg.getResponse());
        }


        if (gui != null && playerSet && gameSet && !(msg.getFrom().equals(name) && stompHeaders.getDestination().equals("/public/map"))) {
            ((MainFrame) gui).updateFrame();
            ((MainFrame) gui).switchToMain();

        }
    }

    Message joinMsg() {
        return new Message(MessageType.JOIN, name, "I want to play!");
    }

}

