package View;

import Global.Configuration;
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
    private final GameActionHandler gameActionHandler;
    private final Map<Class<? extends Insect>, JLabel> player1Labels;
    private final Map<Class<? extends Insect>, JLabel> player2Labels;
    private JPanel panelButtonBankJ1;
    private JPanel panelButtonBankJ2;
    private JLabel player1NameLabel;
    private JLabel player2NameLabel;
    private static JButton currentButton;
    private final GridBagConstraints gbc;

    private static Boolean isInsectButtonClicked = false;
    private static ImageIcon currentIcon;
    private final JPanel panelGame;
    private final RessourceLoader ressourceLoader;

    /**
     * Constructeur
     *
     * @param panelGame JPanel
     * @param gbc GridBagConstraints
     * @param gameActionHandler GameActionHandler
     */
    public DisplayBankInsects(JPanel panelGame, GridBagConstraints gbc, GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);
        this.panelGame = panelGame;
        this.gbc = gbc;
        this.player1Labels = new HashMap<>();
        this.player2Labels = new HashMap<>();

        this.createPanels();
    }

    /**
     * Change le joueur en surbrillance
     *
     * @param panel1 JPanel
     * @param panel2 JPanel
     */
    private void switchBorder(JPanel panel1, JPanel panel2) {
        panel1.setBorder(null);
        panel1.setOpaque(false);
        panel2.setBorder(BorderFactory.createLineBorder(Color.RED));
        panel2.setOpaque(true);
    }

    /**
     * Enleve la surbrillance du joueur 1 et met le joueur 2 en surbrillance
     */
    public void switchBorderJ1ToJ2() {switchBorder(this.panelButtonBankJ1, this.panelButtonBankJ2);}

    /**
     * Enleve la surbrillance du joueur 2 et met le joueur 1 en surbrillance
     */
    public void switchBorderJ2ToJ1() {switchBorder(this.panelButtonBankJ2, this.panelButtonBankJ1);}

    /**
     * Renvoie un panel contenant la banque d'un joueur
     *
     * @param player Player
     * @param playerName JLabel
     * @param side int
     * @return JPanel
     */
    private JPanel createButtonPanel(Player player, JLabel playerName, int side) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        this.player1NameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.player2NameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajouter le bouton et le label pour chaque insecte
        panel.add(Box.createVerticalStrut(10));
        panel.add(playerName);
        panel.add(createButtonWithLabel(Spider.class, player, String.valueOf(player.getInsectCount(Spider.class)), side));
        panel.add(createButtonWithLabel(Ant.class, player, String.valueOf(player.getInsectCount(Ant.class)), side));
        panel.add(createButtonWithLabel(Bee.class, player, String.valueOf(player.getInsectCount(Bee.class)), side));
        panel.add(createButtonWithLabel(Grasshopper.class, player, String.valueOf(player.getInsectCount(Grasshopper.class)), side));
        panel.add(createButtonWithLabel(Beetle.class, player, String.valueOf(player.getInsectCount(Beetle.class)), side));
        panel.add(Box.createVerticalStrut(10));
        return panel;
    }

    /**
     * Renvoie un panel contenant un bouton insecte accompagne d'un label indiquant le nombre d'insecte restant
     *
     * @param insectClass Class<? extends Insect>
     * @param player Player
     * @param labelText String
     * @param side int
     * @return JPanel
     */
    private JPanel createButtonWithLabel(Class<? extends Insect> insectClass, Player player, String labelText, int side) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());

        JLabel label = new JLabel(labelText);
        label.setFont(new Font(Configuration.DEFAULT_FONT, Font.BOLD, Configuration.DEFAULT_FONT_SIZE));

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
            duoGbc.insets = new Insets(0, 0, 0, 10);
            panel.add(label, duoGbc);
        } else {
            duoGbc.insets = new Insets(0, 10, 0, 0);
            panel.add(label, duoGbc);
            duoGbc.gridx = 1;
            panel.add(createButton(insectClass, player), duoGbc);
        }
        return panel;
    }

    /**
     * Crée un bouton pour un insecte
     *
     * @param insectClass Class<? extends Insect>
     * @param player Player
     * @return JButton
     */
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

    /**
     * Met à jour l'état de clic du bouton d'insecte
     *
     * @param isInsectButtonClicked boolean
     */
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

    /**
     * Met à jour tous les labels
     */
    public void updateAllLabels() {
        updateLabelsForPlayer(this.gameActionHandler.getPlayerController().getPlayer1(), this.player1Labels);
        updateLabelsForPlayer(this.gameActionHandler.getPlayerController().getPlayer2(), this.player2Labels);
        updatePlayerName(this.gameActionHandler.getPlayerController().getPlayer1());
        updatePlayerName(this.gameActionHandler.getPlayerController().getPlayer2());
    }

    /**
     * Met à jour l'affichage du nombre d'insectes pour chaque insectes d'un joueur
     *
     * @param player Player
     * @param labels Map<Class<? extends Insect>, JLabel>
     */
    private void updateLabelsForPlayer(Player player, Map<Class<? extends Insect>, JLabel> labels) {
        for (Class<? extends Insect> insectClass : labels.keySet()) {
            JLabel label = labels.get(insectClass);
            label.setText(String.valueOf(player.getInsectCount(insectClass)));
        }
    }

    /**
     * Met à jour l'affichage nom du joueur
     *
     * @param player Player
     */
    private void updatePlayerName(Player player) {
        if (player.equals(this.gameActionHandler.getPlayerController().getPlayer1())) {
            this.player1NameLabel.setText(player.getName());
        } else if (player.equals(this.gameActionHandler.getPlayerController().getPlayer2())) {
            this.player2NameLabel.setText(player.getName());
        }
    }

    /**
     * Définit l'opacité d'une icône
     *
     * @param icon ImageIcon
     * @param opacity float
     */
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

    /**
     * Met a jour l'affichage des panels de bank d'insectes
     */
    public void updateButtons() {
        this.panelButtonBankJ1.removeAll();
        this.panelButtonBankJ2.removeAll();
        this.createPanels();
    }

    /**
     * initialisation des banques d'insectes des deux joueurs
     */
    private void createPanels() {
        this.player1NameLabel = new JLabel(String.valueOf(this.gameActionHandler.getPlayerController().getPlayer1().getName()));
        this.player2NameLabel = new JLabel(String.valueOf(this.gameActionHandler.getPlayerController().getPlayer2().getName()));
        this.player1NameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        this.player2NameLabel.setFont(new Font("Serif", Font.BOLD, 20));

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

    /**
     * Met à jour le joueur en surbrillance
      */
    public void updateBorderBank() {
        if (this.gameActionHandler.getPlayerController().getCurrentPlayer().equals(this.gameActionHandler.getPlayerController().getPlayer1())) {
            this.switchBorderJ2ToJ1();
        } else {
            this.switchBorderJ1ToJ2();
        }
    }
}