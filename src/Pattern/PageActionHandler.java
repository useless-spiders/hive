package Pattern;

import Controller.PageManager;

public interface PageActionHandler {
    void openingToMenu();
    //void gameToMenu(PageManager pageManager);
    void menuToGame();
    void gameToMenu();
    //void gameToWinFrame(PageManager pageManager);
    void winFrameToMenu();
}
