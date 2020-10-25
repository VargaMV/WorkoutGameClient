package com.msh.WorkoutGameClient.gui;

/*import UI.listeners.AddListener;
import UI.listeners.EvolveListener;
import UI.listeners.OccupyListener;
import UI.listeners.PlayerMoveListener;
import UI.mainPanelParts.ActionBoard;
import UI.mainPanelParts.GameField;
import UI.mainPanelParts.InformationBoard;
import UI.mainPanelParts.MiniMap;
import logic.Game;*/

import com.msh.WorkoutGameClient.gui.mainPanelParts.GameField;
import com.msh.WorkoutGameClient.logic.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainPanel extends JPanel {

    private Game game;
    private GameField gameFieldPanel;
    /*private JPanel informationPanel;
    private JPanel actionsPanel;*/
    private WebSocketManager wsm;

    public MainPanel(Game game, WebSocketManager wsm) {
        this.game = game;
        this.wsm = wsm;
        setSize(800, 650);
        setLayout(null);
        createPanels();
        setVisible(true);
    }

    void createPanels() {

       /* informationPanel = new InformationBoard(game);
        add(informationPanel);

        JPanel miniMap = new MiniMap(game);
        add(miniMap);*/

        gameFieldPanel = new GameField(game, wsm);

        /*EvolveListener evolveListener = new EvolveListener(gameFieldPanel, game);
        OccupyListener occupyListener = new OccupyListener(new ArrayList<>(Arrays.asList(gameFieldPanel, miniMap, informationPanel)), game);
        AddListener addListener = new AddListener(informationPanel, gameFieldPanel);
        Map<String, ActionListener> listeners = new HashMap<>();
        listeners.put("occupy", occupyListener);
        listeners.put("evolve", evolveListener);
        listeners.put("add", addListener);

        actionsPanel = new ActionBoard(game, listeners);
        add(actionsPanel);

        PlayerMoveListener moveListener = new PlayerMoveListener(informationPanel, miniMap, actionsPanel);

        gameFieldPanel.setMoveListener(moveListener);*/
        add(gameFieldPanel);

    }

    /*
        public void updateInformationPanel() {
            ((InformationBoard) informationPanel).updateInfo();
        }

        public void updateActionBoard() {
            ((ActionBoard) actionsPanel).updateButtons();
        }
    */
    public void updateMap() {
        gameFieldPanel.drawMap();
    }

}
