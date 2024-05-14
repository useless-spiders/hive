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
        panel.add(createButton(Spider.class, player));
        panel.add(createButton(Ant.class, player));
        panel.add(createButton(Bee.class, player));
        panel.add(createButton(Grasshopper.class, player));
        panel.add(createButton(Beetle.class, player));
        return panel;
    }

    private JButton createButton(Class<? extends Insect> insectClass, Player player) {
        JButton button = new JButton(Display.loadIcon(Display.getImageName(insectClass, player)));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clicInsectButton(insectClass, player);
            }
        });
        return button;
    }
}