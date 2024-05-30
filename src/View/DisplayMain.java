package View;

import Global.Configuration;
import Listener.ComponentActionListener;
import Listener.KeyActionListener;
import Listener.MouseActionListener;
import Pattern.GameActionHandler;
import Structure.FrameMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayMain {

    private final DisplayWin displayWin;
    private final DisplayAbort displayAbort;
    private final DisplayRestart displayRestart;
    private final DisplayGame displayGame;


    public DisplayMain(GameActionHandler gameActionHandler, JFrame frameOpening,
                       JFrame frameMenu, JFrame frameGame, JFrame frameRules) {
        FrameMetrics.setCurrentFrame(frameOpening);

        //Affichage de l'opening
        new DisplayOpening(frameOpening, gameActionHandler);
        FrameMetrics.setupFrame(frameOpening, true, JFrame.EXIT_ON_CLOSE);
        FrameMetrics.setFrameSize(frameOpening, new Dimension(Configuration.FRAME_WIDTH, Configuration.FRAME_HEIGHT)); //Mettre une taille par défaut
        FrameMetrics.setFullScreen(frameOpening);
        //frameOpening.setResizable(false);

        //Affichage du menu
        new DisplayConfigGame(frameMenu, gameActionHandler);
        FrameMetrics.setupFrame(frameMenu, false, JFrame.EXIT_ON_CLOSE);

        //Affichage du jeu
        this.displayGame = new DisplayGame(frameGame, gameActionHandler);
        FrameMetrics.setupFrame(frameGame, false, JFrame.EXIT_ON_CLOSE);


        //Ajouter les écouteurs
        new MouseActionListener(gameActionHandler, displayGame);
        new KeyActionListener(frameGame, gameActionHandler);
        new ComponentActionListener(frameGame, displayGame);

        //Affichage du pop up de fin de jeu
        this.displayWin = new DisplayWin(gameActionHandler);

        //Affichage du pop up d'abandon
        this.displayAbort = new DisplayAbort(gameActionHandler);

        //Affichage du pop up pour recommencer la partie
        this.displayRestart = new DisplayRestart(gameActionHandler);

        //Affichage des regles
        DisplayRules displayRules = new DisplayRules(frameRules, gameActionHandler);
        FrameMetrics.setupFrame(frameRules, false, JFrame.DISPOSE_ON_CLOSE);
        FrameMetrics.setFrameSize(frameRules, new Dimension(700, 800));
    }

    public DisplayGame getDisplayGame() {
        return this.displayGame;
    }

    public DisplayWin getDisplayWin() {
        return this.displayWin;
    }

    public DisplayAbort getDisplayAbort() {
        return this.displayAbort;
    }

    public DisplayRestart getDisplayRestart() {
        return this.displayRestart;
    }
}