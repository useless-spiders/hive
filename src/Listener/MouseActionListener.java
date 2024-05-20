package Listener;

import Model.HexCell;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.ViewMetrics;

import java.awt.event.*;

public class MouseActionListener extends MouseAdapter implements MouseMotionListener, MouseWheelListener {
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
        int x = e.getX();
        int y = e.getY();
        ViewMetrics.updateViewPosition(x - this.lastX, y - this.lastY);
        this.gameActionHandler.getDisplayGame().repaint();
        this.lastX = x;
        this.lastY = y;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        int oldHexWidth = HexMetrics.HEX_WIDTH;
        int oldHexHeight = HexMetrics.HEX_HEIGHT;

        if (notches < 0 && HexMetrics.HEX_WIDTH < HexMetrics.MAX_HEX_WIDTH) {
            // Molette tournée vers le haut
            HexMetrics.updateHexMetricsWidth(5);
        } else if (notches > 0 && HexMetrics.HEX_WIDTH > HexMetrics.MIN_HEX_WIDTH) {
            // Molette tournée vers le bas
            HexMetrics.updateHexMetricsWidth(-5);
        }

        int widthDifference = HexMetrics.HEX_WIDTH - oldHexWidth;
        int heightDifference = HexMetrics.HEX_HEIGHT - oldHexHeight;

        // Adjust the view position based on the change in hexagon size
        ViewMetrics.updateViewPosition(-widthDifference, -heightDifference);

        this.gameActionHandler.getDisplayGame().repaint();
    }
}