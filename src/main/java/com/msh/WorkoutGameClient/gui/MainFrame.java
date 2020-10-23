package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.logic.WebSocketManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final Container containerPanel;
    private final JPanel loginPanel;
    private final JPanel mainPanel;
    /*private final JPanel stockPanel;
    private final JPanel workoutPanel;*/
    private final CardLayout cardLayout;

    public MainFrame(WebSocketManager wsm) {

        super("Workout Game Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null);
        setLayout(null);

        cardLayout = new CardLayout();
        this.loginPanel = new LoginPanel(wsm);
        this.mainPanel = new MainPanel();
        /*this.stockPanel = new StockPanel(game);
        this.workoutPanel = new WorkoutPanel(game);*/


        containerPanel = new JPanel(cardLayout);
        containerPanel.setSize(800, 650);
        /*JScrollPane scrollableStock = new JScrollPane(stockPanel);
        scrollableStock.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane scrollableWorkout = new JScrollPane(workoutPanel);
        scrollableWorkout.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);*/

        containerPanel.add(loginPanel, "login");
        containerPanel.add(mainPanel, "main");
        /*containerPanel.add(scrollableStock, "stock");
        containerPanel.add(scrollableWorkout, "workout");*/

        add(containerPanel);
        setVisible(true);
    }

    /*public void switchToStocks() {
        cardLayout.show(containerPanel, "stock");
        ((StockPanel) stockPanel).updateContent();
    }*/

    public void switchToMain() {
        cardLayout.show(containerPanel, "main");
    }

    /*public void switchToWorkout() {
        cardLayout.show(containerPanel, "workout");
        ((WorkoutPanel) workoutPanel).updateContent();
    }*/
}
