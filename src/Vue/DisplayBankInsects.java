package Vue;

import Modele.Insect.*;
import Modele.Player;
import Pattern.GameActionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayBankInsects {
    private GameActionHandler controller;
    private JLabel label;

    public DisplayBankInsects(JPanel panelGame, GridBagConstraints gbc, GameActionHandler controller) {
        this.controller = controller;

        JPanel panelButtonBankJ1 = createButtonPanel(controller.getPlayer1());
        JPanel panelButtonBankJ2 = createButtonPanel(controller.getPlayer2());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        panelGame.add(panelButtonBankJ2, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        panelGame.add(panelButtonBankJ1, gbc);

    }

    private JPanel createButtonPanel(Player player) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Ajouter le bouton et le label pour chaque insecte
        panel.add(createButtonWithLabel(Spider.class, player, String.valueOf(player.getInsectCount(Spider.class))));
        panel.add(createButtonWithLabel(Ant.class, player, String.valueOf(player.getInsectCount(Ant.class))));
        panel.add(createButtonWithLabel(Bee.class, player, String.valueOf(player.getInsectCount(Bee.class))));
        panel.add(createButtonWithLabel(Grasshopper.class, player, String.valueOf(player.getInsectCount(Grasshopper.class))));
        panel.add(createButtonWithLabel(Beetle.class, player, String.valueOf(player.getInsectCount(Beetle.class))));
        return panel;
    }

    private JPanel createButtonWithLabel(Class<? extends Insect> insectClass, Player player, String labelText) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Créer un nouveau JLabel pour chaque bouton
        JLabel label = new JLabel(labelText);
        buttonPanel.add(label);

        // Ajouter le bouton au-dessous du label
        buttonPanel.add(createButton(insectClass, player, label));

        return buttonPanel;
    }

    private JButton createButton(Class<? extends Insect> insectClass, Player player, JLabel label) {
        JButton button = new JButton(Display.loadIcon(Display.getImageName(insectClass, player)));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayBankInsects.this.label = label;
                controller.clicInsectButton(insectClass, player);
            }
        });
        return button;
    }

    public JLabel getLabel() {
        return this.label;
    }
}