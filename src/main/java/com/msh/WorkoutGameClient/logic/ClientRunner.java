package com.msh.WorkoutGameClient.logic;

import com.msh.WorkoutGameClient.config.MyStompSessionHandler;
import com.msh.WorkoutGameClient.gui.MainFrame;
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

    private JFrame mainFrame;
    private WebSocketManager webSocketManager;

    @Override
    public void run(String... args) throws Exception {
        webSocketManager = new WebSocketManager();
        mainFrame = new MainFrame(webSocketManager);
    }
}
