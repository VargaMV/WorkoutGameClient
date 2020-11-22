package com.msh.WorkoutGameClient.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class RulesPanel extends JPanel {

    List<String> rules = Arrays.asList(
            "The Goal is to own the most fields at the end of the session. (1 week)",
            "If 2 player has the same amount of fields the total score decides who gets the higher position.",
            "You can occupy fields with score. You have to have more score, than the field's score.",
            "In order to increase your current score, you have to buy stocks and then do exercises.",
            "Every exercise has a default value. If you do 1 rep (or sec) of a certain exercise," +
                    "then you increase your score by the exercise's default value multiplied by your share percentage divided by 100.",
            "The prices and the exercises values are rounded values."
    );

    public RulesPanel() {
        JLabel title = new JLabel("RULES");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title);
        setVisible(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (var rule : rules) {
            add(new JLabel(rule));
        }
    }
}
