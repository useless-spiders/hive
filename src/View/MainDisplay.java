package View;

import Controller.Game;
import Listener.MouseActionListener;
import Model.HexGrid;
import Pattern.PageActionHandler;

import javax.swing.*;

public class MainDisplay {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;

    JFrame frameGame;

    public MainDisplay(PageActionHandler pageActionHandler, JFrame frameOpening, JFrame frameMenu, JFrame frameGame){
        new DisplayOpening(frameOpening, pageActionHandler);
        setupFrame(frameOpening, true);

        new DisplayConfigParty(frameMenu, pageActionHandler);
        setupFrame(frameMenu, false);

        initializeGame(pageActionHandler, frameGame);
    }

    private JFrame setupFrame(JFrame frame, boolean isVisible) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(isVisible);
        return frame;
    }

    private void initializeGame(PageActionHandler pageActionHandler, JFrame frameGame) {
        HexGrid hexGrid = new HexGrid();
        Game g = new Game(hexGrid);
        this.frameGame = setupFrame(frameGame, false);
        DisplayGame displayGame = new DisplayGame(hexGrid, this.frameGame, g, pageActionHandler);
        g.setDisplayGame(displayGame);

        MouseActionListener mouseActionListener = new MouseActionListener(g);
        displayGame.addMouseListener(mouseActionListener);
        displayGame.addMouseMotionListener(mouseActionListener);
    }
}