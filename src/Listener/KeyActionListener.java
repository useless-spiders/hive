package Listener;

import Model.HexGrid;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.ViewMetrics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class KeyActionListener implements KeyListener {
    private GameActionHandler gameActionHandler;
    private JFrame frameGame;

    public KeyActionListener(JFrame frameGame, GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.frameGame = frameGame;

        frameGame.addKeyListener(this);
        frameGame.setFocusable(true);

        // Ajout d'un FocusListener pour rétablir le focus
        frameGame.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                frameGame.requestFocusInWindow();
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_C) {
            this.centerGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void centerGame() {
        HexGrid hexGrid = gameActionHandler.getGrid();
        if (!hexGrid.getGrid().isEmpty()) {
            // Récupérer le 1er hexagone de la grille et convertir en pixel
            HexCoordinate hexCoordinate = hexGrid.getGrid().keySet().iterator().next();
            Point hexCoordinatePoint = HexMetrics.hexToPixel(hexCoordinate);
            hexCoordinatePoint.x += ViewMetrics.getViewOffsetX();
            hexCoordinatePoint.y += ViewMetrics.getViewOffsetY();

            // Calculer le centre de la vue en pixels
            HexCoordinate hexCenter = HexMetrics.hexCenterCoordinate(this.gameActionHandler.getDisplayGame().getWidth(), this.gameActionHandler.getDisplayGame().getHeight());
            Point hexCenterPoint = HexMetrics.hexToPixel(hexCenter);

            // Soustraire hexCoordinate à hexCenter
            int dx = hexCenterPoint.x - hexCoordinatePoint.x;
            int dy = hexCenterPoint.y - hexCoordinatePoint.y;

            ViewMetrics.updateViewPosition(dx, dy);

            this.gameActionHandler.getDisplayGame().repaint();
        }
    }
}
