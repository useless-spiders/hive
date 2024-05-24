package Pattern;

import View.DisplayMain;

public interface PageActionHandler {
    void openingToMenu();
    void menuToGame();
    void winToMenu();
    void winToGame();
    void gameAndAbort();
    void gameAndWin();
    void gameAndRules();
    void disposeGame();
    void abortToMenu();
    DisplayMain getDisplayMain();
}