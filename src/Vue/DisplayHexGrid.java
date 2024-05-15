package Vue;

import Modele.HexCell;
import Modele.HexGrid;
import Modele.Insect.Insect;
import Structures.HexCoordinate;
import Structures.HexMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayHexGrid extends JComponent {
    private HexGrid hexGrid;
    private boolean isInsectHexClicked = false;
    private HexCoordinate hexClicked = null;

    public DisplayHexGrid(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
    }

    public void updateInsectClickState(boolean isInsectHexClicked, HexCoordinate hexClicked) {
        this.isInsectHexClicked = isInsectHexClicked;
        this.hexClicked = hexClicked;
    }

    public void paintHexGrid(Graphics g) {
        // PROBLEME REPERE : L'AFFICHAGE SE FAIT 2 FOIS, test avec l'affichage de "OK"

        Graphics2D g2d = (Graphics2D) g;

        // Trouver le nombre maximum d'insectes parmi toutes les cellules
        int maxInsectCount = 0;
        for (HexCell cell : hexGrid.getGrid().values()) {
            int insectCount = cell.getInsects().size();
            if (insectCount > maxInsectCount) {
                maxInsectCount = insectCount;
            }
        }

        // Dessiner les insectes par étage, de l'étage 1 au maximum
        for (int i = 0; i < maxInsectCount; i++) {
            // Parcourir le contenu de la grille hexagonale
            for (HexCoordinate coord : hexGrid.getGrid().keySet()) {
                HexCell cell = hexGrid.getCell(coord);
                int insectIndex = i;
                if (insectIndex < cell.getInsects().size()) {
                    Insect insect = cell.getInsects().get(insectIndex);
                    Image insectImage = Display.loadImage(Display.getImageName(insect.getClass(), insect.getPlayer()));
                    Point center = HexMetrics.hexToPixel(coord);
                    int pileHeight = i * 8; // Hauteur de la pile d'insectes

                    int offsetX = center.x - HexMetrics.HEX_WIDTH / 2 + pileHeight;

                    if (isInsectHexClicked && hexClicked.equals(coord) && insectIndex == cell.getInsects().size() - 1) {
                        float opacity = 0.5f;
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                    } else {
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    }

                    g2d.drawImage(insectImage, offsetX, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);

                    //Remettre à 1 l'opacité, sinon l'affichage "dépilé" d'une pile peut être transparent
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }
            }
        }
    }
}