package View;

import Pattern.GameActionHandler;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

public class DisplayInfoInGame extends JPanel {
    private JLabel turnLabel;
    private JLabel namePlayeurLabel;
    private JLabel tipCenter;
    private GameActionHandler gameActionHandler;

    public DisplayInfoInGame(JPanel panelGame, GridBagConstraints gbc, GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel

        JLabel infoLabel = new JLabel(this.gameActionHandler.getLang().getString("display.info.title"));
        this.namePlayeurLabel = new JLabel(MessageFormat.format(this.gameActionHandler.getLang().getString("display.info.player"), this.gameActionHandler.getPlayerController().getCurrentPlayer().getName()));
        this.turnLabel = new JLabel(MessageFormat.format(this.gameActionHandler.getLang().getString("display.info.turn"), this.gameActionHandler.getPlayerController().getCurrentPlayer().getTurn()));
        this.tipCenter = new JLabel(this.gameActionHandler.getLang().getString("display.info.tip_center"));

        JPanel boxContainer  = new JPanel();
        boxContainer.setLayout(new BoxLayout(boxContainer, BoxLayout.Y_AXIS));
        boxContainer.setOpaque(false);
        boxContainer.add(infoLabel);
        boxContainer.add(this.namePlayeurLabel);
        boxContainer.add(this.turnLabel);
        boxContainer.add(this.tipCenter);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 0); // Espacement entre le bouton et le haut de la fenêtre
        gbc.anchor = GridBagConstraints.NORTHWEST;

        add(boxContainer);

        panelGame.add(this, gbc);
    }

    public void updatePrintInfo() {
        this.namePlayeurLabel.setText(MessageFormat.format(this.gameActionHandler.getLang().getString("display.info.player"), this.gameActionHandler.getPlayerController().getCurrentPlayer().getName()));
        this.turnLabel.setText(MessageFormat.format(this.gameActionHandler.getLang().getString("display.info.turn"), this.gameActionHandler.getPlayerController().getCurrentPlayer().getTurn()));
        this.tipCenter.setText(this.gameActionHandler.getLang().getString("display.info.tip_center"));
    }
}
