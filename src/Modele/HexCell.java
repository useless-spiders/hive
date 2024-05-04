package Modele;

import Modele.Insect.Insect;

import java.util.ArrayList;

public class HexCell {
    private ArrayList<Insect> insects;

    public HexCell() {
        this.insects = new ArrayList<>();
    }

    public void addInsect(Insect insect) {
        if(countInsectsOfType(insect.getClass()) >= insect.getMax()) {
            throw new IllegalArgumentException("Cannot add more than " + insect.getMax() + " insects of type " + insect.getClass().getSimpleName());
        }
        this.insects.add(insect);
    }

    public void removeInsect(Insect insect) {
        this.insects.remove(insect);
    }

    public ArrayList<Insect> getInsects() {
        return this.insects;
    }

    public Insect getTopInsect() {
        return this.insects.get(this.insects.size() - 1);
    }

    private int countInsectsOfType(Class<? extends Insect> insectType) {
        int count = 0;
        for (Insect insect : this.insects) {
            if (insectType.isInstance(insect)) {
                count++;
            }
        }
        return count;
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