package Pattern;

import View.DisplayMain;

public interface PageActionHandler {
    void openingToMenu();
    void menuToGame();
    void gameToMenu();
    void winToMenu();
    void winToGame();
    void gameAndAbort();
    void gameAndWin();
    void disposeGame();
    DisplayMain getDisplayMain();
}