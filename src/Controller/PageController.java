package Controller;

import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.FrameMetrics;
import View.DisplayMain;

import javax.swing.*;

public class PageController implements PageActionHandler {
    private JFrame frameOpening = new JFrame();
    private JFrame frameGame = new JFrame("Hive game");
    private JFrame frameMenu = new JFrame();
    private JFrame frameRules = new JFrame();
    private DisplayMain displayMain;

    public PageController(GameActionHandler gameActionHandler){
        displayMain = new DisplayMain(this, gameActionHandler, this.frameOpening, this.frameMenu, this.frameGame, this.frameRules);
        gameActionHandler.setDisplayGame(this.displayMain.getDisplayGame());
    }

    @Override
    public void openingToMenu() {
        FrameMetrics.switchFrame(this.frameMenu);
    }

    @Override
    public void menuToGame() {
        FrameMetrics.switchFrame(this.frameGame);
    }

    @Override
    public void winToMenu() {
        FrameMetrics.switchFrame(this.frameMenu);
    }

    @Override
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

    public DisplayMain getDisplayMain() {
        return this.displayMain;
    }
}
