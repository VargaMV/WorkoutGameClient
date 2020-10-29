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
        evolveButton.setBounds(100, 10, 120, 30);
        evolveButton.addActionListener(e -> {
            wsm.sendEvolve();
            updateButtons();
        });
        evolveButton.addActionListener(listeners.get("evolve"));
        add(evolveButton);

        /*JTextField moneyInput = new JTextField();
        moneyInput.setBounds(210, 10, 40, 30);
        add(moneyInput);*/

        /*JButton convertButton = new JButton("Convert");
        convertButton.setBounds(110, 10, 80, 30);
        convertButton.addActionListener(listeners.get("add"));
        convertButton.addActionListener(e -> {
            int value = game.getCurrentValue();
            try {
                int amountConverted = Integer.parseInt(moneyInput.getText());
                amountConverted = Math.min(value, amountConverted);
                game.setCurrentValue(value - amountConverted);
                game.incMoney(amountConverted);
                moneyInput.setText("");
                messageLabel.setText("");
                updateButtons();
            } catch (NumberFormatException ex) {
                if ("".equals(moneyInput.getText())) {
                    game.incMoney(game.getCurrentValue());
                    game.setCurrentValue(0);
                    updateButtons();
                } else {
                    messageLabel.setText("Integer number needed!");
                }
            }

        });
        add(convertButton);*/

        messageLabel = new JLabel("");
        messageLabel.setBounds(260, 10, 200, 30);
        add(messageLabel);
        setVisible(true);
    }

    public void updateButtons() {
        occupyButton.setEnabled(game.amIWorthy());
        evolveButton.setEnabled(game.getMe().isVisionIncAffordable() && !game.getMe().isVisionMax());
    }
}
