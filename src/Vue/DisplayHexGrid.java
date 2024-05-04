package Vue;

import Modele.HexCell;
import Modele.HexGrid;

import javax.swing.*;
import java.awt.*;

public class DisplayHexGrid {
    Image imageAraigneeBlanche;
    private HexGrid hexGrid;

    public DisplayHexGrid(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
        this.imageAraigneeBlanche = Display.loadImage("res/Images/Araignee_blanche.png");
    }

    public void paintHexGrid(Graphics g) {
        // PROBLEME REPERE : L'AFFICHAGE SE FAIT 2 FOIS, test avec l'affichage de "OK"
        //System.out.println("OK");

        // Parcourir le contenu de la grille hexagonale
        hexGrid.getGrid().forEach((coord, cell) -> {
            Point center = HexMetrics.calculateHexCenter(coord.getX(), coord.getY());
            g.drawImage(imageAraigneeBlanche, center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
        });
    }
}