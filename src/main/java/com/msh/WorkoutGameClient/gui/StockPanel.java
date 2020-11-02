package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.websocket.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;

public class StockPanel extends JPanel {

    private Game game;
    private WebSocketManager wsm;
    private JPanel manager;
    private JLabel moneyLabel;
    private JLabel[] exerciseLabels;
    private JButton[] buyButtons;
    private JPanel bars;


    class BarsJPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int i = 0;
            for (var exercise : game.getTotalStockNumbers().keySet()) {
                Integer stockNumber = game.getMe().getStockNumbers().get(exercise);
                int all = game.getTotalStockNumber(exercise);
                int share = all == 0 ? 0 : Math.floorDiv(stockNumber * 100, all);
                if (all == 0) {
                    g2d.setColor(new Color(190, 190, 190));
                } else {
                    g2d.setColor(new Color(80, 80, 80));
                }

                g2d.fillRect(30, i * 46 + 35, 300, 20);

                g2d.setColor(new Color(120, 200, 50));
                g2d.fillRect(30, i * 46 + 35, share * 3, 20);
                i++;
            }

        }
    }

    public StockPanel(Game game, WebSocketManager wsm) {
        this.game = game;
        this.wsm = wsm;
        setLayout(new GridLayout());

        manager = new JPanel();
        add(manager);

        bars = new BarsJPanel();
        add(bars);

        manager.setLayout(new GridBagLayout());
        setVisible(true);
    }

    public void init() {
        int exerciseTypeNumber = game.getTotalStockNumbers().size();
        exerciseLabels = new JLabel[exerciseTypeNumber];
        buyButtons = new JButton[exerciseTypeNumber];

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        moneyLabel = new JLabel();
        moneyLabel.setText(String.format("Current money : %d", game.getMe().getMoney()));
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        manager.add(moneyLabel);

        gbc.gridwidth = 1;


        int i = 0;
        for (var entry : game.getTotalStockNumbers().entrySet()) {
            String exercise = entry.getKey();
            exerciseLabels[i] = new JLabel();
            buyButtons[i] = new JButton("Buy");
            buyButtons[i].addActionListener(e -> {
                game.buyStock(exercise);
                wsm.sendStockBought(exercise);
                updateContent();
                bars.repaint();
            });
            buyButtons[i].setPreferredSize(new Dimension(80, 26));

            int col = 0;
            int row = i + 1;

            gbc.gridx = col;
            gbc.gridy = row;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.insets = new Insets(10, 10, 0, 0);
            manager.add(exerciseLabels[i], gbc);

            gbc.gridx = col + 1;
            gbc.gridy = row;
            gbc.insets = new Insets(10, 10, 0, 0);
            gbc.ipadx = 10;
            gbc.ipady = 10;
            manager.add(buyButtons[i], gbc);
            i++;
        }
    }

    public void updateContent() {
        moneyLabel.setText(String.format("Current money : %d", game.getMe().getMoney()));
        int i = 0;
        for (var exercise : game.getTotalStockNumbers().keySet()) {
            int stockNumber = game.getMe().getStockNumbers().get(exercise);
            int price = game.getMe().getNextPrice(exercise);
            int all = game.getTotalStockNumbers().get(exercise);
            int share = game.getSharePercentage(exercise);
            exerciseLabels[i].setText(String.format("%s  >>>  Share: %d %% (%d / %d)", exercise, share, stockNumber, all));
            buyButtons[i].setEnabled(game.getMe().isStockAffordable(exercise));
            buyButtons[i].setText(String.format("Buy ($%d)", price));
            i++;
        }
        bars.repaint();
    }
}
