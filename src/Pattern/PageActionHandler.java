package Pattern;

import Controleur.PageManager;

public interface PageActionHandler {
    void openingToMenu(PageManager pageManager);
    //void gameToMenu(PageManager pageManager);
    void menuToGame(PageManager pageManager);
    void gameToMenu(PageManager pageManager);
    //void gameToWinFrame(PageManager pageManager);
    void winFrameToMenu(PageManager pageManager);
}
