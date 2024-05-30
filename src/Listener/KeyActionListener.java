package Listener;

import Pattern.GameActionHandler;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Listener pour les actions sur les touches
 */
public class KeyActionListener extends KeyAdapter {
    private final GameActionHandler gameActionHandler;
    private final JFrame frameGame;

    /**
     * Constructeur
     *
     * @param frameGame         Fenêtre du jeu
     * @param gameActionHandler GameActionHandler
     */
    public KeyActionListener(JFrame frameGame, GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.frameGame = frameGame;

        frameGame.addKeyListener(this);
        frameGame.setFocusable(true);

        // Ajout d'un FocusListener pour rétablir le focus
        frameGame.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                frameGame.requestFocusInWindow();
            }
        });
    }

    /**
     * Recentrage du jeu quand on appuie sur la touche C
     *
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_C) {
            this.gameActionHandler.getDisplayGame().centerGame();
        }
    }
}
