package Structure;

import java.awt.*;

public class HexMetrics {
    public static int HEX_WIDTH = 100; // Largeur de l'hexagone
    public static int HEX_HEIGHT = (int) (Math.sqrt(3) / 2 * HEX_WIDTH); // Hauteur pour maintenir la proportion

    public static final int MAX_HEX_WIDTH = 200;
    public static final int MIN_HEX_WIDTH = 50;

    public static Point hexToPixel(HexCoordinate hex) {
        int coordX = hex.getY() * HEX_WIDTH / 2 + hex.getX() * HEX_WIDTH;
        int coordY = hex.getY() * 3 * HEX_HEIGHT / 4;
        return new Point(coordX, coordY);
    }

    public static HexCoordinate pixelToHex(int mouseX, int mouseY) {
        // Conversion des coordonnées pixels aux coordonnées hexagonales
        int y = (int) Math.round((mouseY * 4.0) / (3 * HEX_HEIGHT));
        int x = (int) Math.round((mouseX - y * HEX_WIDTH / 2.0) / HEX_WIDTH);

        return new HexCoordinate(x, y);
    }

    public static HexCoordinate hexCenterCoordinate(int frameSizeX, int frameSizeY) {
        return pixelToHex(frameSizeX/2, frameSizeY/2);
    }

    public static void updateHexMetricsWidth(int width) {
        HexMetrics.HEX_WIDTH += width;
        HexMetrics.HEX_HEIGHT = (int) (Math.sqrt(3) / 2 * HEX_WIDTH);
    }
}
