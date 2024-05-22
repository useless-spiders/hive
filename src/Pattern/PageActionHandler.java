package Pattern;

import View.DisplayBackground;
import View.DisplayMain;

public interface PageActionHandler {
    void openingToMenu();
    void menuToGame();
    void gameToMenu();
    void winToMenu();
    void winToGame();
    void gameAndAbort();
    void gameAndWin();
    void gameAndRules();
    void rulesToGame();
    void disposeGame();
    DisplayMain getDisplayMain();
    DisplayBackground getDisplayBackground();
}