package View;

import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.RessourceLoader;

import javax.swing.*;
import java.awt.*;

public class DisplayPlayableHex extends JComponent {
    private final GameActionHandler gameActionHandler;
    private final RessourceLoader ressourceLoader;
    private final Image locationImage;
    private final Image otherLocationImage;
    private HexCoordinate hoverCoord;

    public DisplayPlayableHex(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);
        this.locationImage = this.ressourceLoader.loadImageHexagons("Full.png");
        this.otherLocationImage = this.ressourceLoader.loadImageHexagons("Border.png");
    }

    public void updateHoverCell(HexCoordinate hoverCoord) {
        this.hoverCoord = hoverCoord;
    }

    public void paintPlayableHex(Graphics g) {
        // Affiche les cases jouables
        for (HexCoordinate coord : this.gameActionHandler.getGameActionListener().getPlayableCoordinates()) {
            Point center = HexMetrics.hexToPixel(coord);

            if (coord.equals(this.hoverCoord)) {
                g.drawImage(this.locationImage, center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
            } else {
                g.drawImage(this.otherLocationImage, center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
            }
        }
    }
}
