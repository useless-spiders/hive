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

    public PageManager(Game game){
        displayMain = new DisplayMain(this, game, this.frameOpening, this.frameMenu, this.frameGame, this.frameWin, this.frameRules);
    }

    private void switchFrame(JFrame frame1, JFrame frame2, JFrame size){
        Dimension frameSize = FrameMetrics.getFrameSize(size);
        frame1.setVisible(false);

        frame2.setSize(frameSize.width, frameSize.height);
        frame2.setVisible(true);
    }

    @Override
    public void openingToMenu() {
        this.switchFrame(this.frameOpening, this.frameMenu, this.frameOpening);
    }

    @Override
    public void menuToGame() {
        this.switchFrame(this.frameMenu, this.frameGame, this.frameMenu);
    }

    @Override
    public void gameToMenu() {
        this.switchFrame(this.frameGame, this.frameMenu, this.frameGame);
    }

    @Override
    public void winToMenu() {
        this.switchFrame(this.frameWin, this.frameMenu, this.frameGame);
    }

    @Override
    public void winToGame(){
        this.switchFrame(this.frameWin, this.frameGame, this.frameGame);
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
