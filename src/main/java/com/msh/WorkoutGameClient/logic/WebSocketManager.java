package com.msh.WorkoutGameClient.logic;

import com.msh.WorkoutGameClient.config.MyStompSessionHandler;
import com.msh.WorkoutGameClient.message.Message;
import com.msh.WorkoutGameClient.message.MessageType;
import com.msh.WorkoutGameClient.message.MoveMessage;
import com.msh.WorkoutGameClient.model.Coordinate;
import com.msh.WorkoutGameClient.model.Game;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class WebSocketManager {

    //private static final String URL = "ws://localhost:8082/action";
    private Game game;
    private WebSocketClient client;
    private WebSocketStompClient stompClient;
    private StompSession session;
    private JFrame gui;

    public WebSocketManager(Game game) {
        this.game = game;
        client = new StandardWebSocketClient();
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
        session.send("/app/action/move", new MoveMessage(MessageType.MOVE, game.getMe().getName(), "I just moved!", from, to));
    }

    public void setGUI(JFrame gui) {
        this.gui = gui;
    }
}
