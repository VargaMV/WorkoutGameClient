package com.msh.WorkoutGameClient.gui.mainPanelParts;

import com.msh.WorkoutGameClient.model.Game;
import com.msh.WorkoutGameClient.model.SimplePlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class InformationBoard extends JPanel {

    private final Game game;
    private final JLabel fieldTitle;
    private final JLabel currentFieldValueLabel;
    private final JLabel ownerLabel;
    //private final JLabel moneyLabel;
    private final JLabel othersOnField;

    public InformationBoard(Game game){
        this.game = game;
        setLayout(null);
        setPreferredSize(new Dimension(300, 350));
        setBackground(new Color(160, 160, 160));

        fieldTitle = new JLabel("");
        fieldTitle.setBounds(20, 20, 200, 30);
        fieldTitle.setFont(new Font("Serif", Font.BOLD, 22));
        add(fieldTitle);

        currentFieldValueLabel = new JLabel("");
        currentFieldValueLabel.setBounds(20, 60, 200, 15);
        currentFieldValueLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(currentFieldValueLabel);

        ownerLabel = new JLabel("");
        ownerLabel.setBounds(20, 85, 200, 15);
        ownerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(ownerLabel);

        othersOnField = new JLabel("");
        othersOnField.setBounds(20, 110, 200, 75);
        othersOnField.setVerticalAlignment(SwingConstants.NORTH);
        othersOnField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(othersOnField);

        setVisible(true);

    }

    public void updateInfo(){
        //TODO: run only once
        fieldTitle.setText(
                String.format(
                        "FIELD [ %d , %d ]",
                        game.getMe().getPosition().getX(), game.getMe().getPosition().getY()
                )
        );

        currentFieldValueLabel.setText(
                String.format(
                        "Current field value: %d",
                        game.getField(game.getMe().getPosition()).getValue()
                )
        );
        ownerLabel.setText(
                String.format(
                        "Owner: %s",
                        game.getField(game.getMe().getPosition()).getOwner().getName()
                )
        );
        /*moneyLabel.setText(
                String.format(
                        "My current money: %d",
                        game.getMe().getMoney()
                )
        );*/
        othersOnField.setText(
                "<html>Other players:<br>" +
                        game.getField(game.getMe().getPosition()).getPlayersOnField().stream()
                                .map(SimplePlayer::getName)
                                .filter(name -> !name.equals(game.getMe().getName()))
                                .map(name -> (" > " + name))
                                .collect(Collectors.joining("<br>"))
                        + "</html>"
        );
    }


}
