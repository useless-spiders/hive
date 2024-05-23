package Controller;

import Pattern.PageActionHandler;
import Structure.FrameMetrics;
import View.DisplayBackground;
import View.DisplayMain;

import javax.swing.*;
import java.awt.*;

public class PageManager implements PageActionHandler {
    private JFrame frameOpening = new JFrame();
    private JFrame frameGame = new JFrame("Hive game");
    private JFrame frameMenu = new JFrame();
    private JFrame frameWin = new JFrame();
    private JFrame frameAbort = new JFrame();
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
    public void gameToMenu() {
        this.switchFrame(this.frameMenu);
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
        this.frameAbort.setVisible(true);
    }

    public void gameAndWin() {
        this.frameGame.setVisible(true);
        this.frameWin.setVisible(true);
    }

    public void gameAndRules() {
        this.frameGame.setVisible(true);
        this.frameRules.setVisible(true);
    }

    public void rulesToGame(){this.frameRules.setVisible(false);}


    public void disposeGame(){frameGame.dispose();}

    public DisplayMain getDisplayMain() {
        return this.displayMain;
    }

    public DisplayBackground getDisplayBackground() {return this.displayMain.getDisplayBackground();}
}
