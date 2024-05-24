package View;

import Pattern.PageActionHandler;

import javax.swing.*;
import java.awt.*;

public class DisplayAbort extends JPanel {
    private PageActionHandler pageActionHandler;

    public DisplayAbort(PageActionHandler pageActionHandler) {
        this.pageActionHandler = pageActionHandler;
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
