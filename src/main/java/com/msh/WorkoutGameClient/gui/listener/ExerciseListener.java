package com.msh.WorkoutGameClient.gui.listener;

import com.msh.WorkoutGameClient.gui.Header;
import com.msh.WorkoutGameClient.gui.WorkoutPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExerciseListener implements ActionListener {

    private JPanel header;
    private JPanel panel;

    public ExerciseListener(WorkoutPanel panel, JPanel header) {
        this.panel = panel;
        this.header = header;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((WorkoutPanel) panel).updateContent();
        ((WorkoutPanel) panel).updateHeaderPanel();
    }
}
