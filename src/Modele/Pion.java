package Modele;

public class Pion {
    private int type;

    //constantes des pièces
    public static final int ABEILLE = 0;
    public static final int SAUTERELLE = 1;
    public static final int SCARABEE = 2;
    public static final int FOURMI = 3;
    public static final int ARAIGNEE = 4;

    public Pion(int t) {
        this.type = t;
    }

    public int getIndice() {
        return indice;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Pion{" +
                "indice=" + indice +
                ", type=" + type +
                ", adjacents=" + getAdjacents() +
                '}';
    }
}
