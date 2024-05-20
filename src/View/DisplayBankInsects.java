package View;

import Model.Insect.*;
import Model.Player;
import Pattern.GameActionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class DisplayBankInsects {
    private GameActionHandler controller;
    private Map<Class<? extends Insect>, JLabel> player1Labels;
    private Map<Class<? extends Insect>, JLabel> player2Labels;
    private JPanel panelButtonBankJ1;
    private JPanel panelButtonBankJ2;

    public DisplayBankInsects(JPanel panelGame, GameActionHandler controller) {
        this.controller = controller;
        this.player1Labels = new HashMap<>();
        this.player2Labels = new HashMap<>();

        GridBagConstraints gbc = new GridBagConstraints();

        this.panelButtonBankJ1 = createButtonPanel(controller.getPlayer1());
        this.panelButtonBankJ2 = createButtonPanel(controller.getPlayer2());
        panelButtonBankJ1.setBackground(new Color(255, 215, 0, 100));
        panelButtonBankJ2.setBackground(new Color(255, 215, 0, 100));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.PAGE_START;
        panelGame.add(panelButtonBankJ2, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        panelGame.add(panelButtonBankJ1, gbc);

    }

    private void switchBorder(JPanel panel1, JPanel panel2) {
        panel1.setBorder(null);
        panel1.setOpaque(false);
        panel2.setBorder(BorderFactory.createLineBorder(Color.RED));
        panel2.setOpaque(true);

    }

    public void switchBorderJ1ToJ2(){ switchBorder(panelButtonBankJ1, panelButtonBankJ2); }

    public void switchBorderJ2ToJ1(){ switchBorder(panelButtonBankJ2, panelButtonBankJ1); }


    private JPanel createButtonPanel(Player player) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);

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
        buttonPanel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Times New Roman", Font.BOLD, 30)); // Augmenter la taille de la police ici



        // Créer un nouveau JLabel pour chaque bouton

        if (player.equals(controller.getPlayer1())) {
            player1Labels.put(insectClass, label);
        } else if (player.equals(controller.getPlayer2())) {
            player2Labels.put(insectClass, label);
        }

        // Ajoutez le label au panel des boutons
        buttonPanel.add(label);

        // Ajouter le bouton au-dessous du label
        buttonPanel.add(createButton(insectClass, player, label));
        return buttonPanel;
    }

    private JButton createButton(Class<? extends Insect> insectClass, Player player, JLabel label) {
        JButton button = new JButton(MainDisplay.loadIcon(MainDisplay.getImageInsectName(insectClass, player)));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clicInsectButton(insectClass, player);
            }
        });
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    public void updateAllLabels() {
        updateLabelsForPlayer(controller.getPlayer1(), player1Labels);
        updateLabelsForPlayer(controller.getPlayer2(), player2Labels);
    }

    private void updateLabelsForPlayer(Player player, Map<Class<? extends Insect>, JLabel> labels) {
        for (Class<? extends Insect> insectClass : labels.keySet()) {
            JLabel label = labels.get(insectClass);
            label.setText(String.valueOf(player.getInsectCount(insectClass)));
        }
    }
}