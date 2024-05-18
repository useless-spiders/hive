package Listener;

import Model.HexCell;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.ViewMetrics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseActionListener extends MouseAdapter implements MouseMotionListener {
    private GameActionHandler gameActionHandler;
    private int lastX;
    private int lastY;
    private HexCoordinate hoverCell;

    public MouseActionListener(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX() - ViewMetrics.getViewOffsetX();
        int y = e.getY() - ViewMetrics.getViewOffsetY();
        HexCoordinate newHoverCell = HexMetrics.pixelToHex(x, y);
        if (!newHoverCell.equals(this.hoverCell) && this.gameActionHandler.getPlayableCoordinates().contains(newHoverCell)) {
            this.hoverCell = newHoverCell;
            this.gameActionHandler.getDisplayGame().getDisplayPlayableHex().updateHoverCell(this.hoverCell);

            this.gameActionHandler.getDisplayGame().repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.gameActionHandler.launchAi();
        this.lastX = e.getX();
        this.lastY = e.getY();
        int x = this.lastX - ViewMetrics.getViewOffsetX();
        int y = this.lastY - ViewMetrics.getViewOffsetY();
        HexCoordinate hexagon = HexMetrics.pixelToHex(x, y);
        HexCell cell = this.gameActionHandler.getGrid().getCell(hexagon);

        if (cell != null) { //on clique sur une case existante pour la déplacer ou bien pour être un insecte cible du scarabée
            this.gameActionHandler.handleCellClicked(cell, hexagon);
        } else if (this.gameActionHandler.getIsInsectCellClicked()) { //on clique sur une case vide pour déplacer une case sélectionnée
            this.gameActionHandler.handleInsectMoved(hexagon);
        } else if (this.gameActionHandler.getIsInsectButtonClicked()) { //on clique sur une case vide pour déposer une nouvelle case
            this.gameActionHandler.handleInsectPlaced(hexagon);
        }

        this.gameActionHandler.getDisplayGame().repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX() - this.lastX;
        int y = e.getY() - this.lastY;
        ViewMetrics.updateViewPosition(x, y);
        this.gameActionHandler.getDisplayGame().repaint();
        this.lastX = x;
        this.lastY = y;
    }
}