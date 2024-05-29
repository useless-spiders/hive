package View;

import Model.Insect.*;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.RessourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
    private GridBagConstraints gbc;

    private static Boolean isInsectButtonClicked = false;
    private static ImageIcon currentIcon;
    private JPanel panelGame;
    private RessourceLoader ressourceLoader;

    public DisplayBankInsects(JPanel panelGame, GridBagConstraints gbc, GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);
        this.panelGame = panelGame;
        this.gbc = gbc;
        this.player1Labels = new HashMap<>();
        this.player2Labels = new HashMap<>();

        this.createPanels();
    }

    private void switchBorder(JPanel panel1, JPanel panel2) {
        panel1.setBorder(null);
        panel1.setOpaque(false);
        panel2.setBorder(BorderFactory.createLineBorder(Color.RED));
        panel2.setOpaque(true);
    }

    public void switchBorderJ1ToJ2() {
        switchBorder(this.panelButtonBankJ1, this.panelButtonBankJ2);
    }

    public void switchBorderJ2ToJ1() {
        switchBorder(this.panelButtonBankJ2, this.panelButtonBankJ1);
    }


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

        if (player.equals(this.gameActionHandler.getPlayerController().getPlayer1())) {
            this.player1Labels.put(insectClass, label);
        } else if (player.equals(this.gameActionHandler.getPlayerController().getPlayer2())) {
            this.player2Labels.put(insectClass, label);
        }

        GridBagConstraints duoGbc = new GridBagConstraints();
        duoGbc.gridx = 0;

        if (side == 1) {
            panel.add(createButton(insectClass, player), duoGbc);
            duoGbc.gridx = 1;
            panel.add(label, duoGbc);
        } else {
            panel.add(label, duoGbc);
            duoGbc.gridx = 1;
            panel.add(createButton(insectClass, player), duoGbc);
        }
        return panel;
    }

    private JButton createButton(Class<? extends Insect> insectClass, Player player) {
        ImageIcon imageIcon = this.ressourceLoader.loadIconInsects(this.ressourceLoader.getImageInsectName(insectClass, player, this.gameActionHandler.getPlayerController().getCurrentPlayer()));
        JButton button = new JButton(imageIcon);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isInsectButtonClicked) {
                    DisplayBankInsects.currentButton = button;
                    DisplayBankInsects.currentIcon = imageIcon;
                }
                gameActionHandler.getGameActionListener().clicInsectButton(insectClass, player);
            }
        });
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setRolloverEnabled(false);

        return button;
    }

    public void updateButtonClickState(boolean isInsectButtonClicked) {
        DisplayBankInsects.isInsectButtonClicked = isInsectButtonClicked;

        if (isInsectButtonClicked) {
            // Créer une copie de l'icône actuelle
            ImageIcon copyIcon = new ImageIcon(currentIcon.getImage());

            // Modifier l'opacité de la copie
            setOpacity(copyIcon, 0.5F);

            // Appliquer l'icône modifiée au bouton
            currentButton.setIcon(copyIcon);
        } else {
            // Rétablir l'icône d'origine
            currentButton.setIcon(currentIcon);
        }
    }

    public void updateAllLabels() {
        updateLabelsForPlayer(this.gameActionHandler.getPlayerController().getPlayer1(), this.player1Labels);
        updateLabelsForPlayer(this.gameActionHandler.getPlayerController().getPlayer2(), this.player2Labels);
        updatePlayerName(this.gameActionHandler.getPlayerController().getPlayer1());
        updatePlayerName(this.gameActionHandler.getPlayerController().getPlayer2());
    }

    private void updateLabelsForPlayer(Player player, Map<Class<? extends Insect>, JLabel> labels) {
        for (Class<? extends Insect> insectClass : labels.keySet()) {
            JLabel label = labels.get(insectClass);
            label.setText(String.valueOf(player.getInsectCount(insectClass)));
        }
    }

    private void updatePlayerName(Player player) {
        if (player.equals(this.gameActionHandler.getPlayerController().getPlayer1())) {
            this.player1NameLabel.setText(player.getName());
        } else if (player.equals(this.gameActionHandler.getPlayerController().getPlayer2())) {
            this.player2NameLabel.setText(player.getName());
        }
    }

    public void setOpacity(ImageIcon icon, float opacity) {

        // Obtenez l'image de l'ImageIcon
        Image img = icon.getImage();

        // Créez une BufferedImage pour traiter l'opacité
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        // Remplacez l'image de l'ImageIcon par l'image avec l'opacité
        icon.setImage(bufferedImage);
    }

    public void updateButtons() {
        this.panelButtonBankJ1.removeAll();
        this.panelButtonBankJ2.removeAll();
        this.createPanels();
    }

    private void createPanels(){
        this.player1NameLabel = new JLabel(String.valueOf(this.gameActionHandler.getPlayerController().getPlayer1().getName()));
        this.player2NameLabel = new JLabel(String.valueOf(this.gameActionHandler.getPlayerController().getPlayer2().getName()));

        this.panelButtonBankJ1 = createButtonPanel(this.gameActionHandler.getPlayerController().getPlayer1(), this.player1NameLabel, 1);
        this.panelButtonBankJ2 = createButtonPanel(this.gameActionHandler.getPlayerController().getPlayer2(), this.player2NameLabel, 2);
        this.panelButtonBankJ1.setBackground(new Color(255, 215, 0, 100));
        this.panelButtonBankJ2.setBackground(new Color(255, 215, 0, 100));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        //gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        this.panelGame.add(panelButtonBankJ1, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        this.panelGame.add(this.panelButtonBankJ2, gbc);
    }

    public void updateBorderBank() {
        if (this.gameActionHandler.getPlayerController().getCurrentPlayer().equals(this.gameActionHandler.getPlayerController().getPlayer1())) {
            this.switchBorderJ2ToJ1();
        } else {
            this.switchBorderJ1ToJ2();
        }
    }
}