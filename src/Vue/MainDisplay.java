package Vue;

import Controleur.ChangePage;
import Controleur.Game;
import Controleur.PageManager;
import Modele.HexGrid;

import javax.swing.*;

public class MainDisplay {
    JFrame frameOpening;
    JFrame frameGame;

    public MainDisplay(PageManager pageManager, JFrame frameOpening, JFrame frameGame){
        /*affichage de l'opening*/
        this.frameOpening = frameOpening;
        ChangePage changePage = new ChangePage();
        DisplayOpening displayOpening = new DisplayOpening(frameOpening, pageManager, changePage);
        frameOpening.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameOpening.setSize(1280, 720);
        frameOpening.setVisible(true);

        /*affichage du jeu*/
        this.frameGame = frameGame;

        frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGame.setSize(1280, 720);
        frameGame.setVisible(false);

        HexGrid hexGrid = new HexGrid();
        Game g = new Game(hexGrid);
        Display display = new Display(hexGrid, frameGame, g);


        g.setDisplay(display);

        display.addMouseListener(g);
        display.addMouseMotionListener(g);
    }


}
