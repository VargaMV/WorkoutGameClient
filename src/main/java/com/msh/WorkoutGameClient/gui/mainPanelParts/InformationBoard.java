package com.msh.WorkoutGameClient.gui.mainPanelParts;

import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;

public class InformationBoard extends JPanel {

    private final Game game;
    private final JLabel myNameLabel;
    private final JLabel currentFieldValueLabel;
    private final JLabel myCurrentValueLabel;
    private final JLabel moneyLabel;

    public InformationBoard(Game game){
        this.game = game;
        setLayout(null);
        setBounds(500,300,300,350);
        setBackground(Color.YELLOW);

        myNameLabel = new JLabel("");
        myNameLabel.setBounds(10, 20, 200, 30);
        myNameLabel.setFont(new Font("Serif", Font.BOLD, 22));
        add(myNameLabel);

        myCurrentValueLabel = new JLabel("");
        myCurrentValueLabel.setBounds(10, 60, 200, 15);
        add(myCurrentValueLabel);

        moneyLabel = new JLabel("");
        moneyLabel.setBounds(10, 80, 200, 15);
        add(moneyLabel);

        currentFieldValueLabel = new JLabel("");
        currentFieldValueLabel.setBounds(10, 110, 200, 15);
        add(currentFieldValueLabel);

    }

    public void updateInfo(){
        //TODO: run only once
        myNameLabel.setText(game.getMe().getName());

        currentFieldValueLabel.setText(
                String.format(
                        "Current field value: %d",
                        game.getField(game.getMe().getPosition()).getValue()
                )
        );
        myCurrentValueLabel.setText(
                String.format(
                        "My current value: %d",
                        game.getMe().getCurrentScore()
                )
        );
        moneyLabel.setText(
                String.format(
                        "My current money: %d",
                        game.getMe().getMoney()
                )
        );
    }


}
