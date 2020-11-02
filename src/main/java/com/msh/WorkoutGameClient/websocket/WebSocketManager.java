package com.msh.WorkoutGameClient.websocket;

import com.msh.WorkoutGameClient.websocket.MyStompSessionHandler;
import com.msh.WorkoutGameClient.message.*;
import com.msh.WorkoutGameClient.model.Coordinate;
import com.msh.WorkoutGameClient.model.Game;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.swing.*;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

public class WebSocketManager {

    private Game game;
    private WebSocketClient client;
    private WebSocketStompClient stompClient;
    private StompSession session;
    private JFrame gui;

    public WebSocketManager(Game game) {
        this.game = game;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(20 * 1024 * 1024);
        client = new StandardWebSocketClient(container);
        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    public void establishConnection(String name, String URL) {
        StompSessionHandler sessionHandler = new MyStompSessionHandler(game, name, gui);
        try {
            session = stompClient.connect(URL, sessionHandler).get();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void sendMove(Coordinate from, Coordinate to) {
        session.send("/app/action/move", new MoveMessage(game.getMe().getName(), "I just moved!", from, to));
    }

    public void sendOccupy(Coordinate field) {
        session.send("/app/action/occupy", new OccupationMessage(game.getMe().getName(), "I have a new field!", field));
    }

    //TODO: StockMessage
    public void sendStockBought(String exercise) {
        session.send("/app/action/stock", new Message(MessageType.STOCK, game.getMe().getName(), exercise));
    }

    public void sendExerciseDone(String exercise, int amount) {
        session.send("/app/action/exercise", new ExerciseMessage(game.getMe().getName(), "I workout!", exercise, amount));
    }

    public void sendVisionInc() {
        session.send("/app/action/vision", new Message(MessageType.STOCK, game.getMe().getName(), "Increase vision!"));
    }

    public void sendConvert(int amount) {
        session.send("/app/action/convert", new ConvertMessage(game.getMe().getName(), "Convert score to money!", amount));
    }

    public void sendSecondsUntilMove(int seconds) {
        session.send("/app/action/time", new TimeMessage(game.getMe().getName(), "Time until move!", seconds));

    }

    public void setGUI(JFrame gui) {
        this.gui = gui;
    }
}
