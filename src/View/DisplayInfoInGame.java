package View;

import Global.Configuration;
import Pattern.GameActionHandler;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

public class DisplayInfoInGame extends JPanel {
    private final JLabel turnLabel;
    private final JLabel namePlayeurLabel;
    private final JLabel tipCenter;
    private final GameActionHandler gameActionHandler;

    /**
     * Constructeur de la classe DisplayInfoInGame.
     *
     * @param panelGame         JPanel du jeu principal.
     * @param gbc               Contraintes de disposition pour le GridBagLayout.
     * @param gameActionHandler Gestionnaire des actions du jeu.
     */
    public DisplayInfoInGame(JPanel panelGame, GridBagConstraints gbc, GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        this.setLayout(new GridBagLayout()); // Définir le layout du JPanel

        JLabel infoLabel = new JLabel(this.gameActionHandler.getLang().getString("display.info.title"));
        infoLabel.setFont(new Font(Configuration.DEFAULT_FONT, Font.BOLD, Configuration.DEFAULT_FONT_SIZE));
        this.namePlayeurLabel = new JLabel(MessageFormat.format(this.gameActionHandler.getLang().getString("display.info.player"), this.gameActionHandler.getPlayerController().getCurrentPlayer().getName()));
        this.namePlayeurLabel.setFont(new Font(Configuration.DEFAULT_FONT, Font.PLAIN, 20));
        this.turnLabel = new JLabel(MessageFormat.format(this.gameActionHandler.getLang().getString("display.info.turn"), this.gameActionHandler.getPlayerController().getCurrentPlayer().getTurn()));
        this.turnLabel.setFont(new Font(Configuration.DEFAULT_FONT, Font.PLAIN, 20));
        this.tipCenter = new JLabel(this.gameActionHandler.getLang().getString("display.info.tip_center"));
        this.tipCenter.setFont(new Font(Configuration.DEFAULT_FONT, Font.PLAIN, 20));

        JPanel boxContainer = new JPanel();
        boxContainer.setLayout(new BoxLayout(boxContainer, BoxLayout.Y_AXIS));
        boxContainer.setOpaque(false);
        boxContainer.add(infoLabel);
        boxContainer.add(this.namePlayeurLabel);
        boxContainer.add(this.turnLabel);
        boxContainer.add(Box.createVerticalStrut(10));
        boxContainer.add(this.tipCenter);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 0); // Espacement entre le bouton et le haut de la fenêtre
        gbc.anchor = GridBagConstraints.NORTHWEST;

        this.add(boxContainer);

        panelGame.add(this, gbc);
    }

    /**
     * Met à jour les informations affichées (nom du joueur, numéro du tour et conseil central).
     */
    public void updatePrintInfo() {
        this.namePlayeurLabel.setText(MessageFormat.format(this.gameActionHandler.getLang().getString("display.info.player"), this.gameActionHandler.getPlayerController().getCurrentPlayer().getName()));
        this.turnLabel.setText(MessageFormat.format(this.gameActionHandler.getLang().getString("display.info.turn"), this.gameActionHandler.getPlayerController().getCurrentPlayer().getTurn()));
        this.tipCenter.setText(this.gameActionHandler.getLang().getString("display.info.tip_center"));
    }
}
