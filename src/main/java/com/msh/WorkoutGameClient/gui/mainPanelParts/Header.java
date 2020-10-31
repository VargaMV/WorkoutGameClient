package com.msh.WorkoutGameClient.gui.mainPanelParts;

import com.msh.WorkoutGameClient.logic.ColorConverter;
import com.msh.WorkoutGameClient.logic.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

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
        playerNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        playerNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        playerNameLabel.setPreferredSize(new Dimension(170, 50));
        add(playerNameLabel);

        moneyLabel = new JLabel("");
        moneyLabel.setHorizontalAlignment(SwingConstants.LEFT);
        moneyLabel.setVerticalAlignment(SwingConstants.CENTER);
        moneyLabel.setPreferredSize(new Dimension(150, 50));
        add(moneyLabel);

        scoreLabel = new JLabel("");
        scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        scoreLabel.setVerticalAlignment(SwingConstants.CENTER);
        scoreLabel.setPreferredSize(new Dimension(150, 50));
        add(scoreLabel);

        fieldsLabel = new JLabel("");
        fieldsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        fieldsLabel.setVerticalAlignment(SwingConstants.CENTER);
        fieldsLabel.setPreferredSize(new Dimension(150, 50));
        add(fieldsLabel);

        setVisible(true);
    }

    public void updateHeader() {
        setBackground(game.getMe().getAwtColor());
        playerNameLabel.setText(game.getMe().getName());
        playerNameLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        playerNameLabel.setForeground(ColorConverter.determineTextColor(game.getMe().getColor()));

        scoreLabel.setText("score: " + game.getMe().getCurrentScore());
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setForeground(ColorConverter.determineTextColor(game.getMe().getColor()));

        moneyLabel.setText("money: $" + game.getMe().getMoney());
        moneyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        moneyLabel.setForeground(ColorConverter.determineTextColor(game.getMe().getColor()));

        fieldsLabel.setText("fields: " + game.getMe().getFieldsOwned());
        fieldsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        fieldsLabel.setForeground(ColorConverter.determineTextColor(game.getMe().getColor()));
    }
}
