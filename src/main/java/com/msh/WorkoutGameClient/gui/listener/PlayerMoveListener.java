package com.msh.WorkoutGameClient.gui.listener;


import com.msh.WorkoutGameClient.gui.mainPanelParts.ActionBoard;
import com.msh.WorkoutGameClient.gui.mainPanelParts.InformationBoard;
import com.msh.WorkoutGameClient.gui.mainPanelParts.MiniMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerMoveListener implements ActionListener {

    private JPanel infoPanel;
    private JPanel miniMap;
    private JPanel actionPanel;

    public PlayerMoveListener(JPanel infoPanel, JPanel miniMap, JPanel actionPanel) {
        this.infoPanel = infoPanel;
        this.miniMap = miniMap;
        this.actionPanel = actionPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((InformationBoard) infoPanel).updateInfo();
        ((ActionBoard) actionPanel).updateButtons();
        ((MiniMap) miniMap).drawMiniMap();
    }
}
