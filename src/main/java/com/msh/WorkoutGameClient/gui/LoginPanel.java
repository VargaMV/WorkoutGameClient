package com.msh.WorkoutGameClient.gui;

import com.msh.WorkoutGameClient.logic.WebSocketManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class LoginPanel extends JPanel {

    private JTextField nameInput;
    private JTextField ipInput;
    private JTextField portInput;
    private WebSocketManager wsm;

    LoginPanel(WebSocketManager wsm) {
        this.wsm = wsm;

        setSize(800, 650);
        setLayout(null);

        JLabel title = new JLabel("LOGIN");
        title.setBounds(300, 170, 200, 30);
        add(title);


        nameInput = new JTextField("Name");
        nameInput.setBounds(300, 210, 200, 30);
        add(nameInput);

        ipInput = new JTextField("IP");
        ipInput.setBounds(300, 250, 200, 30);
        add(ipInput);

        portInput = new JTextField("Port");
        portInput.setBounds(300, 290, 200, 30);
        add(portInput);

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
        joinButton.setBounds(350, 330, 100, 30);
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
        add(joinButton);
        setVisible(true);
    }
}
