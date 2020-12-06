package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.logic.ColorConverter;
import com.msh.WorkoutGameClient.model.NamedAmount;
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
    private JButton undoButton;
    private String panelName = "";

    public Header(Game game, WebSocketManager wsm, String panelName) {
        this.game = game;
        this.wsm = wsm;
        this.panelName = panelName;
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

        switch (panelName) {
            case "main":
                undoButton = new JButton("Undo convert");
                undoButton.setPreferredSize(new Dimension(120, 30));
                add(undoButton);
                break;
            case "workout":
                undoButton = new JButton("Undo save");
                undoButton.setPreferredSize(new Dimension(120, 30));
                add(undoButton);
                break;
            default:
                break;
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

        boolean enableUndoButton;
        switch (panelName) {
            case "main":
                enableUndoButton = game.getLastConvert() > 0;
                if (enableUndoButton) {
                    undoButton.addActionListener(e -> {
                        wsm.sendConvert((-1) * game.getLastConvert());
                        game.convertScoreToMoney((-1) * game.getLastConvert());
                        game.setLastConvert(0);
                        updateHeader();
                    });
                }
                undoButton.setEnabled(enableUndoButton);
                break;
            case "workout":
                NamedAmount lastSave = game.getLastSave();
                enableUndoButton = lastSave.getAmount() > 0;
                if (enableUndoButton) {
                    undoButton.addActionListener(e -> {
                        wsm.sendExerciseDone(lastSave.getName(), (-1) * lastSave.getAmount());
                        game.exerciseDone(lastSave.getName(), (-1) * lastSave.getAmount());
                        game.setLastSave(new NamedAmount("", 0));
                        updateHeader();
                    });
                }
                undoButton.setEnabled(enableUndoButton);
                break;
            default:
                break;
        }

    }
}
