package View;

import Model.Insect.Insect;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.RessourceLoader;

import java.awt.*;
import java.util.ArrayList;

public class DisplayStack {
    private boolean isInsectCellClicked = false;
    private HexCoordinate hexClicked = null;
    private static final int OFFSET = 5; // Marge entre chaque insecte
    private final GameActionHandler gameActionHandler;
    private final RessourceLoader ressourceLoader;

    /**
     * Constructeur de la classe DisplayStack.
     *
     * @param gameActionHandler GameActionHandler
     */
    public DisplayStack(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);
    }

    /**
     * Met à jour l'état de clic sur une cellule contenant des insectes.
     *
     * @param isInsectCellClicked boolean
     * @param hexClicked HexCoordinate
     */
    public void updateStackClickState(boolean isInsectCellClicked, HexCoordinate hexClicked) {
        this.isInsectCellClicked = isInsectCellClicked;
        this.hexClicked = hexClicked;
    }

    /**
     * Dessine la pile d'insectes.
     *
     * @param g Graphics
     */
    public void paintStack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (this.hexClicked != null && this.isInsectCellClicked) {
            ArrayList<Insect> depiledInsects = this.gameActionHandler.getGrid().getCell(this.hexClicked).getInsects();

            // Calculer la largeur et la hauteur du rectangle
            int rectWidth = HexMetrics.HEX_WIDTH + 20; // Largeur du rectangle (ajouter une marge de 10 pixels de chaque côté)
            int rectHeight = (HexMetrics.HEX_HEIGHT + OFFSET) * depiledInsects.size(); // Hauteur du rectangle

            // Calculer les coordonnées X et Y pour positionner le rectangle juste au-dessus de la pile en question
            Point hexCenter = HexMetrics.hexToPixel(this.hexClicked);
            int startX = hexCenter.x - rectWidth / 2; // Position X de départ (centrée sur la cellule)
            int startY = hexCenter.y - HexMetrics.HEX_HEIGHT - rectHeight; // Position Y de départ (au-dessus de la cellule)

            // Dessiner le rectangle blanc autour de l'affichage de la pile
            Color lightGray = Color.GRAY.brighter();  // Obtient une version plus claire de la couleur grise
            g2d.setColor(lightGray);
            g2d.fillRect(startX, startY, rectWidth, rectHeight);

            // Dessiner les insectes dépilés à l'intérieur du rectangle blanc
            for (int i = depiledInsects.size() - 1; i >= 0; i--) {
                Insect insect = depiledInsects.get(i);
                Image insectImage = this.ressourceLoader.loadImageInsects(this.ressourceLoader.getImageInsectName(insect.getClass(), insect.getPlayer(), this.gameActionHandler.getPlayerController().getCurrentPlayer()));
                int offsetY = startY + OFFSET + (HexMetrics.HEX_HEIGHT + OFFSET) * (depiledInsects.size() - i - 1); // Calculer la position Y en inversant l'ordre

                g2d.drawImage(insectImage, startX + 10, offsetY, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
            }
        }
    }
}
