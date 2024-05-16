package View;

import Controller.Game;
import Listener.MouseActionListener;
import Model.HexGrid;
import Pattern.PageActionHandler;

import javax.swing.*;

public class MainDisplay {
    JFrame frameOpening;
    JFrame frameGame;

    public MainDisplay(PageActionHandler pageActionHandler, JFrame frameOpening, JFrame frameMenu, JFrame frameGame){
        /*affichage de l'opening*/
        this.frameOpening = frameOpening;

        DisplayOpening displayOpening = new DisplayOpening(frameOpening, pageActionHandler);
        frameOpening.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameOpening.setSize(1280, 720);
        frameOpening.setVisible(true);

        /*Affichage de la config*/
        DisplayConfigParty displayConfigParty = new DisplayConfigParty(frameMenu, pageActionHandler);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setSize(1280, 720);
        frameMenu.setVisible(false);

        /*affichage du jeu*/
        this.frameGame = frameGame;
        HexGrid hexGrid = new HexGrid();
        Game g = new Game(hexGrid);

        DisplayGame displayGame = new DisplayGame(hexGrid, frameGame, g, pageActionHandler);
        frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGame.setSize(1280, 720);
        frameGame.setVisible(false);

        g.setDisplayGame(displayGame);

        MouseActionListener mouseActionListener = new MouseActionListener(g);
        displayGame.addMouseListener(mouseActionListener);
        displayGame.addMouseMotionListener(mouseActionListener);
    }


}
