package Vue;

import java.awt.*;

public class HexMetrics {
    public static final int HEX_WIDTH = 100; // Définir la largeur de l'hexagone
    public static final int HEX_HEIGHT = (int) (Math.sqrt(3) / 2 * HEX_WIDTH); // Hauteur calculée pour maintenir la proportion
    public static final int ORIGIN_X = 300;
    public static final int ORIGIN_Y = 300;

    public static Point calculateHexCenter(int x, int y) {
        int coordX;
        int coordY;

        coordX = ORIGIN_X + y * HEX_WIDTH/2 + x * HEX_WIDTH;
        coordY = ORIGIN_Y + y * 3*HEX_HEIGHT/4;

        return new Point(coordX, coordY);
    }
}