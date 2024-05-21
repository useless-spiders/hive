package Listener;

import View.DisplayGame;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ComponentActionListener implements ComponentListener {
    JFrame frameGame;
    DisplayGame displayGame;

    public ComponentActionListener(JFrame frameGame, DisplayGame displayGame) {
        this.frameGame = frameGame;
        this.displayGame = displayGame;

        frameGame.addComponentListener(this);
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        displayGame.centerGame();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}

