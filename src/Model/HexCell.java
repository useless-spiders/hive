package Model;

import Model.Insect.Insect;

import java.io.Serializable;
import java.util.ArrayList;

public class HexCell implements Serializable {
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
}