package Pattern;

import Controller.PageManager;
import View.MainDisplay;

public interface PageActionHandler {
    void openingToMenu();
    void menuToGame();
    void gameToMenu();
    void winToMenu();
    void gameAndAbort();
    void gameAndWin();
    MainDisplay getMainDisplay();
}