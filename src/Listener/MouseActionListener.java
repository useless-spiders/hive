package Listener;

import Global.Configuration;
import Model.HexCell;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.ViewMetrics;
import View.DisplayGame;

import java.awt.event.*;

/**
 * Listener pour les actions de la souris
 */
public class MouseActionListener extends MouseAdapter implements MouseMotionListener, MouseWheelListener {
    private final GameActionHandler gameActionHandler;
    private final DisplayGame displayGame;
    private int lastX;
    private int lastY;
    private HexCoordinate hoverCell;

    /**
     * Constructeur
     *
     * @param gameActionHandler GameActionHandler
     * @param displayGame       DisplayGame
     */
    public MouseActionListener(GameActionHandler gameActionHandler, DisplayGame displayGame) {
        this.gameActionHandler = gameActionHandler;
        this.displayGame = displayGame;

        this.displayGame.addMouseListener(this);
        this.displayGame.addMouseMotionListener(this);
        this.displayGame.addMouseWheelListener(this);
    }

    /**
     * Met en surbrillance l'hexagon survolé
     *
     * @return HexCoordinate
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX() - ViewMetrics.getViewOffsetX();
        int y = e.getY() - ViewMetrics.getViewOffsetY();
        HexCoordinate newHoverCell = HexMetrics.pixelToHex(x, y);
        if (!newHoverCell.equals(this.hoverCell) && this.gameActionHandler.getGameActionListener().getPlayableCoordinates().contains(newHoverCell)) {
            this.hoverCell = newHoverCell;
            this.displayGame.getDisplayPlayableHex().updateHoverCell(this.hoverCell);

            this.displayGame.repaint();
        }
    }

    /**
     * Gère les clics de souris
     *
     * @param e MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.lastX = e.getX();
        this.lastY = e.getY();
        int x = this.lastX - ViewMetrics.getViewOffsetX();
        int y = this.lastY - ViewMetrics.getViewOffsetY();
        HexCoordinate hexagon = HexMetrics.pixelToHex(x, y);
        HexCell cell = this.gameActionHandler.getGrid().getCell(hexagon);

        if (cell != null) { //on clique sur une case existante pour la déplacer ou bien pour être un insecte cible du scarabée
            this.gameActionHandler.getGameActionListener().handleCellClicked(cell, hexagon);
        } else if (this.gameActionHandler.getGameActionListener().getIsInsectCellClicked()) { //on clique sur une case vide pour déplacer une case sélectionnée
            this.gameActionHandler.getGameActionListener().handleInsectMoved(hexagon);
        } else if (this.gameActionHandler.getGameActionListener().getIsInsectButtonClicked()) { //on clique sur une case vide pour déposer une nouvelle case
            this.gameActionHandler.getGameActionListener().handleInsectPlaced(hexagon);
        }
        this.displayGame.repaint();
    }

    /**
     * Gère le dragging de la souris
     *
     * @param e MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // Bloque le dragging si la grille est vide
        if (this.gameActionHandler.getGrid().getGrid().isEmpty()) {
            return;
        }
        int x = e.getX();
        int y = e.getY();
        ViewMetrics.updateViewPosition(x - this.lastX, y - this.lastY);
        this.displayGame.repaint();
        this.lastX = x;
        this.lastY = y;
    }

    /**
     * Gère le zoom de la souris
     *
     * @param e MouseWheelEvent
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Bloque le zoom si la grille est vide
        if (this.gameActionHandler.getGrid().getGrid().isEmpty()) {
            return;
        }
        int notches = e.getWheelRotation();
        int oldHexWidth = HexMetrics.HEX_WIDTH;
        int oldHexHeight = HexMetrics.HEX_HEIGHT;

        // Calculate the mouse position in the game world before zooming
        int x = e.getX() - ViewMetrics.getViewOffsetX();
        int y = e.getY() - ViewMetrics.getViewOffsetY();

        if (notches < 0 && HexMetrics.HEX_WIDTH < Configuration.MAX_HEX_WIDTH) {
            // Molette tournée vers le haut
            HexMetrics.updateHexMetricsWidth(5);
        } else if (notches > 0 && HexMetrics.HEX_WIDTH > Configuration.MIN_HEX_WIDTH) {
            // Molette tournée vers le bas
            HexMetrics.updateHexMetricsWidth(-5);
        }

        // Calculate the mouse position in the game world after zooming
        int newMouseX = x * HexMetrics.HEX_WIDTH / oldHexWidth;
        int newMouseY = y * HexMetrics.HEX_HEIGHT / oldHexHeight;

        // Adjust the view position to keep the mouse at the same place in the game world
        ViewMetrics.updateViewPosition(x - newMouseX, y - newMouseY);

        this.displayGame.repaint();
    }
}