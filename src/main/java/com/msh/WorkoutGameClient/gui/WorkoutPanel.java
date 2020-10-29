package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.logic.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;

class WorkoutPanel extends JPanel {

    private final Game game;
    private WebSocketManager wsm;
    private JLabel[] exerciseLabels;
    private JButton[] saveButtons;
    private JTextField[] inputFields;

    WorkoutPanel(Game game, WebSocketManager wsm) {
        this.game = game;
        this.wsm = wsm;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        int exerciseNumber = game.getExerciseValues().size();
        exerciseLabels = new JLabel[exerciseNumber];
        saveButtons = new JButton[exerciseNumber];
        inputFields = new JTextField[exerciseNumber];

        int i = 0;
        for (var exercise : game.getExerciseValues().keySet()) {
            exerciseLabels[i] = new JLabel();
            saveButtons[i] = new JButton("Save");
            int finalI = i;
            saveButtons[i].addActionListener(e -> {
                int reps = Integer.parseInt(inputFields[finalI].getText());
                game.exerciseDone(exercise, reps);
                inputFields[finalI].setText("");
                updateContent();
                wsm.sendExerciseDone(exercise, reps);
            });
            inputFields[i] = new JTextField();

            gbc.anchor = GridBagConstraints.WEST;

            int col = (i % 2) * 3;
            int row = Math.floorDiv(i, 2);
            gbc.gridx = col;
            gbc.gridy = row;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            add(exerciseLabels[i], gbc);

            gbc.gridx = col + 1;
            gbc.gridy = row;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            add(saveButtons[i], gbc);

            gbc.gridx = col + 2;
            gbc.gridy = row;
            gbc.ipadx = 60;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            add(inputFields[i], gbc);

            i++;
        }

    }

    void updateContent() {
        int i = 0;
        for (var entry : game.getExerciseValues().entrySet()) {
            String exercise = entry.getKey();
            Integer value = entry.getValue();
            int records = game.getMe().getExerciseNumbers().get(exercise);
            int ownStock = game.getMe().getStockNumbers().get(exercise);
            int allStock = game.getTotalStockNumber(exercise);
            int myValue = (int) Math.ceil(value * ownStock / (double) allStock);
            exerciseLabels[i].setText(String.format("%s : %d DefValue: %d OwnValue: %d", exercise, records, value, myValue));
            i++;
        }
    }
}
