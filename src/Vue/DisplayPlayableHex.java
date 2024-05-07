package Vue;

import Pattern.GameActionHandler;
import Structures.HexCoordinate;

import javax.swing.*;
import java.awt.*;

public class DisplayPlayableHex extends JComponent {
    private GameActionHandler controller;
    private Image locationImage;

    public DisplayPlayableHex(GameActionHandler controller) {
        this.controller = controller;
        this.locationImage = Display.loadImage("Location.png");
    }

    public void paintPlayableHex(Graphics g) {
        // Affiche les cases jouables
        for (HexCoordinate cell : controller.getPlayableCells()) {
            Point center = HexMetrics.calculateHexCenter(cell.getX(), cell.getY());
            g.drawImage(locationImage, center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
        }
    }
}
