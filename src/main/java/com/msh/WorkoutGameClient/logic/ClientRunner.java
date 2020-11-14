package com.msh.WorkoutGameClient.logic;

import com.msh.WorkoutGameClient.gui.MainFrame;
import com.msh.WorkoutGameClient.model.Game;
import com.msh.WorkoutGameClient.websocket.WebSocketManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;

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
