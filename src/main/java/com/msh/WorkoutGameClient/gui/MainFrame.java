package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.model.SimpleGame;
import com.msh.WorkoutGameClient.websocket.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.List;

@Getter
public class MainFrame extends JFrame {

    private Game game;
    private final Container containerPanel;
    private final JPanel loginPanel;
    private final JPanel mainPanel;
    private final JPanel stockPanel;
    private final JPanel workoutPanel;
    private final JPanel rulesPanel;
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
        this.rulesPanel = new RulesPanel();


        containerPanel = new JPanel(cardLayout);

        containerPanel.add(loginPanel, "login");
        containerPanel.add(mainPanel, "main");
        containerPanel.add(stockPanel, "stock");
        containerPanel.add(workoutPanel, "workout");
        containerPanel.add(rulesPanel, "rules");

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

    public void updateLoginPanel(List<SimpleGame> games) {
        ((LoginPanel) loginPanel).updateGamesPanel(games);
    }

    public void updatePanels() {
        updateMainPanel();
        updateStockPanel();
        updateWorkoutPanel();
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
        ((StockPanel) stockPanel).updateHeaderPanel();
    }

    public void updateWorkoutPanel() {
        ((WorkoutPanel) workoutPanel).updateHeaderPanel();
    }

    public void switchToStocks() {
        cardLayout.show(containerPanel, "stock");
        ((StockPanel) stockPanel).updateContent();
        ((StockPanel) stockPanel).updateHeaderPanel();
    }

    public void switchToLogin() {
        cardLayout.show(containerPanel, "login");
    }

    public void switchToRules() {
        cardLayout.show(containerPanel, "rules");
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
        ((WorkoutPanel) workoutPanel).updateHeaderPanel();
    }

    public void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem rulesPanelItem = new JMenuItem("Rules");
        rulesPanelItem.addActionListener(e -> switchToRules());
        fileMenu.add(rulesPanelItem);

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
