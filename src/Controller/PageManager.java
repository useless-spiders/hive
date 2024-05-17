package Controller;

import Pattern.PageActionHandler;
import Structure.FrameMetrics;
import View.MainDisplay;

import javax.swing.*;
import java.awt.*;

public class PageManager implements PageActionHandler {
    private JFrame frameOpening;
    private JFrame frameMenu;
    private JFrame frameGame;
    private JFrame frameWin;
    private JFrame frameAbort;


    public PageManager() {
        this.frameOpening = new JFrame();
        this.frameGame = new JFrame("Hive game");
        this.frameMenu = new JFrame();
        this.frameWin = new JFrame();
        this.frameAbort = new JFrame();
    }

    public void start() {
        MainDisplay mainDisplay = new MainDisplay(this, frameOpening, frameMenu, frameGame);
    }

    @Override
    public void openingToMenu(){
        Dimension frameSize = frameOpening.getSize();
        frameOpening.setVisible(false);
        frameMenu.setSize(frameSize.width, frameSize.height);
        frameMenu.setVisible(true);
    }

    @Override
    public void menuToGame(){
        Dimension frameSize = FrameMetrics.getFrameSize(frameMenu);
        frameMenu.setVisible(false);
        frameGame.setSize(frameSize.width, frameSize.height);
        frameGame.setVisible(true);
    }

    @Override
    public void gameToMenu(){
        Dimension frameSize = frameGame.getSize();
        frameGame.setVisible(false);
        frameMenu.setSize(frameSize.width, frameSize.height);
        frameMenu.setVisible(true);

    }

    public void gameAndWin(){
        frameGame.setVisible(true);
        frameWin.setVisible(true);
    }

    @Override
    public void winToMenu(){
        frameWin.setVisible(false);
        frameMenu.setVisible(true);
    }

    @Override
    public void gameAndAbort() {
        frameGame.setVisible(true);
        frameAbort.setVisible(true);
    }

}
