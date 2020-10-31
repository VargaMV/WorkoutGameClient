package com.msh.WorkoutGameClient.gui.mainPanelParts;

import com.msh.WorkoutGameClient.logic.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class ActionBoard extends JPanel {

    private final Game game;
    private WebSocketManager wsm;

    private JButton occupyButton;
    private JButton evolveButton;
    private JLabel messageLabel;
    private JTextField moneyInput;

    public ActionBoard(Game game, WebSocketManager wsm, Map<String, ActionListener> listeners) {
        this.game = game;
        this.wsm = wsm;
        setLayout(null);
        setPreferredSize(new Dimension(500, 50));
        setBackground(new Color(50, 50, 50));

        occupyButton = new JButton("Occupy");
        occupyButton.setBounds(10, 10, 80, 30);
        occupyButton.addActionListener(listeners.get("occupy"));
        occupyButton.addActionListener(e -> {
            game.occupyOrIncrease();
            wsm.sendOccupy(game.getMe().getPosition());
            updateButtons();
        });
        add(occupyButton);

        evolveButton = new JButton("Vision ++");
        evolveButton.setBounds(100, 10, 100, 30);
        evolveButton.addActionListener(e -> {
            wsm.sendVisionInc();
            updateButtons();
        });
        evolveButton.addActionListener(listeners.get("evolve"));
        add(evolveButton);

        JButton convertButton = new JButton("Convert");
        convertButton.setBounds(210, 10, 80, 30);
        convertButton.addActionListener(listeners.get("convert"));
        convertButton.addActionListener(e -> {
            //TODO: convertScoreToMoney to game
            int value = game.getMe().getCurrentScore();
            try {
                int amountConverted = Integer.parseInt(moneyInput.getText());
                amountConverted = Math.min(value, amountConverted);
                wsm.sendConvert(amountConverted);
                game.getMe().decScore(amountConverted);
                game.getMe().incMoney(amountConverted);
                moneyInput.setText("");
                messageLabel.setText("");
                updateButtons();
            } catch (NumberFormatException ex) {
                if ("".equals(moneyInput.getText())) {
                    wsm.sendConvert(game.getMe().getCurrentScore());
                    game.getMe().incMoney(game.getMe().getCurrentScore());
                    game.getMe().setCurrentScore(0);
                    updateButtons();
                } else {
                    messageLabel.setText("Integer number needed!");
                }
            }

        });
        add(convertButton);

        moneyInput = new JTextField();
        moneyInput.setBounds(300, 10, 50, 30);
        add(moneyInput);

        messageLabel = new JLabel("");
        messageLabel.setBounds(360, 10, 200, 30);
        add(messageLabel);
        setVisible(true);
    }

    public void updateButtons() {
        occupyButton.setEnabled(game.amIWorthy());
        evolveButton.setEnabled(game.getMe().isVisionIncAffordable() && !game.getMe().isVisionMax());
    }
}
