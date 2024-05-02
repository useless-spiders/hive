package Modele;

import Modele.Insect.Insect;

public class HexCell {
    private Insect insect;

    public HexCell(Insect insect) {
        this.insect = insect;
    }

    public Insect getType() {
        return this.insect;
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
        return this.insect == other.insect;
    }

}