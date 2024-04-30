package Vue;

import Modele.HexGrid;

import javax.swing.*;
import java.awt.*;

public class DisplayHexGrid extends JComponent {
    Image imageAraignerBlanche = Display.charge("res/Images/Araigner_blanche.png");
    private HexGrid hexGrid;

    public DisplayHexGrid(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
    }

    public void paintHexGrid(Graphics g) {
        //g.drawImage(imageAraignerBlanche, 250, 250, 50, 50, null);
        super.paintComponent(g);

        // Coordonnées de la cellule centrale
        int centerX = 300;
        int centerY = 300;

        // Tracer chaque cellule hexagonale avec l'image
        for (int y = -5; y <= 5; y++) {
            for (int x = -5; x <= 5; x++) {
                // Calculer les coordonnées de la cellule actuelle
                int[] coords = getHexCellCoordinates(centerX, centerY, x, y, 100);

                // Dessiner l'image dans la cellule
                g.drawImage(imageAraignerBlanche, coords[0], coords[1], null);
            }
        }
    }

    // Calculer les coordonnées d'une cellule hexagonale
    private int[] getHexCellCoordinates(int centerX, int centerY, int x, int y, int cellSize) {
        double hexHeight = Math.sqrt(3) * cellSize;
        double hexWidth = 2 * cellSize;

        int coordX = centerX + (int) (x * hexWidth * 0.75);
        int coordY = centerY + (int) (y * hexHeight - x * hexHeight * 0.5);

        return new int[] {coordX, coordY};
    }
}
