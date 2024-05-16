package Vue;

import Pattern.GameActionHandler;
import Structures.HexCoordinate;
import Structures.HexMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayPlayableHex extends JComponent {
    private GameActionHandler controller;
    private Image locationImage;
    private Image otherLocationImage;
    private HexCoordinate hoverCoord;

    public DisplayPlayableHex(GameActionHandler controller) {
        this.controller = controller;
        this.locationImage = DisplayGame.loadImage("Location.png");
        this.otherLocationImage = DisplayGame.loadImage("Other_location.png");
    }

    public void updateHoverCell(HexCoordinate hoverCoord) {
        this.hoverCoord = hoverCoord;
    }

    public void paintPlayableHex(Graphics g) {
        // Affiche les cases jouables
        for (HexCoordinate coord : controller.getPlayableCoordinates()) {
            Point center = HexMetrics.hexToPixel(coord);

            if (coord.equals(hoverCoord)) {
                g.drawImage(locationImage, center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
            } else {
                g.drawImage(otherLocationImage, center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
            }
        }
    }
}
