package Vue;

import Controleur.ChangePage;
import Controleur.Game;
import Controleur.PageManager;
import Modele.HexGrid;

import javax.swing.*;

public class MainDisplay {
    JFrame frameOpening;
    JFrame frameGame;

    public MainDisplay(PageManager pageManager, JFrame frameOpening, JFrame frameMenu, JFrame frameGame){
        ChangePage changePage = new ChangePage();
        /*affichage de l'opening*/
        this.frameOpening = frameOpening;

        DisplayOpening displayOpening = new DisplayOpening(frameOpening, pageManager, changePage);
        frameOpening.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameOpening.setSize(1280, 720);
        frameOpening.setVisible(true);

        DisplayConfigParty displayConfigParty = new DisplayConfigParty(frameMenu, pageManager, changePage);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setSize(1280, 720);
        frameMenu.setVisible(false);

        /*affichage du jeu*/
        this.frameGame = frameGame;

        frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGame.setSize(1280, 720);
        frameGame.setVisible(false);

        HexGrid hexGrid = new HexGrid();
        Game g = new Game(hexGrid);
        DisplayGame displayGame = new DisplayGame(hexGrid, frameGame, g, pageManager, changePage);


        g.setDisplay(displayGame);

        displayGame.addMouseListener(g);
        displayGame.addMouseMotionListener(g);
    }


}
