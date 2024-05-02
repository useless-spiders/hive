package Modele;

public class HexCell {
    private int type;

    public static final int TYPE_BEE = 0;
    public static final int TYPE_GRASSHOPPER = 1;
    public static final int TYPE_BEETLE = 2;
    public static final int TYPE_ANT = 3;
    public static final int TYPE_SPIDER = 4;
    public static final int TYPE_EMPTY = 5;


    public HexCell(int t) {
        this.type = t;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}