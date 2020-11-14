package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.exceptions.NegativeNumberException;
import com.msh.WorkoutGameClient.websocket.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;

class WorkoutPanel extends JPanel {

    private final Game game;
    private WebSocketManager wsm;
    private JLabel[] exerciseLabels;
    private JLabel[] exerciseNumberLabels;
    private JLabel[] valueLabels;
    private JButton[] saveButtons;
    private JTextField[] inputFields;

    WorkoutPanel(Game game, WebSocketManager wsm) {
        this.game = game;
        this.wsm = wsm;
        setLayout(new GridBagLayout());
    }

    void init() {
        GridBagConstraints gbc = new GridBagConstraints();
        int exerciseNumber = game.getExerciseValues().size();
        exerciseLabels = new JLabel[exerciseNumber];
        exerciseNumberLabels = new JLabel[exerciseNumber];
        valueLabels = new JLabel[exerciseNumber];
        saveButtons = new JButton[exerciseNumber];
        inputFields = new JTextField[exerciseNumber];

        int i = 0;
        for (var exercise : game.getExerciseValues().keySet()) {
            exerciseLabels[i] = new JLabel();
            exerciseLabels[i].setFont(new Font("Arial", Font.BOLD, 15));
            exerciseNumberLabels[i] = new JLabel();
            exerciseNumberLabels[i].setFont(new Font("Arial", Font.BOLD, 15));
            exerciseNumberLabels[i].setPreferredSize(new Dimension(70, 30));
            exerciseNumberLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            valueLabels[i] = new JLabel();
            valueLabels[i].setFont(new Font("Arial", Font.BOLD, 15));
            valueLabels[i].setPreferredSize(new Dimension(100, 30));
            valueLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            saveButtons[i] = new JButton("Save");
            int finalI = i;
            saveButtons[i].addActionListener(e -> {
                int reps = 0;
                try {
                    reps = Integer.parseInt(inputFields[finalI].getText());
                    if (reps < 0) {
                        throw new NegativeNumberException();
                    }
                    game.exerciseDone(exercise, reps);
                    inputFields[finalI].setText("");
                    updateContent();
                    wsm.sendExerciseDone(exercise, reps);
                } catch (NumberFormatException | NegativeNumberException ex) {
                    inputFields[finalI].setText("");
                    ;
                }
            });
            inputFields[i] = new JTextField();

            gbc.anchor = GridBagConstraints.WEST;

            int col = (i % 2) * 5;
            int row = Math.floorDiv(i, 2);
            gbc.gridx = col;
            gbc.gridy = row;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10 + (i % 2) * 30, 0, 0);
            add(exerciseLabels[i], gbc);

            gbc.gridx = col + 1;
            gbc.gridy = row;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            add(exerciseNumberLabels[i], gbc);

            gbc.gridx = col + 2;
            gbc.gridy = row;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            add(valueLabels[i], gbc);

            gbc.gridx = col + 3;
            gbc.gridy = row;
            gbc.ipadx = 60;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            add(inputFields[i], gbc);

            gbc.gridx = col + 4;
            gbc.gridy = row;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            add(saveButtons[i], gbc);

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
            int share = game.getSharePercentage(exercise);
            int myValue = (int) Math.ceil(value * ownStock / (double) allStock);
            exerciseLabels[i].setText(String.format("%s [%d%%]", exercise.toUpperCase(), share));
            exerciseNumberLabels[i].setText(String.valueOf(records));
            valueLabels[i].setText(String.format("%d / rep", myValue));
            if (myValue == 0) {
                valueLabels[i].setForeground(Color.RED);
            } else {
                valueLabels[i].setForeground(Color.BLACK);
            }
            i++;
        }
    }
}
