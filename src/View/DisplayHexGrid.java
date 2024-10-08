package View;

import Model.HexCell;
import Model.Insect.Insect;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.RessourceLoader;

import javax.swing.*;
import java.awt.*;

public class DisplayHexGrid extends JComponent {
    private final GameActionHandler gameActionHandler;
    private final RessourceLoader ressourceLoader;
    private boolean isInsectHexClicked = false;
    private HexCoordinate hexClicked = null;

    /**
     * Constructeur de la classe DisplayHexGrid.
     *
     * @param gameActionHandler GameActionHandler
     */
    public DisplayHexGrid(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);
    }

    /**
     * Met à jour l'état de clic sur un hexagone contenant un insecte.
     *
     * @param isInsectHexClicked boolean
     * @param hexClicked         HexCoordinate
     */
    public void updateInsectClickState(boolean isInsectHexClicked, HexCoordinate hexClicked) {
        this.isInsectHexClicked = isInsectHexClicked;
        this.hexClicked = hexClicked;
    }

    /**
     * Dessine la grille hexagonale remplie d'insectes
     *
     * @param g Graphics
     */
    public void paintHexGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Trouver le nombre maximum d'insectes parmi toutes les cellules
        int maxInsectCount = 0;
        for (HexCell cell : this.gameActionHandler.getGrid().getGrid().values()) {
            int insectCount = cell.getInsects().size();
            if (insectCount > maxInsectCount) {
                maxInsectCount = insectCount;
            }
        }

        // Dessiner les insectes par étage, de l'étage 1 au maximum
        for (int i = 0; i < maxInsectCount; i++) {
            // Parcourir le contenu de la grille hexagonale
            for (HexCoordinate coord : this.gameActionHandler.getGrid().getGrid().keySet()) {
                HexCell cell = this.gameActionHandler.getGrid().getCell(coord);
                int insectIndex = i;
                if (insectIndex < cell.getInsects().size()) {
                    Insect insect = cell.getInsects().get(insectIndex);
                    Image insectImage = this.ressourceLoader.loadImageInsects(this.ressourceLoader.getImageInsectName(insect.getClass(), insect.getPlayer(), this.gameActionHandler.getPlayerController().getCurrentPlayer()));
                    Point center = HexMetrics.hexToPixel(coord);
                    int pileHeight = i * 8; // Hauteur de la pile d'insectes

                    int offsetX = center.x - HexMetrics.HEX_WIDTH / 2 + pileHeight;

                    if (this.isInsectHexClicked && this.hexClicked.equals(coord) && insectIndex == cell.getInsects().size() - 1) {
                        float opacity = 0.5f;
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                    } else {
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    }

                    g2d.drawImage(insectImage, offsetX, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);

                    // Remettre à 1 l'opacité, sinon l'affichage "dépilé" d'une pile peut être transparent
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }
            }
        }
    }
}
