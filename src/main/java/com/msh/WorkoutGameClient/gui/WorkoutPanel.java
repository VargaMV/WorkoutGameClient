package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.exceptions.NegativeNumberException;
import com.msh.WorkoutGameClient.model.Exercise;
import com.msh.WorkoutGameClient.model.NamedAmount;
import com.msh.WorkoutGameClient.websocket.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;

class WorkoutPanel extends JPanel {

    private final Game game;
    private WebSocketManager wsm;
    private Header headerPanel;
    private JPanel body;
    private JLabel[] exerciseLabels;
    private JLabel[] exerciseNumberLabels;
    private JLabel[] valueLabels;
    private JButton[] saveButtons;
    private JTextField[] inputFields;

    WorkoutPanel(Game game, WebSocketManager wsm) {
        this.game = game;
        this.wsm = wsm;
        setLayout(new BorderLayout());
        headerPanel = new Header(game, wsm, "workout");

        body = new JPanel();
        body.setLayout(new GridBagLayout());

        JScrollPane scrollableBody = new JScrollPane(body);
        scrollableBody.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableBody.getVerticalScrollBar().setUnitIncrement(20);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollableBody, BorderLayout.CENTER);
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
                double reps;
                try {
                    reps = Double.parseDouble(inputFields[finalI].getText());
                    if (reps < 0) {
                        throw new NegativeNumberException();
                    }
                    game.exerciseDone(exercise, reps);
                    inputFields[finalI].setText("");
                    updateContent();
                    updateHeaderPanel();
                    wsm.sendExerciseDone(exercise, reps);
                    game.setLastSave(new NamedAmount(exercise, reps));
                } catch (NumberFormatException | NegativeNumberException ex) {
                    inputFields[finalI].setText("");
                }
                updateContent();
                updateHeaderPanel();
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
            body.add(exerciseLabels[i], gbc);

            gbc.gridx = col + 1;
            gbc.gridy = row;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            body.add(exerciseNumberLabels[i], gbc);

            gbc.gridx = col + 2;
            gbc.gridy = row;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            body.add(valueLabels[i], gbc);

            gbc.gridx = col + 3;
            gbc.gridy = row;
            gbc.ipadx = 60;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            body.add(inputFields[i], gbc);

            gbc.gridx = col + 4;
            gbc.gridy = row;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            body.add(saveButtons[i], gbc);

            i++;
        }
    }

    void updateContent() {
        int i = 0;
        for (var entry : game.getExerciseValues().entrySet()) {
            String exerciseName = entry.getKey();
            Exercise exercise = entry.getValue();
            int value = exercise.getValue();
            int records = game.getMe().getExerciseNumbers().get(exerciseName);
            int ownStock = game.getMe().getStockNumbers().get(exerciseName);
            int allStock = game.getTotalStockNumber(exerciseName);
            int share = game.getSharePercentage(exerciseName);
            int myValue = (int) Math.ceil(value * ownStock / (double) allStock);
            exerciseLabels[i].setText(String.format("%s [%d%%]", exerciseName.toUpperCase(), share));
            exerciseNumberLabels[i].setText(String.valueOf(records));
            valueLabels[i].setText(String.format("%d / %s", myValue, exercise.getType()));
            valueLabels[i].setToolTipText("Full: " + value);
            if (myValue == 0) {
                valueLabels[i].setForeground(Color.RED);
            } else {
                valueLabels[i].setForeground(Color.BLACK);
            }
            i++;
        }
    }

    public void updateHeaderPanel() {
        headerPanel.updateHeader();
    }
}
