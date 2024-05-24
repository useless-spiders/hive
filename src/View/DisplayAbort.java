package View;

import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayAbort extends JPanel {
    private static final String YES = "Oui";
    private static final String NO = "Non";
    private PageActionHandler pageActionHandler;
    private GameActionHandler gameActionHandler;
    private GridBagConstraints gbc;

    public DisplayAbort(PageActionHandler pageActionHandler, GameActionHandler gameActionHandler) {
        this.pageActionHandler = pageActionHandler;
        this.gameActionHandler = gameActionHandler;
    }

    public void printAskAbort() {
        int choice = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment abandonner ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            this.pageActionHandler.abortToMenu();
            this.pageActionHandler.disposeGame();
        } else {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        }
    }
}
