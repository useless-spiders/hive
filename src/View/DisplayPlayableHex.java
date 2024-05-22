package View;

import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayPlayableHex extends JComponent {
    private GameActionHandler controller;
    private Image locationImage;
    private Image otherLocationImage;
    private HexCoordinate hoverCoord;

    public DisplayPlayableHex(GameActionHandler controller) {
        this.controller = controller;
        this.locationImage = MainDisplay.loadImageHexagons("Full.png");
        this.otherLocationImage = MainDisplay.loadImageHexagons("Border.png");
    }

    public void updateHoverCell(HexCoordinate hoverCoord) {
        this.hoverCoord = hoverCoord;
    }

    public void paintPlayableHex(Graphics g) {
        // Affiche les cases jouables
        for (HexCoordinate coord : this.controller.getPlayableCoordinates()) {
            Point center = HexMetrics.hexToPixel(coord);

            if (coord.equals(this.hoverCoord)) {
                g.drawImage(this.locationImage, center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
            } else {
                g.drawImage(this.otherLocationImage, center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
            }
        }
    }
}
