package com.msh.WorkoutGameClient.websocket;

import com.msh.WorkoutGameClient.gui.LoginPanel;
import com.msh.WorkoutGameClient.gui.MainFrame;
import com.msh.WorkoutGameClient.logic.PriceCalculator;
import com.msh.WorkoutGameClient.message.MessageType;
import com.msh.WorkoutGameClient.message.in.*;
import com.msh.WorkoutGameClient.model.Field;
import com.msh.WorkoutGameClient.model.Game;
import com.msh.WorkoutGameClient.model.Player;
import com.msh.WorkoutGameClient.model.SimpleGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.*;

import javax.swing.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private Game game;
    private final Logger logger = LogManager.getLogger(MyStompSessionHandler.class);
    private JFrame gui;

    public MyStompSessionHandler(Game game, JFrame gui) {
        this.game = game;
        this.gui = gui;
    }

    @Override
    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
        stompSession.subscribe("/public", this);
        stompSession.subscribe("/private/games/" + stompSession.getSessionId(), this);
        stompSession.subscribe("/public/games", this);
        stompSession.subscribe("/public/stop", this);
    }

    @Override
    public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
        logger.error("Exception at " + stompHeaders.getDestination(), throwable);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        MainFrame mainFrame = (MainFrame) gui;
        mainFrame.switchToLogin();
        ((LoginPanel) mainFrame.getLoginPanel()).writeFeedback("Couldn't connect to server.");
        logger.error("Transport exception: ", throwable);
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        String[] destination = Objects.requireNonNull(stompHeaders.getDestination()).split("/");
        if (destination.length > 2) {
            if (destination[2].equals("games")) {
                return GamesResponse.class;
            }
            if (destination[1].equals("public")) {
                return SimpleResponse.class;
            } else if (destination[1].equals("private")) {
                switch (destination[3]) {
                    case "game":
                        return GameResponse.class;
                    case "player":
                        return PlayerResponse.class;
                    case "map":
                        return MapResponse.class;
                    case "stock":
                        return StockResponse.class;
                    default:
                        return SimpleResponse.class;
                }
            }
        }
        return SimpleResponse.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object payload) {
        SimpleResponse msg = (SimpleResponse) payload;
        MainFrame mainFrame = (MainFrame) gui;
        List<String> connectionResponses = Arrays.asList("USED", "SUB", "OFF", "INVALID", "SUCCESS", "STOP");
        if (connectionResponses.contains(msg.getResponse())) {
            ((LoginPanel) mainFrame.getLoginPanel()).writeFeedback(msg.getText());
        }

        switch (msg.getResponse()) {
            case "STOP":
                ((MainFrame) gui).exit();
                break;
            case "GAMES":
                GamesResponse gamesMsg = (GamesResponse) payload;
                List<SimpleGame> games = gamesMsg.getGames();
                games.forEach(System.out::println);
                ((MainFrame) gui).updateLoginPanel(games);
                break;
            case "GAME":
                //TODO: if needed? if yes, why?
                if (!game.isRetrievedDataFromServer()) {
                    GameResponse gameMsg = (GameResponse) payload;
                    Game game = gameMsg.getGame();
                    this.game.setServerGameState(game);
                    ((MainFrame) gui).createMenuBar();
                    ((MainFrame) gui).initPanels();
                    ((MainFrame) gui).updatePanels();
                    ((MainFrame) gui).switchToMain();
                    game.setRetrievedDataFromServer(true);
                }
                break;
            case "PLAYER":
                PlayerResponse playerMsg = (PlayerResponse) payload;
                Player player = playerMsg.getPlayer();
                this.game.setMe(player);
                ((MainFrame) gui).updateMainPanel();
                break;
            case "MAP":
                //only use the current game updates
                if (game.getId().equals(msg.getText())) {
                    MapResponse mapMsg = (MapResponse) payload;
                    Field[][] map = mapMsg.getMap();
                    this.game.setMap(map);
                    ((MainFrame) gui).updateMainPanel();
                }
                break;
            case "STOCK":
                if (game.getId().equals(msg.getText())) {
                    StockResponse stockMsg = (StockResponse) payload;
                    Map<String, Integer> totalStocks = stockMsg.getAll();
                    this.game.setTotalStockNumbers(totalStocks);
                    if (msg.getFrom().equals(this.game.getMe().getName())) {
                        this.game.getMe().setStockNumbers(stockMsg.getOwn());
                        this.game.getMe().setMoney(stockMsg.getOwnMoney());
                    }
                    ((MainFrame) gui).updateStockPanel();
                }
                break;
        }
    }

}

