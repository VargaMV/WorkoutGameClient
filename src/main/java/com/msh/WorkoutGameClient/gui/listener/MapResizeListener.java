package com.msh.WorkoutGameClient.gui.listener;

import com.msh.WorkoutGameClient.gui.mainPanelParts.GameField;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MapResizeListener extends ComponentAdapter {

    private JPanel map;
    private Game game;

    public MapResizeListener(Game game, JPanel map) {
        this.map = map;
        this.game = game;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (game.isRetrievedDataFromServer()) {
            ((GameField) map).drawMap();
        }
    }

}
