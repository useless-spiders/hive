package Controleur;

import Pattern.PageActionHandler;

public class ChangePage implements PageActionHandler {

    @Override
    public void openingToGame(PageManager pageManager){
        pageManager.menuToGame();
    }
}
