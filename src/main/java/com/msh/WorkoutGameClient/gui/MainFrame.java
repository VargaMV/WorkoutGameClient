package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.websocket.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

@Getter
public class MainFrame extends JFrame {

    private Game game;
    private final Container containerPanel;
    private final JPanel loginPanel;
    private final JPanel mainPanel;
    private final JPanel stockPanel;
    private final JPanel workoutPanel;
    private final CardLayout cardLayout;
    private WebSocketManager wsm;

    public MainFrame(Game game, WebSocketManager wsm) {

        super("Workout Game Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        setLocationRelativeTo(null);

        this.game = game;
        cardLayout = new CardLayout();
        this.loginPanel = new LoginPanel(wsm);
        this.mainPanel = new MainPanel(game, wsm);
        this.wsm = wsm;
        this.stockPanel = new StockPanel(game, wsm);
        this.workoutPanel = new WorkoutPanel(game, wsm);


        containerPanel = new JPanel(cardLayout);
        JScrollPane scrollableStock = new JScrollPane(stockPanel);
        scrollableStock.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableStock.getVerticalScrollBar().setUnitIncrement(20);
        JScrollPane scrollableWorkout = new JScrollPane(workoutPanel);
        scrollableWorkout.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableWorkout.getVerticalScrollBar().setUnitIncrement(20);

        containerPanel.add(loginPanel, "login");
        containerPanel.add(mainPanel, "main");
        containerPanel.add(scrollableStock, "stock");
        containerPanel.add(scrollableWorkout, "workout");

        add(containerPanel, BorderLayout.CENTER);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                if (game.isRetrievedDataFromServer() && wsm.isSessionConnected()) {
                    wsm.sendSecondsUntilMove(game.getMe().getSecondsUntilMove());
                }
                System.exit(0);
            }
        });
    }

    public void initPanels() {
        ((WorkoutPanel) workoutPanel).init();
        ((StockPanel) stockPanel).init();
        ((MainPanel) mainPanel).initMap();
    }

    public void updateMainPanel() {
        ((MainPanel) mainPanel).updateInformationPanel();
        ((MainPanel) mainPanel).updateActionBoard();
        ((MainPanel) mainPanel).updateMap();
        ((MainPanel) mainPanel).updateMiniMap();
        ((MainPanel) mainPanel).updateHeaderPanel();
    }

    public void updateStockPanel() {
        ((StockPanel) stockPanel).updateContent();
    }

    public void switchToStocks() {
        cardLayout.show(containerPanel, "stock");
        ((StockPanel) stockPanel).updateContent();
    }

    public void switchToLogin() {
        cardLayout.show(containerPanel, "login");
    }

    public void switchToMain() {
        cardLayout.show(containerPanel, "main");
        ((MainPanel) mainPanel).updateInformationPanel();
        ((MainPanel) mainPanel).updateActionBoard();
        ((MainPanel) mainPanel).updateMap();
        ((MainPanel) mainPanel).updateMiniMap();
        ((MainPanel) mainPanel).updateHeaderPanel();
    }

    public void switchToWorkout() {
        cardLayout.show(containerPanel, "workout");
        ((WorkoutPanel) workoutPanel).updateContent();
    }

    public void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> {
            if (game.isRetrievedDataFromServer() && wsm.isSessionConnected()) {
                wsm.sendSecondsUntilMove(game.getMe().getSecondsUntilMove());
            }
            System.exit(0);
        });
        fileMenu.add(exitMenuItem);

        JMenu panelsMenu = new JMenu("Panels");
        panelsMenu.setMnemonic(KeyEvent.VK_P);
        menuBar.add(panelsMenu);

        JMenuItem mainPanelItem = new JMenuItem("Main");
        mainPanelItem.addActionListener(e -> switchToMain());
        panelsMenu.add(mainPanelItem);

        JMenuItem stocksPanelItem = new JMenuItem("Manage Stocks");
        stocksPanelItem.addActionListener(e -> switchToStocks());
        panelsMenu.add(stocksPanelItem);

        JMenuItem workoutPanelItem = new JMenuItem("Manage Workout");
        workoutPanelItem.addActionListener(e -> switchToWorkout());
        panelsMenu.add(workoutPanelItem);
    }
}
