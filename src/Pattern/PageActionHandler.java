package Pattern;

import Controller.PageManager;

public interface PageActionHandler {
    void openingToMenu();
    void menuToGame();
    void gameToMenu();
    void gameAndWin();
    void winToMenu();
    void gameAndAbort();
}
