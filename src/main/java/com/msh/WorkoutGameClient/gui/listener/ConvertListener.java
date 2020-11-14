package com.msh.WorkoutGameClient.gui.listener;

import com.msh.WorkoutGameClient.gui.mainPanelParts.GameField;
import com.msh.WorkoutGameClient.gui.Header;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConvertListener implements ActionListener {

    private JPanel infoPanel;
    private JPanel map;
    private JPanel header;

    public ConvertListener(JPanel infoPanel, JPanel map, JPanel header) {
        this.infoPanel = infoPanel;
        this.map = map;
        this.header = header;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //((InformationBoard)infoPanel).updateInfo();
        ((GameField) map).drawMap();
        ((Header) header).updateHeader();
    }
}
