package View;

import Pattern.GameActionHandler;

import javax.swing.*;
import java.awt.*;

public class DisplayAbort extends JPanel {
    private final GameActionHandler gameActionHandler;

    /**
     * Constructeur pour DisplayAbort.
     *
     * @param gameActionHandler GameActionHandler
     */
    public DisplayAbort(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    /**
     * Affiche un dialogue demandant si l'utilisateur veut abandonner.
     */
    public void printAskAbort() {
        int choice = JOptionPane.showOptionDialog(null, this.gameActionHandler.getLang().getString("display.abort.message"), this.gameActionHandler.getLang().getString("display.abort.title"),
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{this.gameActionHandler.getLang().getString("display.abort.option.yes"), this.gameActionHandler.getLang().getString("display.abort.option.no")}, null);

        if (choice == JOptionPane.YES_OPTION) {
            this.gameActionHandler.getPageController().abortToMenu();
            this.gameActionHandler.getPageController().disposeGame();
        } else {
            this.gameActionHandler.getAiController().stopAi();
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        }
    }
}
