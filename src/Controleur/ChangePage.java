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
}
