package com.msh.WorkoutGameClient.gui.listener;

import com.msh.WorkoutGameClient.gui.mainPanelParts.GameField;
import com.msh.WorkoutGameClient.gui.mainPanelParts.Header;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvolveListener implements ActionListener {

    private final JPanel field;
    private final JPanel header;
    private Game game;

    public EvolveListener(JPanel header, JPanel field, Game game) {
        this.header = header;
        this.field = field;
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.getMe().incRangeOfVision();
        ((GameField) field).drawMap();
        ((Header) header).updateHeader();
    }
}
