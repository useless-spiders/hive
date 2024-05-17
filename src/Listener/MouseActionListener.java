package Listener;

import Pattern.GameActionHandler;
import Structure.ViewMetrics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseActionListener extends MouseAdapter implements MouseMotionListener {
    private GameActionHandler gameActionHandler;
    private int lastX;
    private int lastY;

    public MouseActionListener(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.gameActionHandler.mouseMoved(e.getX() - ViewMetrics.getViewOffsetX(), e.getY() - ViewMetrics.getViewOffsetY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.lastX = e.getX();
        this.lastY = e.getY();
        this.gameActionHandler.mousePressed(this.lastX - ViewMetrics.getViewOffsetX(), this.lastY - ViewMetrics.getViewOffsetY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        this.gameActionHandler.mouseDragged(x - this.lastX, y - this.lastY);
        this.lastX = x;
        this.lastY = y;
    }
}