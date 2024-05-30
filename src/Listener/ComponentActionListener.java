package Listener;

import View.DisplayGame;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


/**
 * Listener pour les actions sur les composants
 */
public class ComponentActionListener extends ComponentAdapter {
    JFrame frameGame;
    DisplayGame displayGame;

    /**
     * Constructeur
     *
     * @param frameGame   Fenêtre du jeu
     * @param displayGame Affichage du jeu
     */
    public ComponentActionListener(JFrame frameGame, DisplayGame displayGame) {
        this.frameGame = frameGame;
        this.displayGame = displayGame;

        frameGame.addComponentListener(this);
    }

    /**
     * Recentre le jeu quand on change la taille de la fenêtre
     *
     * @param componentEvent ComponentEvent
     */
    @Override
    public void componentResized(ComponentEvent componentEvent) {
        displayGame.centerGame();
    }
}

