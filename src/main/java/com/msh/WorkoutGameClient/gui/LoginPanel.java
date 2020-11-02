package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.websocket.WebSocketManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LoginPanel extends JPanel {

    private JTextField nameInput;
    private JTextField ipInput;
    private JTextField portInput;
    private JLabel feedBackLabel;
    private WebSocketManager wsm;

    public LoginPanel(WebSocketManager wsm) {
        this.wsm = wsm;

        setSize(800, 650);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 0, 0, 0);

        JLabel title = new JLabel("LOGIN");
        title.setPreferredSize(new Dimension(200, 30));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridy = 2;
        add(title, gbc);


        nameInput = new JTextField("Name");
        nameInput.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        add(nameInput, gbc);

        ipInput = new JTextField("IP");
        ipInput.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 4;
        add(ipInput, gbc);

        portInput = new JTextField("Port");
        portInput.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        add(portInput, gbc);

        feedBackLabel = new JLabel("");
        feedBackLabel.setPreferredSize(new Dimension(400, 30));
        feedBackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 7;
        add(feedBackLabel, gbc);

        File file = new File("serverAddress.txt");
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split("#");
                if (line.length == 3) {
                    nameInput.setText(line[0]);
                    ipInput.setText(line[1]);
                    portInput.setText(line[2]);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        JButton joinButton = new JButton("Join");
        joinButton.setPreferredSize(new Dimension(100, 30));
        joinButton.addActionListener(e -> {
            String name = nameInput.getText();
            String url = "ws://" + ipInput.getText() + ":" + portInput.getText() + "/action";
            if (name.length() > 0) {
                wsm.establishConnection(name, url);
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(nameInput.getText() + "#");
                    writer.append(ipInput.getText()).append("#");
                    writer.append(portInput.getText());
                    writer.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }

        });
        gbc.gridy = 6;
        add(joinButton, gbc);
        setVisible(true);
    }

    public void writeFeedback(String feedback) {
        feedBackLabel.setText(feedback);
    }
}
