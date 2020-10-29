package com.msh.WorkoutGameClient.gui.mainPanelParts;

import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;

public class MiniMap extends JPanel {
    private Game game;
    public MiniMap(Game game){
        this.game = game;
        setPreferredSize(new Dimension(300, 300));
        setLayout(null);
        setVisible(true);
    }
    public void drawMiniMap(){
        removeAll();
        for (int i = 0; i < game.getMap().length; i++) {
            for (int j = 0; j < game.getMap().length; j++) {
                JButton mapButton = new JButton("");
                mapButton.setBounds(10 + (i + 1) * 20, (j + 1) * 20, 10, 10);
                mapButton.setEnabled(false);
                if (game.getMe().getColor() == game.getField(i, j).getColor()) {
                    mapButton.setBackground(game.getField(i, j).getAwtColor());
                }
                if (game.getMe().getPosition().getX() == i && game.getMe().getPosition().getY() == j) {
                    mapButton.setEnabled(true);
                    mapButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
                add(mapButton);
            }
        }
        repaint();
        revalidate();
    }
}
