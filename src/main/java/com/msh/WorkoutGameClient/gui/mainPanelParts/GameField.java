package com.msh.WorkoutGameClient.gui.mainPanelParts;

import com.msh.WorkoutGameClient.gui.listener.MapResizeListener;
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
    private JLabel posLabel;

    public GameField(Game game, WebSocketManager wsm) {
        this.game = game;
        this.wsm = wsm;
        posLabel = new JLabel();
        setLayout(null);
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.GRAY);
        listener = e -> System.out.println("MoveListener needs to be set");
        addComponentListener(new MapResizeListener(game, this));
        setVisible(true);
    }

    public void setMoveListener(ActionListener listener) {
        this.listener = listener;
    }

    public void drawMap() {
        removeAll();

        Coordinate pos = game.getMe().getPosition();
        int posX = pos.getX();
        int posY = pos.getY();
        int size = game.getMap().length;
        Field[][] fields = game.getMap();

        int minX = Math.max(0, posX - 2);
        int maxX = Math.min(size - 1, posX + 2);
        int minY = Math.max(0, posY - 2);
        int maxY = Math.min(size - 1, posY + 2);

        int shift = Math.min(this.getSize().width - 75, this.getSize().height - 75) / 5;
        int buttonSize = 2 * shift / 3;
        int wholeSize = 4 * shift + buttonSize;
        int leftMargin = (this.getSize().width - wholeSize) / 2;
        int topMargin = (this.getSize().height - wholeSize) / 2;

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                JButton button = new JButton(i + "" + j);
                button.setBounds(leftMargin + (2 + i - posX) * shift, topMargin + (2 + j - posY) * shift, buttonSize, buttonSize);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                int finalJ = j;
                int finalI = i;
                button.addActionListener(listener);
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
                if (pos.distance(i, j, game.getMe().isSqrRange()) <= game.getMe().getRangeOfVision()) {
                    button.setBorderPainted(true);
                    button.setBorder(BorderFactory.createLineBorder(fields[i][j].getAwtColor(), 3));
                    if (fields[i][j].getPlayersOnField().size() > 0) {
                        List<String> monograms = new ArrayList<>();
                        for (var player : fields[i][j].getPlayersOnField()) {
                            int to = Math.min(player.getName().length(), 2);
                            monograms.add(player.getName().substring(0, to));
                        }
                        button.setText(String.join(" ", monograms));
                    }
                }

                if (pos.distance(i, j, false) > 1) {
                    button.setEnabled(false);
                } else if (pos.distance(i, j, false) == 1) {
                    if (fields[i][j].getPlayersOnField().size() > 0) {
                        button.setBorderPainted(true);
                        button.setBorder(BorderFactory.createLineBorder(fields[i][j].getPlayersOnField().get(0).getAwtColor(), 3));
                    }
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

        posLabel.setText(String.format("Current position: (%d, %d)", posX, posY));
        posLabel.setBounds(20, 10, 200, 30);
        add(posLabel);

        repaint();
        revalidate();
    }

}

