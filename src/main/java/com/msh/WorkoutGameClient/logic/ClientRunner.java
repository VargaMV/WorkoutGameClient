package com.msh.WorkoutGameClient.logic;

import com.msh.WorkoutGameClient.config.MyStompSessionHandler;
import com.msh.WorkoutGameClient.gui.MainFrame;
import com.msh.WorkoutGameClient.model.Game;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.swing.*;
import java.util.Scanner;

@Component
public class ClientRunner implements CommandLineRunner {

    private Game game;
    private JFrame mainFrame;
    private WebSocketManager webSocketManager;

    @Override
    public void run(String... args) throws Exception {
        game = new Game();
        webSocketManager = new WebSocketManager(game);
        mainFrame = new MainFrame(game, webSocketManager);

        webSocketManager.setGUI(mainFrame);
    }
}
