package Structures;

import java.awt.*;

public class HexMetrics {
    public static final int HEX_WIDTH = 100; // Largeur de l'hexagone
    public static final int HEX_HEIGHT = (int) (Math.sqrt(3) / 2 * HEX_WIDTH); // Hauteur pour maintenir la proportion

    public static int viewOffsetX;
    public static int viewOffsetY;

    public static void updateViewPosition(int dx, int dy) {
        viewOffsetX += dx;
        viewOffsetY += dy;
    }

    public static Point hexToPixel(HexCoordinate hex) {
        int coordX = hex.getY() * HEX_WIDTH / 2 + hex.getX() * HEX_WIDTH;
        int coordY = hex.getY() * 3 * HEX_HEIGHT / 4;
        return new Point(coordX, coordY);
    }

    public static int getViewOffsetX() {
        return viewOffsetX;
    }

    public static int getViewOffsetY() {
        return viewOffsetY;
    }

    public static HexCoordinate pixelToHex(int mouseX, int mouseY) {
        // Ajuster les coordonnées de la souris avec les offsets de vue
        double adjustedMouseX = mouseX;
        double adjustedMouseY = mouseY;

        // Conversion des coordonnées pixels aux coordonnées hexagonales
        int y = (int) Math.round((adjustedMouseY * 4.0) / (3 * HEX_HEIGHT));
        int x = (int) Math.round((adjustedMouseX - y * HEX_WIDTH / 2.0) / HEX_WIDTH);

        return new HexCoordinate(x, y);
    }
}
