
package MillionaireGame.GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HelpOptionsPanel extends JPanel {

    public HelpOptionsPanel(GameWindow gameWindow) {
        setLayout(new GridLayout(1, 3));
        setOpaque(false);

        // Create help buttons with their respective actions
        createHelpButton("FiftyFifty", e -> {
            gameWindow.useFiftyFifty();
            disableButtons();
        });
        createHelpButton("Ask the Audience", e -> {
            gameWindow.askAudience();
            disableButtons();
        });
        createHelpButton("Phone a Friend", e -> {
            gameWindow.phoneAFriend();
            disableButtons();
        });
    }

    //should disable the button once the player used the help option
    private void disableButtons() {
        for (Component comp : getComponents()) {
            comp.setEnabled(false);
        }
    }

    //create help option button
    private void createHelpButton(String label, ActionListener actionListener) {
        JButton btn = new JButton(label);
        btn.setPreferredSize(new Dimension(200, 200));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.addActionListener(actionListener);
        btn.setToolTipText("Use this help option"); // Add tooltip for accessibility
        add(btn);
    }
}