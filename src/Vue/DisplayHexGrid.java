package Vue;

import Modele.HexCell;
import Modele.HexGrid;
import Modele.Insect.Insect;
import Structures.HexCoordinate;

import javax.swing.*;
import java.awt.*;

public class DisplayHexGrid extends JComponent {
    private HexGrid hexGrid;
    private boolean isInsectCellClicked = false;
    private HexCoordinate hexClicked = null;

    public DisplayHexGrid(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
    }

    public void updateInsectClickState(boolean isInsectCellClicked, HexCoordinate hexClicked) {
        this.isInsectCellClicked = isInsectCellClicked;
        this.hexClicked = hexClicked;
    }

    public void paintHexGrid(Graphics g) {
        // PROBLEME REPERE : L'AFFICHAGE SE FAIT 2 FOIS, test avec l'affichage de "OK"

        Graphics2D g2d = (Graphics2D) g;

        // Parcourir le contenu de la grille hexagonale
        for (HexCoordinate coord : hexGrid.getGrid().keySet()) {
            HexCell cell = hexGrid.getGrid().get(coord);
            for (Insect insect : cell.getInsects()) {
                Image insectImage = Display.loadImage(insect.getImageName());
                Point center = HexMetrics.calculateHexCenter(coord.getX(), coord.getY());

                if (isInsectCellClicked && hexClicked.equals(coord)) {
                    float opacity = 0.5f;
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                } else {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }

                g2d.drawImage(insectImage, center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
            }
        }
    }

}