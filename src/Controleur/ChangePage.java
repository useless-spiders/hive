package Controleur;

import Pattern.PageActionHandler;

public class ChangePage implements PageActionHandler {

    @Override
    public void openingToMenu(PageManager pageManager){
        pageManager.openingToMenu();
    }

    @Override
    public void menuToGame(PageManager pageManager) {
        pageManager.menuToGame();
    }

    @Override
    public void gameToMenu(PageManager pageManager) {
        pageManager.gameToMenu();
    }
}
