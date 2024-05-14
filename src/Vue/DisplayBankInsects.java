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
        panel.add(createButton(new Spider(player)));
        panel.add(createButton(new Ant(player)));
        panel.add(createButton(new Bee(player)));
        panel.add(createButton(new Grasshopper(player)));
        panel.add(createButton(new Beetle(player)));
        return panel;
    }

    private JButton createButton(Insect insect) {
        JButton button = new JButton(Display.loadIcon(insect.getImageName()));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clicInsectButton(insect);
            }
        });
        return button;
    }
}