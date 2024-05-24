package View;

import Pattern.GameActionHandler;

import javax.swing.*;
import java.awt.*;

public class DisplayRestart extends JPanel {
    private GameActionHandler gameActionHandler;

    public DisplayRestart(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    public void printAskRestart() {
        int choice = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment recommencer la partie ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            this.gameActionHandler.restartGameWithSamePlayers();
        } else {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        }
    }
}
