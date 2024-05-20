package Pattern;

import View.MainDisplay;

public interface PageActionHandler {
    void openingToMenu();
    void menuToGame();
    void gameToMenu();
    void winToMenu();
    void winToGame();
    void gameAndAbort();
    void gameAndWin();
    void disposeGame();
    MainDisplay getMainDisplay();
}