package Vue;

import Modele.HexGrid;
import Pattern.GameActionHandler;
import Structures.HexCoordinate;

import javax.swing.*;
import java.awt.*;

public class DisplayPlayableHex extends JComponent {

    public DisplayPlayableHex() {
    }

    public void paintPlayableHex(Graphics g, GameActionHandler controller) {
        // Affiche les cases jouables
        for (HexCoordinate cell : controller.getPlayableCells()) {
            Point center = HexMetrics.calculateHexCenter(cell.getX(), cell.getY());
            g.drawImage(Display.loadImage("Location.png"), center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
        }
    }
}
