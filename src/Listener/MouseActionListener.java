package Listener;

import Controller.Game;
import Structure.ViewMetrics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseActionListener extends MouseAdapter implements MouseMotionListener {
    private Game game;
    private int lastX;
    private int lastY;

    public MouseActionListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        game.mouseMoved(e.getX() - ViewMetrics.getViewOffsetX(), e.getY() - ViewMetrics.getViewOffsetY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.lastX = e.getX();
        this.lastY = e.getY();
        game.mousePressed(this.lastX - ViewMetrics.getViewOffsetX(), this.lastY - ViewMetrics.getViewOffsetY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        game.mouseDragged(x - this.lastX, y - this.lastY);
        this.lastX = x;
        this.lastY = y;
    }
}