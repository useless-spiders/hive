package Structure;

import java.io.Serializable;
import java.util.Objects;

public class HexCoordinate implements Cloneable, Serializable {
    private int x;
    private int y;

    public HexCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public HexCoordinate clone() {
        try {
            return (HexCoordinate) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Ne devrait jamais se produire
        }
    }
}
