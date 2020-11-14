package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.logic.ColorConverter;
import com.msh.WorkoutGameClient.websocket.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Header extends JPanel {

    private final Game game;
    private WebSocketManager wsm;
    private JLabel playerNameLabel;
    private JLabel moneyLabel;
    private JLabel scoreLabel;
    private JLabel fieldsLabel;

    public Header(Game game, WebSocketManager wsm) {
        this.game = game;
        this.wsm = wsm;
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
        setPreferredSize(new Dimension(500, 60));

        playerNameLabel = new JLabel("");
        moneyLabel = new JLabel("");
        scoreLabel = new JLabel("");
        fieldsLabel = new JLabel("");

        List<JLabel> labels = Arrays.asList(playerNameLabel, moneyLabel, scoreLabel, fieldsLabel);
        for (var label : labels) {
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setPreferredSize(new Dimension(250, 50));
            label.setFont(new Font("Arial", Font.PLAIN, 24));
            add(label);
        }

        setVisible(true);
    }

    public void updateHeader() {
        setBackground(game.getMe().getAwtColor());
        playerNameLabel.setText(game.getMe().getName());
        playerNameLabel.setForeground(ColorConverter.determineTextColor(game.getMe().getColor()));

        scoreLabel.setText("score: " + game.getMe().getCurrentScore());
        scoreLabel.setForeground(ColorConverter.determineTextColor(game.getMe().getColor()));

        moneyLabel.setText("money: $" + game.getMe().getMoney());
        moneyLabel.setForeground(ColorConverter.determineTextColor(game.getMe().getColor()));

        fieldsLabel.setText("fields: " + game.getMe().getFieldsOwned());
        fieldsLabel.setForeground(ColorConverter.determineTextColor(game.getMe().getColor()));
    }
}
