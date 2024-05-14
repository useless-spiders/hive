package Controleur;

import Pattern.PageActionHandler;
import Vue.MainDisplay;

import javax.swing.*;

public class PageManager implements PageActionHandler {
    private JFrame frameOpening;
    private JFrame frameGame;

    public PageManager() {
        // Initialisation des deux JFrame
        this.frameOpening = new JFrame();
        this.frameGame = new JFrame();
    }

    public void start() {
        MainDisplay mainDisplay = new MainDisplay(frameOpening, frameGame);
    }

    public void menuToGame(){
        frameOpening.setVisible(false);
        frameGame.setVisible(true);
    }

    @Override
    public void openingToGame(){
        System.out.println("opening to game");
    }
}
