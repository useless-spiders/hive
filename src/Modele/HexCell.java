package Modele;

public class HexCell {
    private Integer type;

    public static final int TYPE_BEE = 0;
    public static final int TYPE_GRASSHOPPER = 1;
    public static final int TYPE_BEETLE = 2;
    public static final int TYPE_ANT = 3;
    public static final int TYPE_SPIDER = 4;

    public HexCell(Integer t) {
        this.type = t;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}