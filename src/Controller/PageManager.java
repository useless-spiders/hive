package Controller;

import Pattern.PageActionHandler;
import Structure.FrameMetrics;
import View.MainDisplay;

import javax.swing.*;
import java.awt.*;

public class PageManager implements PageActionHandler {
    private JFrame frameOpening = new JFrame();
    private JFrame frameGame = new JFrame("Hive game");
    private JFrame frameMenu = new JFrame();
    private JFrame frameWin = new JFrame();
    private JFrame frameAbort = new JFrame();

    public PageManager(Game game){
        new MainDisplay(this, game, this.frameOpening, this.frameMenu, this.frameWin, this.frameGame);
    }

    private void switchFrame(JFrame frame1, JFrame frame2){
        Dimension frameSize = FrameMetrics.getFrameSize(frame1);
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
    public void gameToWin() {this.switchFrame(this.frameGame, this.frameWin);}

    @Override
    public void gameAndAbort() {
        frameGame.setVisible(true);
        frameAbort.setVisible(true);
    }

    public void gameAndWin() {
        frameGame.setVisible(true);
        frameWin.setVisible(true);
    }

}
