package Vue;

import Modele.HexCell;
import Modele.HexGrid;
import Modele.Insect.Insect;
import Structures.HexCoordinate;

import javax.swing.*;
import java.awt.*;

public class DisplayHexGrid {
    private HexGrid hexGrid;

    public DisplayHexGrid(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
    }

    public void paintHexGrid(Graphics g) {
        // PROBLEME REPERE : L'AFFICHAGE SE FAIT 2 FOIS, test avec l'affichage de "OK"
        //System.out.println("OK");

        // Parcourir le contenu de la grille hexagonale
        for (HexCoordinate coord : hexGrid.getGrid().keySet()) {
            HexCell cell = hexGrid.getGrid().get(coord);
            for (Insect insect : cell.getInsects()) {
                Image insectImage = Display.loadImage(insect.getImageName());
                Point center = HexMetrics.calculateHexCenter(coord.getX(), coord.getY());
                g.drawImage(insectImage, center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
            }
        }
    }
}