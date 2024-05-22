package Structure;

public class ViewMetrics {
    public static int viewOffsetX;
    public static int viewOffsetY;

    public static void updateViewPosition(int dx, int dy) {
        viewOffsetX += dx;
        viewOffsetY += dy;
    }

    public static int getViewOffsetX() {
        return viewOffsetX;
    }

    public static int getViewOffsetY() {
        return viewOffsetY;
    }

    public static void resetViewPosition() {
        viewOffsetX = 0;
        viewOffsetY = 0;
    }
}
