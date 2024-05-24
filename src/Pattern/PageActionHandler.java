package Pattern;

import View.DisplayMain;

public interface PageActionHandler {
    void openingToMenu();
    void menuToGame();
    void winToMenu();
    void gameAndAbort();
    void gameAndWin();
    void gameAndRules();
    void disposeGame();
    void abortToMenu();
    void gameAndRestart();
    DisplayMain getDisplayMain();
}