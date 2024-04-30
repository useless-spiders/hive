package Modele;

public class HexCell {
    private int type;

    public static final int TYPE_BEE = 0;
    public static final int TYPE_GRASSHOPPER = 1;
    public static final int TYPE_BEETLE = 2;
    public static final int TYPE_ANT = 3;
    public static final int TYPE_SPIDER = 4;

    public HexCell(int t) {
        this.type = t;
    }

    public int getType() {
        return type;
    }

}