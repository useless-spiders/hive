package Vue;

import Controleur.Game;
import Modele.HexGrid;

import javax.swing.*;

public class MainDisplay {
    JFrame frameOpening;
    JFrame frameGame;

    public MainDisplay(JFrame frameOpening, JFrame frameGame){
        /*affichage de l'opening*/
        this.frameOpening = frameOpening;
        DisplayOpening displayOpening = new DisplayOpening(frameOpening);
        frameOpening.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameOpening.setSize(1280, 720);
        frameOpening.setVisible(false);

        /*affichage du jeu*/
        this.frameGame = frameGame;

        frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGame.setSize(1280, 720);
        frameGame.setVisible(true);

        HexGrid hexGrid = new HexGrid();
        Game g = new Game(hexGrid);
        Display display = new Display(hexGrid, frameGame, g);


        g.setDisplay(display);

        display.addMouseListener(g);
        display.addMouseMotionListener(g);
    }


}
