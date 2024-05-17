package Pattern;

import Controller.PageManager;

public interface PageActionHandler {
    void openingToMenu();
    void menuToGame();
    void gameToMenu();
    void gameAndWin();
    void gameToWin();
    void winToMenu();
    void gameAndAbort();
}
