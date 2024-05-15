package Controleur;

import Pattern.PageActionHandler;
import Vue.MainDisplay;

import javax.swing.*;

public class PageManager {
    private JFrame frameOpening;
    private JFrame frameMenu;
    private JFrame frameGame;

    public PageManager() {
        // Initialisation des deux JFrame
        this.frameOpening = new JFrame();
        this.frameGame = new JFrame();
        this.frameMenu = new JFrame();
    }

    public void start() {
        MainDisplay mainDisplay = new MainDisplay(this, frameOpening, frameMenu, frameGame);
    }

    public void openingToMenu(){
        frameOpening.setVisible(false);
        frameMenu.setVisible(true);
    }

    public void menuToGame(){
        frameMenu.setVisible(false);
        frameGame.setVisible(true);
    }

    public void gameToMenu(){
        frameGame.setVisible(false);
        frameMenu.setVisible(true);

    }

    public void gameToOpening(){
        frameOpening.setVisible(false);
        frameGame.setVisible(true);
    }

}
