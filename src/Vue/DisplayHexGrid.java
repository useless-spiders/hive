package Vue;

import Modele.HexGrid;

import javax.swing.*;
import java.awt.*;

public class DisplayHexGrid extends JComponent {
    Image imageAraigneeBlanche;
    private HexGrid hexGrid;
    private int hexWidth = 100; // Définir la largeur de l'hexagone
    private int hexHeight = (int) (Math.sqrt(3) / 2 * hexWidth); // Hauteur calculée pour maintenir la proportion


    public DisplayHexGrid(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
        this.imageAraigneeBlanche = Display.charge("res/Images/Araignee_blanche.png");
    }

    protected void paintHexGrid(Graphics g) {
        // PROBLEME REPERE : L'AFFICHAGE SE FAIT 2 FOIS, test avec l'affichage de "OK"
        //System.out.println("OK");
        super.paintComponent(g);

        // Parcourir le contenu de la grille hexagonale
        hexGrid.getGrid().forEach((coord, cell) -> {
            Point center = calculateHexCenter(coord.getX(), coord.getY());
            g.drawImage(imageAraigneeBlanche, center.x - hexWidth / 2, center.y - hexHeight / 2, hexWidth, hexHeight, this);
        });
    }

    private Point calculateHexCenter(int x, int y) {
        // Point de départ ou origine de la grille
        int originX = 300;
        int originY = 300;

        int coordX;
        int coordY;

        if (y % 2 == 0) {
            coordX = originX + x * hexWidth;
        } else {
            coordX = originX + x * hexWidth + hexWidth/2;
        }

        coordY = originY + y * 3*hexHeight/4;

        return new Point(coordX, coordY);
    }
}
