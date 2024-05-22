package Controller;

import Pattern.PageActionHandler;
import Structure.FrameMetrics;
import View.DisplayMain;

import javax.swing.*;
import java.awt.*;

public class PageManager implements PageActionHandler {
    private JFrame frameOpening = new JFrame();
    private JFrame frameGame = new JFrame("Hive game");
    private JFrame frameMenu = new JFrame();
    private JFrame frameWin = new JFrame();
    private JFrame frameAbort = new JFrame();
    private DisplayMain displayMain;

    public PageManager(Game game){
        displayMain = new DisplayMain(this, game, this.frameOpening, this.frameMenu, this.frameGame, this.frameWin);
    }

    private void switchFrame(JFrame frame1, JFrame frame2){
        Dimension frameSize = FrameMetrics.getFrameSize(frame2);
        frame1.setVisible(false);

        frame2.setSize(frameSize.width, frameSize.height);
        frame2.setVisible(true);
    }

    @Override
    public void openingToMenu() {
        this.switchFrame(this.frameOpening, this.frameMenu);
    }

    @Override
    public void menuToGame() {
        this.switchFrame(this.frameMenu, this.frameGame);
    }

    @Override
    public void gameToMenu() {
        this.switchFrame(this.frameGame, this.frameMenu);
    }

    @Override
    public void winToMenu() {
        this.switchFrame(this.frameWin, this.frameMenu);
    }

    @Override
    public void winToGame(){
        this.switchFrame(this.frameWin, this.frameGame);
    }

    @Override
    public void gameAndAbort() {
        frameGame.setVisible(true);
        frameAbort.setVisible(true);
    }

    public void gameAndWin() {
        frameGame.setVisible(true);
        frameWin.setVisible(true);
    }

    public void disposeGame(){frameGame.dispose();}

    public DisplayMain getDisplayMain() {
        return this.displayMain;
    }
}
