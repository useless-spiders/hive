package View;

import Pattern.GameActionHandler;

import javax.swing.*;
import java.awt.*;

public class DisplayAbort extends JPanel {
    private GameActionHandler gameActionHandler;

    public DisplayAbort(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    public void printAskAbort() {
        int choice = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment abandonner ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            this.gameActionHandler.getPageActionHandler().abortToMenu();
            this.gameActionHandler.getPageActionHandler().disposeGame();
        } else {
            this.gameActionHandler.getAiController().changeStateAi();
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        }
    }
}
