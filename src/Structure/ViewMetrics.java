package Structure;

/**
 * Classe pour les métriques de la vue
 */
public class ViewMetrics {
    public static int viewOffsetX;
    public static int viewOffsetY;

    /**
     * Met à jour la position de la vue
     *
     * @param dx Déplacement en x
     * @param dy Déplacement en y
     */
    public static void updateViewPosition(int dx, int dy) {
        viewOffsetX += dx;
        viewOffsetY += dy;
    }

    /**
     * Renvoie le décalage en x
     *
     * @return int
     */
    public static int getViewOffsetX() {
        return viewOffsetX;
    }

    /**
     * Renvoie le décalage en y
     *
     * @return int
     */
    public static int getViewOffsetY() {
        return viewOffsetY;
    }

    /**
     * Reset la position de la vue
     */
    public static void resetViewPosition() {
        viewOffsetX = 0;
        viewOffsetY = 0;
    }
}
