package Controller;

import Pattern.PageActionHandler;
import View.DisplayAbort;
import View.DisplayMain;

import javax.swing.*;

public class PageManager implements PageActionHandler {
    private JFrame frameOpening = new JFrame();
    private JFrame frameGame = new JFrame("Hive game");
    private JFrame frameMenu = new JFrame();
    private JFrame frameWin = new JFrame();
    private JFrame frameRules = new JFrame();
    private DisplayMain displayMain;
    private JFrame currentFrame;

    public PageManager(Game game){
        displayMain = new DisplayMain(this, game, this.frameOpening, this.frameMenu, this.frameGame, this.frameWin, this.frameRules);
        this.currentFrame = this.displayMain.getCurrentFrame();
    }

    private void switchFrame(JFrame nextFrame){
        int currentWidth = this.currentFrame.getWidth();
        int currentHeight = this.currentFrame.getHeight();
        boolean isFullScreen = (this.currentFrame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH && this.currentFrame.isUndecorated();

        if (isFullScreen) {
            displayMain.setFullScreen(nextFrame);
        } else {
            nextFrame.setSize(currentWidth, currentHeight);
        }

        this.currentFrame.setVisible(false);
        nextFrame.setVisible(true);

        this.currentFrame = nextFrame;
    }

    @Override
    public void openingToMenu() {
        this.switchFrame(this.frameMenu);
    }

    @Override
    public void menuToGame() {
        this.switchFrame(this.frameGame);
    }

    @Override
    public void winToMenu() {
        this.switchFrame(this.frameMenu);
    }

    @Override
    public void winToGame(){
        this.switchFrame(this.frameGame);
    }

    @Override
    public void gameAndAbort() {
        this.frameGame.setVisible(true);
        this.displayMain.getDisplayAbort().printAskAbort();
    }

    public void gameAndWin() {
        this.frameGame.setVisible(true);
        this.frameWin.setVisible(true);
    }

    public void gameAndRules() {
        this.frameGame.setVisible(true);
        this.frameRules.setVisible(true);
    }

    public void disposeGame() {
        frameGame.dispose();
    }

    public void abortToMenu() {
        this.switchFrame(this.frameMenu);
    }

    public void gameAndRestart() {
        this.frameGame.setVisible(true);
        this.displayMain.getDisplayRestart().printAskRestart();
    }

    public DisplayMain getDisplayMain() {
        return this.displayMain;
    }
}
