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
    void gameAndRule();
    void ruleToGame();
    void disposeGame();
    DisplayMain getDisplayMain();
    DisplayBackground getDisplayBackground();
}