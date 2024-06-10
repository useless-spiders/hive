package View;

import Pattern.GameActionHandler;

import javax.swing.*;
import java.awt.*;

public class DisplayRestart extends JPanel {
    private final GameActionHandler gameActionHandler;

    /**
     * Constructeur pour DisplayRestart.
     *
     * @param gameActionHandler GameActionHandler
     */
    public DisplayRestart(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    /**
     * Affiche une boîte de dialogue demandant si l'utilisateur souhaite redémarrer le jeu.
     */
    public void printAskRestart() {
        int choice = JOptionPane.showOptionDialog(null, this.gameActionHandler.getLang().getString("display.restart.message"), this.gameActionHandler.getLang().getString("display.restart.title"),
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{this.gameActionHandler.getLang().getString("display.restart.option.yes"), this.gameActionHandler.getLang().getString("display.restart.option.no")}, null);
        if (choice == JOptionPane.YES_OPTION) {
            this.gameActionHandler.restartGameWithSamePlayers();
        } else {
            this.gameActionHandler.getAiController().startAi();
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        }
    }
}
