package View;

import Model.Insect.Insect;
import Model.HexGrid;
import Structure.HexCoordinate;
import Structure.HexMetrics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DisplayStack {
    private HexGrid hexGrid;
    private boolean isInsectCellClicked = false;
    private HexCoordinate hexClicked = null;

    public DisplayStack(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
    }

    public void updateStackClickState(boolean isInsectCellClicked, HexCoordinate hexClicked) {
        this.isInsectCellClicked = isInsectCellClicked;
        this.hexClicked = hexClicked;
    }

    public void paintStack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (this.hexClicked != null && this.isInsectCellClicked) {
            List<Insect> depiledInsects = new ArrayList<>(hexGrid.getCell(hexClicked).getInsects());
            int padding = 5; // Marge entre chaque insecte
            int insectWidth = HexMetrics.HEX_WIDTH;
            int insectHeight = HexMetrics.HEX_HEIGHT;

            // Calculer la largeur et la hauteur du rectangle
            int rectWidth = insectWidth + 20; // Largeur du rectangle (ajouter une marge de 10 pixels de chaque côté)
            int rectHeight = (insectHeight + padding) * depiledInsects.size(); // Hauteur du rectangle

            // Calculer les coordonnées X et Y pour positionner le rectangle juste au-dessus de la pile en question
            Point hexCenter = HexMetrics.hexToPixel(hexClicked);
            int startX = hexCenter.x - rectWidth / 2; // Position X de départ (centrée sur la cellule)
            int startY = hexCenter.y - HexMetrics.HEX_HEIGHT - rectHeight; // Position Y de départ (au-dessus de la cellule)

            // Dessiner le rectangle blanc autour de l'affichage de la pile
            g2d.setColor(Color.WHITE);
            g2d.fillRect(startX, startY, rectWidth, rectHeight);

            // Dessiner les insectes dépilés à l'intérieur du rectangle blanc
            for (int i = depiledInsects.size() - 1; i >= 0; i--) {
                Insect insect = depiledInsects.get(i);
                Image insectImage = DisplayGame.loadImage(DisplayGame.getImageName(insect.getClass(), insect.getPlayer()));
                int offsetY = startY + padding + (insectHeight + padding) * (depiledInsects.size() - i - 1); // Calculer la position Y en inversant l'ordre

                g2d.drawImage(insectImage, startX + 10, offsetY, insectWidth, insectHeight, null);
            }
        }
    }
}
