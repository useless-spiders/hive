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

    private Spider spider;
    private Ant ant;
    private Bee bee;
    private Grasshopper grasshopper;
    private Beetle beetle;

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
        this.spider = new Spider(player);
        this.ant = new Ant(player);
        this.bee = new Bee(player);
        this.grasshopper = new Grasshopper(player);
        this.beetle = new Beetle(player);

        panel.add(createButtonWithLabel(spider, String.valueOf(spider.getMax())));
        panel.add(createButtonWithLabel(ant, String.valueOf(ant.getMax())));
        panel.add(createButtonWithLabel(bee, String.valueOf(bee.getMax())));
        panel.add(createButtonWithLabel(grasshopper, String.valueOf(grasshopper.getMax())));
        panel.add(createButtonWithLabel(beetle, String.valueOf(beetle.getMax())));

        return panel;
    }

    private JPanel createButtonWithLabel(Insect insect, String labelText) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Créer un nouveau JLabel pour chaque bouton
        JLabel label = new JLabel(labelText);
        buttonPanel.add(label);

        // Ajouter le bouton au-dessous du label
        buttonPanel.add(createButton(insect, label));

        return buttonPanel;
    }

    private JButton createButton(Insect insect, JLabel label) {
        JButton button = new JButton(Display.loadIcon(insect.getImageName()));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clicInsectButton(insect);
                DisplayBankInsects.this.label = label;
            }
        });
        return button;
    }

    public JLabel getLabel() {
        return this.label;
    }
}