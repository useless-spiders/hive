package Structure;

import Global.Configuration;

import java.awt.*;

/**
 * Classe pour les métriques des hexagones
 */
public class HexMetrics {
    public static int HEX_WIDTH = Configuration.HEX_DEFAULT_WIDTH; // Largeur de l'hexagone
    public static int HEX_HEIGHT = (int) (Math.sqrt(3) / 2 * HEX_WIDTH); // Hauteur pour maintenir la proportion

    /**
     * Renvoie les coordonnées du centre de l'hexagone
     * @param hex Coordonnées hexagonales
     * @return Point
     */
    public static Point hexToPixel(HexCoordinate hex) {
        int coordX = hex.getY() * HEX_WIDTH / 2 + hex.getX() * HEX_WIDTH;
        int coordY = hex.getY() * 3 * HEX_HEIGHT / 4;
        return new Point(coordX, coordY);
    }

    /**
     * Renvoie les coordonnées du centre de l'hexagone
     * @param mouseX Coordonnée x
     * @param mouseY Coordonnée y
     * @return Point
     */
    public static HexCoordinate pixelToHex(int mouseX, int mouseY) {
        // Conversion des coordonnées pixels aux coordonnées hexagonales
        int y = (int) Math.round((mouseY * 4.0) / (3 * HEX_HEIGHT));
        int x = (int) Math.round((mouseX - y * HEX_WIDTH / 2.0) / HEX_WIDTH);

        return new HexCoordinate(x, y);
    }

    /**
     * Renvoie les coordonnées du centre de la fenêtre
     * @param frameSizeX Taille de la fenêtre en x
     * @param frameSizeY Taille de la fenêtre en y
     * @return HexCoordinate
     */
    public static HexCoordinate hexCenterCoordinate(int frameSizeX, int frameSizeY) {
        return pixelToHex(frameSizeX/2, frameSizeY/2);
    }

    /**
     * Met à jour la taille de l'hexagone
     */
    public static void updateHexMetricsWidth(int width) {
        HexMetrics.HEX_WIDTH += width;
        HexMetrics.HEX_HEIGHT = (int) (Math.sqrt(3) / 2 * HEX_WIDTH);
    }

    /**
     * Reset la taille de l'hexagone
     */
    public static void resetHexMetricsWidth() {
        HexMetrics.HEX_WIDTH = 100;
        HexMetrics.HEX_HEIGHT = (int) (Math.sqrt(3) / 2 * HEX_WIDTH);
    }
}
