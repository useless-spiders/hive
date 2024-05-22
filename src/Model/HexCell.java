package Model;

import Model.Insect.Insect;

import java.util.ArrayList;
import java.util.Objects;

public class HexCell implements Cloneable {
    private ArrayList<Insect> insects;

    public HexCell() {
        this.insects = new ArrayList<>();
    }

    public void addInsect(Insect insect) {
        this.insects.add(insect);
    }

    public void removeTopInsect() {
        this.insects.remove(this.insects.size() - 1);
    }

    public ArrayList<Insect> getInsects() {
        return this.insects;
    }

    public Insect getTopInsect() {
        return this.insects.get(this.insects.size() - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HexCell other = (HexCell) obj;
        if (this.insects.size() != other.insects.size()) {
            return false;
        }
        for (int i = 0; i < this.insects.size(); i++) {
            if (!this.insects.get(i).equals(other.insects.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.insects);
    }

    @Override
    public HexCell clone() {
        try {
            HexCell clone = (HexCell) super.clone();
            clone.insects = new ArrayList<>();
            for (Insect insect : this.insects) {
                clone.insects.add(insect.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Ne devrait jamais se produire
        }
    }
}