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
    private GameActionHandler gameActionHandler;
    private Map<Class<? extends Insect>, JLabel> player1Labels;
    private Map<Class<? extends Insect>, JLabel> player2Labels;
    private JPanel panelButtonBankJ1;
    private JPanel panelButtonBankJ2;
    private JLabel player1NameLabel;
    private JLabel player2NameLabel;
    private static JButton currentButton;
    private static Boolean isInsectButtonClicked = false;

    public DisplayBankInsects(JPanel panelGame, GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.player1Labels = new HashMap<>();
        this.player2Labels = new HashMap<>();

        this.player1NameLabel = new JLabel(String.valueOf(this.gameActionHandler.getPlayer1().getName()));
        this.player2NameLabel = new JLabel(String.valueOf(this.gameActionHandler.getPlayer2().getName()));

        GridBagConstraints gbc = new GridBagConstraints();

        this.panelButtonBankJ1 = createButtonPanel(this.gameActionHandler.getPlayer1(), this.player1NameLabel, 1);
        this.panelButtonBankJ2 = createButtonPanel(this.gameActionHandler.getPlayer2(), this.player2NameLabel, 2);
        this.panelButtonBankJ1.setBackground(new Color(255, 215, 0, 100));
        this.panelButtonBankJ2.setBackground(new Color(255, 215, 0, 100));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        panelGame.add(panelButtonBankJ1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panelGame.add(this.panelButtonBankJ2, gbc);

    }

    private void switchBorder(JPanel panel1, JPanel panel2) {
        panel1.setBorder(null);
        panel1.setOpaque(false);
        panel2.setBorder(BorderFactory.createLineBorder(Color.RED));
        panel2.setOpaque(true);

    }

    public void switchBorderJ1ToJ2(){ switchBorder(this.panelButtonBankJ1, this.panelButtonBankJ2); }

    public void switchBorderJ2ToJ1(){ switchBorder(this.panelButtonBankJ2, this.panelButtonBankJ1); }


    private JPanel createButtonPanel(Player player, JLabel playerName, int side) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);


        this.player1NameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.player2NameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajouter le bouton et le label pour chaque insecte
        panel.add(playerName);
        panel.add(createButtonWithLabel(Spider.class, player, String.valueOf(player.getInsectCount(Spider.class)), side));
        panel.add(createButtonWithLabel(Ant.class, player, String.valueOf(player.getInsectCount(Ant.class)), side));
        panel.add(createButtonWithLabel(Bee.class, player, String.valueOf(player.getInsectCount(Bee.class)), side));
        panel.add(createButtonWithLabel(Grasshopper.class, player, String.valueOf(player.getInsectCount(Grasshopper.class)), side));
        panel.add(createButtonWithLabel(Beetle.class, player, String.valueOf(player.getInsectCount(Beetle.class)), side));
        return panel;
    }

    private JPanel createButtonWithLabel(Class<? extends Insect> insectClass, Player player, String labelText, int side) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Times New Roman", Font.BOLD, 30)); // Augmenter la taille de la police ici
        // Créer un nouveau JLabel pour chaque bouton

        if (player.equals(this.gameActionHandler.getPlayer1())) {
            this.player1Labels.put(insectClass, label);
        } else if (player.equals(this.gameActionHandler.getPlayer2())) {
            this.player2Labels.put(insectClass, label);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        if(side == 1){
            panel.add(createButton(insectClass, player, label), gbc);
            gbc.gridx = 1;
            panel.add(label, gbc);
        }
        else{
            panel.add(label, gbc);
            gbc.gridx = 1;
            panel.add(createButton(insectClass, player, label), gbc);
        }
        return panel;
    }

    private JButton createButton(Class<? extends Insect> insectClass, Player player, JLabel label) {
        JButton button = new JButton(MainDisplay.loadIconInsects(MainDisplay.getImageInsectName(insectClass, player)));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isInsectButtonClicked) {
                    DisplayBankInsects.currentButton = button;
                }
                gameActionHandler.clicInsectButton(insectClass, player);
            }
        });
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    public void updateButtonClickState(boolean isInsectButtonClicked) {
        DisplayBankInsects.isInsectButtonClicked = isInsectButtonClicked;
        if (currentButton != null) {
            if (isInsectButtonClicked) {
                currentButton.setOpaque(true);
            } else {
                currentButton.setOpaque(false);
            }
        }
    }

    public void updateAllLabels() {
        updateLabelsForPlayer(this.gameActionHandler.getPlayer1(), this.player1Labels);
        updateLabelsForPlayer(this.gameActionHandler.getPlayer2(), this.player2Labels);
        updatePlayerName(this.gameActionHandler.getPlayer1());
        updatePlayerName(this.gameActionHandler.getPlayer2());
    }

    private void updateLabelsForPlayer(Player player, Map<Class<? extends Insect>, JLabel> labels) {
        for (Class<? extends Insect> insectClass : labels.keySet()) {
            JLabel label = labels.get(insectClass);
            label.setText(String.valueOf(player.getInsectCount(insectClass)));
        }
    }


    private void updatePlayerName(Player player) {
        if (player.equals(this.gameActionHandler.getPlayer1())) {
            this.player1NameLabel.setText(player.getName());
        } else if (player.equals(this.gameActionHandler.getPlayer2())) {
            this.player2NameLabel.setText(player.getName());
        }
    }
}