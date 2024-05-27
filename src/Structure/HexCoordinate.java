package Structure;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe pour les coordonnées hexagonales
 */
public class HexCoordinate implements Cloneable, Serializable {
    private int x;
    private int y;

    /**
     * Constructeur
     * @param x Coordonnée x
     * @param y Coordonnée y
     */
    public HexCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Renvoie la coordonnée x
     * @return int
     */
    public int getX() {
        return this.x;
    }

    /**
     * Renvoie la coordonnée y
     * @return int
     */
    public int getY() {
        return this.y;
    }

    /**
     * Test si les coordonnées sont égales
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        HexCoordinate other = (HexCoordinate) obj;
        return this.x == other.x && this.y == other.y;
    }

    /**
     * Renvoie le hashcode
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    /**
     * Clone l'objet
     * @return HexCoordinate
     */
    @Override
    public HexCoordinate clone() {
        try {
            return (HexCoordinate) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Ne devrait jamais se produire
        }
    }
}
