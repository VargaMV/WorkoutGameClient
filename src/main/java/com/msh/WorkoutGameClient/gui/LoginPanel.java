package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.model.SimpleGame;
import com.msh.WorkoutGameClient.websocket.WebSocketManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.tomcat.util.codec.binary.Base64;

public class LoginPanel extends JPanel {

    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JTextField nameInput;
    private JPasswordField pswInput;
    private JButton joinButton;
    private JLabel feedBackLabel;
    private WebSocketManager wsm;
    private JPanel gamesPanel;

    private String gameId = null;
    private boolean running;

    public LoginPanel(WebSocketManager wsm) {
        this.wsm = wsm;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = 1;

        JLabel select = new JLabel("Select a game:");
        select.setPreferredSize(new Dimension(200, 40));
        select.setHorizontalAlignment(SwingConstants.CENTER);
        select.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(select, gbc);

        gamesPanel = new JPanel();
        gamesPanel.setLayout(new GridBagLayout());

        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gamesPanel.setPreferredSize(new Dimension(450, 250));
        add(gamesPanel, gbc);

        JLabel title = new JLabel("LOGIN");
        title.setPreferredSize(new Dimension(200, 50));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 42));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        nameLabel = new JLabel("Name: ");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(nameLabel, gbc);

        nameInput = new JTextField("Name");
        nameInput.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(nameInput, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        passwordLabel = new JLabel("Password: ");
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(passwordLabel, gbc);

        pswInput = new JPasswordField("");
        pswInput.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(pswInput, gbc);

        feedBackLabel = new JLabel("");
        feedBackLabel.setPreferredSize(new Dimension(400, 30));
        feedBackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(feedBackLabel, gbc);

        File file = new File("username.txt");
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                nameInput.setText(name);
            }
            if (scanner.hasNextLine()) {
                String psw = new String(Base64.decodeBase64(scanner.nextLine()));
                pswInput.setText(psw);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        joinButton = new JButton("-");
        joinButton.setEnabled(false);
        joinButton.setPreferredSize(new Dimension(100, 30));
        joinButton.addActionListener(e -> {
            String name = nameInput.getText();
            String password = new String(pswInput.getPassword());
            if (name.length() > 0) {
                wsm.join(name, password, gameId);
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(nameInput.getText());
                    writer.write("\n");
                    writer.write(Base64.encodeBase64String(password.getBytes()));
                    writer.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }

        });
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(joinButton, gbc);

        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 30));
        registerButton.addActionListener(e -> {
            String name = nameInput.getText();
            String password = new String(pswInput.getPassword());
            if (name.length() > 0) {
                wsm.register(name, password);
            }

        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(registerButton, gbc);

        setVisible(true);
    }

    public void writeFeedback(String feedback) {
        feedBackLabel.setText(feedback);
    }

    public void updateContent() {
        joinButton.setEnabled(true);
        joinButton.setText(running ? "Join" : "Subscribe");
    }

    public void updateGamesPanel(List<SimpleGame> games) {
        GridBagConstraints gpGbc = new GridBagConstraints();
        int i = 0;
        gamesPanel.removeAll();
        for (var game : games) {
            JButton gameButton = new JButton();
            gameButton.setText(
                    String.format(
                            "Name: %s, Running: %b, Sub: %b, player number: %d",
                            game.getTitle(), game.isRunning(), game.isSubOn(), game.getPlayerNumber()
                    )
            );
            gameButton.addActionListener(e -> {
                gameId = game.getId();
                running = game.isRunning();
                updateGamesPanel(games);
                updateContent();
            });
            gameButton.setFocusPainted(false);
            gameButton.setBorderPainted(false);
            if (game.getId().equals(gameId)) {
                gameButton.setBackground(Color.GREEN);
            } else {
                gameButton.setBackground(Color.GRAY);
            }
            gameButton.setPreferredSize(new Dimension(450, 40));
            gameButton.setHorizontalAlignment(SwingConstants.CENTER);
            gpGbc.insets = new Insets(5, 0, 5, 0);
            gpGbc.gridx = 1;
            gpGbc.gridy = i;
            gamesPanel.add(gameButton, gpGbc);
            i++;
        }
        gamesPanel.repaint();
        gamesPanel.revalidate();
    }
}
