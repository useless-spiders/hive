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
        int choice = JOptionPane.showConfirmDialog(null, this.gameActionHandler.getMessages().getString("display.abort.message"), this.gameActionHandler.getMessages().getString("display.abort.title"), JOptionPane.YES_NO_OPTION);
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
