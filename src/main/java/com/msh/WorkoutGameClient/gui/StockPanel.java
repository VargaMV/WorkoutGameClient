package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.websocket.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;

public class StockPanel extends JPanel {

    private Game game;
    private WebSocketManager wsm;
    private Header headerPanel;
    private JPanel body;
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
                int barWidth = this.getSize().width - 150;
                g2d.fillRect(30, i * 60 + 30, barWidth, 20);

                //g2d.setColor(new Color(120, 200, 50));
                g2d.setColor(game.getMe().getAwtColor());
                g2d.fillRect(30, i * 60 + 30, share * barWidth / 100, 20);
                i++;
            }

        }
    }

    public StockPanel(Game game, WebSocketManager wsm) {
        this.game = game;
        this.wsm = wsm;
        setLayout(new BorderLayout());

        headerPanel = new Header(game, wsm, "stock");
        add(headerPanel, BorderLayout.NORTH);

        body = new JPanel();
        body.setLayout(new GridLayout());

        JScrollPane scrollableBody = new JScrollPane(body);
        scrollableBody.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableBody.getVerticalScrollBar().setUnitIncrement(20);

        add(scrollableBody, BorderLayout.CENTER);

        manager = new JPanel();
        body.add(manager);

        bars = new BarsJPanel();
        body.add(bars);

        manager.setLayout(new GridBagLayout());
        setVisible(true);
    }

    public void init() {
        int exerciseTypeNumber = game.getTotalStockNumbers().size();
        exerciseLabels = new JLabel[exerciseTypeNumber];
        buyButtons = new JButton[exerciseTypeNumber];

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        int i = 0;
        for (var entry : game.getTotalStockNumbers().entrySet()) {
            String exercise = entry.getKey();
            exerciseLabels[i] = new JLabel();
            exerciseLabels[i].setFont(new Font("Arial", Font.BOLD, 15));
            buyButtons[i] = new JButton("");
            buyButtons[i].addActionListener(e -> {
                wsm.sendStockBought(exercise, 1);
                game.setLastConvert(0);
            });
            buyButtons[i].setPreferredSize(new Dimension(100, 30));

            int col = 0;
            int row = i;

            gbc.gridx = col;
            gbc.gridy = row;
            gbc.insets = new Insets(20, 20, 0, 0);
            manager.add(exerciseLabels[i], gbc);

            gbc.gridx = col + 1;
            gbc.gridy = row;
            gbc.insets = new Insets(20, 20, 0, 0);
            gbc.ipadx = 10;
            gbc.ipady = 10;
            manager.add(buyButtons[i], gbc);
            i++;
        }
    }

    public void updateContent() {
        int i = 0;
        for (var exercise : game.getTotalStockNumbers().keySet()) {
            int stockNumber = game.getMe().getStockNumbers().get(exercise);
            double price = game.getNextPrice(exercise);
            int all = game.getTotalStockNumbers().get(exercise);
            int share = game.getSharePercentage(exercise);
            exerciseLabels[i].setText(String.format("%s  >>>  Share: %d %% (%d / %d)", exercise, share, stockNumber, all));
            buyButtons[i].setEnabled(game.isStockAffordable(exercise));
            buyButtons[i].setText(String.format("Buy ($%.2f)", price));
            i++;
        }
        bars.repaint();
    }

    public void updateHeaderPanel() {
        headerPanel.updateHeader();
    }
}
