package com.msh.WorkoutGameClient.gui.mainPanelParts;

import com.msh.WorkoutGameClient.logic.WebSocketManager;
import com.msh.WorkoutGameClient.model.Coordinate;
import com.msh.WorkoutGameClient.model.Field;
import com.msh.WorkoutGameClient.model.Game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameField extends JPanel {
    private final Game game;
    private ActionListener listener;
    private WebSocketManager wsm;

    public GameField(Game game, WebSocketManager wsm) {
        this.game = game;
        this.wsm = wsm;
        setLayout(null);
        setBounds(0, 0, 500, 500);
        setBackground(Color.GRAY);
        //drawMap();
        //listener = e -> System.out.println("MoveListener needs to be set");

    }

    public void setMoveListener(ActionListener listener) {
        this.listener = listener;
    }

    public void drawMap() {
        removeAll();
        repaint();
        revalidate();

        Coordinate pos = game.getMe().getPosition();
        int posX = pos.getX();
        int posY = pos.getY();
        int size = game.getMap().length;
        Field[][] fields = game.getMap();

        int minX = Math.max(0, posX - 2);
        int maxX = Math.min(size - 1, posX + 2);
        int minY = Math.max(0, posY - 2);
        int maxY = Math.min(size - 1, posY + 2);
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                JButton button = new JButton(i + "" + j);
                button.setBounds((3 + i - posX) * 75, (3 + j - posY) * 75, 50, 50);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                int finalJ = j;
                int finalI = i;
                //button.addActionListener(listener);
                button.addActionListener(e -> {
                    Coordinate prevPos = game.getMe().getPosition();
                    Coordinate newPos = new Coordinate(finalI, finalJ);
                    game.getMe().setPosition(newPos);
                    game.getMap()[prevPos.getX()][prevPos.getY()].removePlayerFromField(game.getMe());
                    game.getMap()[newPos.getX()][newPos.getY()].addPlayerToField(game.getMe());
                    drawMap();
                    wsm.sendMove(prevPos, newPos);
                });
                add(button);
                button.setBackground(Color.GRAY);
                if (pos.distance(i, j, game.getMe().isMaxRange()) <= game.getMe().getRangeOfVision()) {
                    button.setBorderPainted(true);
                    button.setBorder(BorderFactory.createLineBorder(fields[i][j].getAwtColor(), 3));
                    if (fields[i][j].getPlayersOnField().size() > 0) {
                        List<String> monograms = new ArrayList<>();
                        fields[i][j].getPlayersOnField().forEach(p -> monograms.add(p.getName().substring(0, 2)));
                        button.setText(String.join(" ", monograms));
                    }
                }

                if (pos.distance(i, j, false) > 1) {
                    button.setEnabled(false);
                } else if (pos.distance(i, j, false) == 1) {
                    button.setBorderPainted(true);
                    if (game.getMe().getCurrentScore() > 0) {
                        button.setEnabled(false);
                    } else {
                        button.setBackground(fields[i][j].getAwtColor());
                    }
                } else if (pos.distance(i, j, false) == 0) {
                    button.setEnabled(false);
                    button.setBorderPainted(true);
                    button.setBorder(BorderFactory.createLineBorder(game.getMe().getAwtColor(), 3));
                    button.setBackground(fields[i][j].getAwtColor());
                }
            }
        }

        JLabel posLabel = new JLabel();
        posLabel.setText(String.format("Current position: (%d, %d)", posX, posY));
        posLabel.setBounds(20, 10, 200, 30);
        add(posLabel);
    }

    /*public void setFieldColor(Color color) {
        game.updateFieldColor(color);
        drawMap();
    }*/

}

