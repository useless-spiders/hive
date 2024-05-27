package Controller;

import Pattern.GameActionHandler;
import Structure.FrameMetrics;
import View.DisplayMain;

import javax.swing.*;

/**
 * Controleur pour les pages
 */
public class PageController {
    private JFrame frameOpening = new JFrame();
    private JFrame frameGame = new JFrame("Hive game");
    private JFrame frameMenu = new JFrame();
    private JFrame frameRules = new JFrame();
    private DisplayMain displayMain;

    /**
     * Constructeur
     * @param gameActionHandler GameActionHandler
     */
    public PageController(GameActionHandler gameActionHandler){
        displayMain = new DisplayMain(gameActionHandler, this.frameOpening, this.frameMenu, this.frameGame, this.frameRules);
        gameActionHandler.setDisplayGame(this.displayMain.getDisplayGame());
    }

    /*
     * Méthodes de transition entre les pages
     */
    public void openingToMenu() {
        FrameMetrics.switchFrame(this.frameMenu);
    }

    public void menuToGame() {
        FrameMetrics.switchFrame(this.frameGame);
    }

    public void winToMenu() {
        FrameMetrics.switchFrame(this.frameMenu);
    }

    public void gameAndAbort() {
        this.frameGame.setVisible(true);
        this.displayMain.getDisplayAbort().printAskAbort();
    }

    public void gameAndWin() {
        this.frameGame.setVisible(true);
        this.displayMain.getDisplayWin().printVictoryDialog();
    }

    public void gameAndRules() {
        this.frameGame.setVisible(true);
        this.frameRules.setVisible(true);
    }

    public void disposeGame() {
        frameGame.dispose();
    }

    public void abortToMenu() {
        FrameMetrics.switchFrame(this.frameMenu);
    }

    public void gameAndRestart() {
        this.frameGame.setVisible(true);
        this.displayMain.getDisplayRestart().printAskRestart();
    }

    /**
     * Renvoie le displayMain
     * @return DisplayMain
     */
    public DisplayMain getDisplayMain() {
        return this.displayMain;
    }
}
