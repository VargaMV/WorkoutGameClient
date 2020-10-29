package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.gui.listener.EvolveListener;
import com.msh.WorkoutGameClient.gui.listener.OccupyListener;
import com.msh.WorkoutGameClient.gui.listener.PlayerMoveListener;
import com.msh.WorkoutGameClient.gui.mainPanelParts.ActionBoard;
import com.msh.WorkoutGameClient.gui.mainPanelParts.GameField;
import com.msh.WorkoutGameClient.gui.mainPanelParts.InformationBoard;
import com.msh.WorkoutGameClient.gui.mainPanelParts.MiniMap;
import com.msh.WorkoutGameClient.logic.WebSocketManager;
import com.msh.WorkoutGameClient.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainPanel extends JPanel {

    private Game game;
    private GameField gameFieldPanel;
    private MiniMap miniMap;
    private InformationBoard informationPanel;
    private ActionBoard actionsPanel;
    private WebSocketManager wsm;

    public MainPanel(Game game, WebSocketManager wsm) {
        this.game = game;
        this.wsm = wsm;
        setPreferredSize(new Dimension(800, 650));
        setLayout(new BorderLayout());
        createPanels();
        setVisible(true);
    }

    void createPanels() {

        JPanel rightSideContainer = new JPanel();
        rightSideContainer.setLayout(new FlowLayout());
        rightSideContainer.setPreferredSize(new Dimension(300, 650));

        miniMap = new MiniMap(game);
        rightSideContainer.add(miniMap);
        informationPanel = new InformationBoard(game);
        rightSideContainer.add(informationPanel, BorderLayout.EAST);

        add(rightSideContainer, BorderLayout.EAST);

        gameFieldPanel = new GameField(game, wsm);

        EvolveListener evolveListener = new EvolveListener(gameFieldPanel, game);
        OccupyListener occupyListener = new OccupyListener(new ArrayList<>(Arrays.asList(gameFieldPanel, miniMap, informationPanel)), game);
        //AddListener addListener = new AddListener(informationPanel, gameFieldPanel);*/
        Map<String, ActionListener> listeners = new HashMap<>();
        listeners.put("occupy", occupyListener);
        listeners.put("evolve", evolveListener);
        //listeners.put("add", addListener);

        actionsPanel = new ActionBoard(game, wsm, listeners);
        add(actionsPanel, BorderLayout.NORTH);

        PlayerMoveListener moveListener = new PlayerMoveListener(informationPanel, miniMap, actionsPanel);
        gameFieldPanel.setMoveListener(moveListener);
        add(gameFieldPanel, BorderLayout.CENTER);

    }


    public void updateInformationPanel() {
        informationPanel.updateInfo();
    }

    public void updateActionBoard() {
        actionsPanel.updateButtons();
    }

    public void updateMap() {
        gameFieldPanel.drawMap();
    }

    public void updateMiniMap() {
        miniMap.drawMiniMap();
    }

}
