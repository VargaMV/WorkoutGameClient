package com.msh.WorkoutGameClient.gui.listener;

import com.msh.WorkoutGameClient.gui.mainPanelParts.GameField;
import com.msh.WorkoutGameClient.gui.mainPanelParts.InformationBoard;
import com.msh.WorkoutGameClient.gui.mainPanelParts.MiniMap;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OccupyListener implements ActionListener {

    JPanel map;
    JPanel miniMap;
    JPanel info;
    Game game;

    public OccupyListener(List<JPanel> panels, Game game) {
        map = panels.get(0);
        miniMap = panels.get(1);
        info = panels.get(2);
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((InformationBoard) info).updateInfo();
        ((GameField) map).drawMap();
        ((MiniMap) miniMap).drawMiniMap();
    }
}
