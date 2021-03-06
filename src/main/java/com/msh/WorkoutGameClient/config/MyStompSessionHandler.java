package com.msh.WorkoutGameClient.config;

import com.msh.WorkoutGameClient.gui.LoginPanel;
import com.msh.WorkoutGameClient.gui.MainFrame;
import com.msh.WorkoutGameClient.message.*;
import com.msh.WorkoutGameClient.message.response.ExerciseInfoResponse;
import com.msh.WorkoutGameClient.message.response.MapResponse;
import com.msh.WorkoutGameClient.message.response.PlayerResponse;
import com.msh.WorkoutGameClient.message.response.SimpleResponse;
import com.msh.WorkoutGameClient.model.Field;
import com.msh.WorkoutGameClient.model.Game;
import com.msh.WorkoutGameClient.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.*;

import javax.swing.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private Game game;
    private final Logger logger = LogManager.getLogger(MyStompSessionHandler.class);
    private final String name;
    private JFrame gui;
    private boolean mapSet = false;
    private boolean playerSet = false;
    private boolean stockSet = false;
    private boolean exerciseSet = false;
    private boolean allSet = false;

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
        stompSession.subscribe("/public/stock", this);
        stompSession.subscribe("/private/map/" + name, this);
        stompSession.subscribe("/private/player/" + name, this);
        stompSession.subscribe("/private/exercise/" + name, this);
        logger.info("Subscribed to /public");
        stompSession.send("/app/action", new Message(MessageType.JOIN, name, "I want to play!"));
        logger.info("Join message sent to webSocket server");
    }

    @Override
    public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
        logger.error("Exception at " + stompHeaders.getDestination(), throwable);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        MainFrame mainFrame = (MainFrame) gui;
        ((LoginPanel) mainFrame.getLoginPanel()).writeFeedback("Couldn't connect to server.");
        logger.error("Transport exception: ", throwable);
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        String[] destination = Objects.requireNonNull(stompHeaders.getDestination()).split("/");
        if (destination.length > 2) {
            if (destination[2].equals("map")) {
                return MapResponse.class;
            } else if (destination[2].equals("player")) {
                return PlayerResponse.class;
            } else if (destination[2].equals("stock") || destination[2].equals("exercise")) {
                return ExerciseInfoResponse.class;
            }
        }
        return SimpleResponse.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object payload) {
        SimpleResponse msg = (SimpleResponse) payload;
        logger.info(msg.getFrom() + " : " + msg.getText());
        logger.info("Response:" + msg.getResponse());
        MainFrame mainFrame = (MainFrame) gui;
        switch (msg.getResponse()) {
            case "SUB":
                ((LoginPanel) mainFrame.getLoginPanel()).writeFeedback("Subscribed to the game!");
                break;
            case "USED":
                ((LoginPanel) mainFrame.getLoginPanel()).writeFeedback("Already subscribed or the name is taken.");
                break;
            case "OFF":
                ((LoginPanel) mainFrame.getLoginPanel()).writeFeedback("The game has already started or it's full!");
                break;
            default:
                ((LoginPanel) mainFrame.getLoginPanel()).writeFeedback("");
                break;

        }

        switch (msg.getResponse()) {
            case "PLAYER":
                PlayerResponse playerMsg = (PlayerResponse) payload;
                Player player = playerMsg.getPlayer();
                game.setMe(player);
                playerSet = true;
                if (allSet) {
                    ((MainFrame) gui).updateMainPanel();
                }
                break;
            case "MAP":
                if (!allSet || !msg.getFrom().equals(game.getMe().getName())) {
                    MapResponse mapMsg = (MapResponse) payload;
                    Field[][] map = mapMsg.getMap();
                    game.setMap(map);
                    mapSet = true;
                    if (playerSet && allSet) {
                        ((MainFrame) gui).updateMainPanel();
                    }
                }
                break;
            case "STOCK":
                if (!allSet || !msg.getFrom().equals(game.getMe().getName())) {
                    ExerciseInfoResponse stockMsg = (ExerciseInfoResponse) payload;
                    Map<String, Integer> totalStocks = stockMsg.getInformation();
                    game.setTotalStockNumbers(totalStocks);
                    stockSet = true;
                    System.out.println(playerSet);
                    System.out.println(allSet);
                    if (playerSet && allSet) {
                        System.out.println("HERE YOU GO!");
                        ((MainFrame) gui).updateStockPanel();
                    }
                }
                break;
            case "EXERCISE":
                if (!allSet) {
                    ExerciseInfoResponse exerciseMsg = (ExerciseInfoResponse) payload;
                    Map<String, Integer> exerciseValues = exerciseMsg.getInformation();
                    game.setExerciseValues(exerciseValues);
                    exerciseSet = true;
                }
                break;
        }


        //when joining
        if (gui != null && playerSet && mapSet && stockSet && exerciseSet && !allSet) {
            ((MainFrame) gui).initPanels();
            ((MainFrame) gui).createMenuBar();
            ((MainFrame) gui).updateMainPanel();
            ((MainFrame) gui).updateStockPanel();
            ((MainFrame) gui).switchToMain();
            game.setRetrievedDataFromServer(true);
            allSet = true;
        }
    }

}

