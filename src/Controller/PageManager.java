package Controller;

import Pattern.PageActionHandler;
import View.MainDisplay;

import javax.swing.*;
import java.awt.*;

public class PageManager implements PageActionHandler {
    private JFrame frameOpening;
    private JFrame frameMenu;
    private JFrame frameGame;
    private JFrame frameWinFrame;


    public PageManager() {
        this.frameOpening = new JFrame();
        this.frameGame = new JFrame("Hive game");
        this.frameMenu = new JFrame();
        this.frameWinFrame = new JFrame();
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
        Dimension frameSize = frameMenu.getSize();
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

    public void gameToOpening(){
        frameOpening.setVisible(false);
        frameGame.setVisible(true);
    }

    public void gameToWinFrame(){
        frameWinFrame.setVisible(true);
        frameGame.setVisible(false);
    }

    @Override
    public void winFrameToMenu(){
        frameWinFrame.setVisible(false);
        frameMenu.setVisible(true);
    }

}
