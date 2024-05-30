package Model;

import Model.Insect.Insect;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe pour une cellule de la grille
 */
public class HexCell implements Cloneable {
    private ArrayList<Insect> insects;

    /**
     * Constructeur
     */
    public HexCell() {
        this.insects = new ArrayList<>();
    }

    /**
     * Ajoute un insecte
     *
     * @param insect Insecte
     */
    public void addInsect(Insect insect) {
        this.insects.add(insect);
    }

    /**
     * Supprime le haut de la pile
     */
    public void removeTopInsect() {
        this.insects.remove(this.insects.size() - 1);
    }

    /**
     * Renvoie la liste des insectes
     *
     * @return ArrayList<Insect>
     */
    public ArrayList<Insect> getInsects() {
        return this.insects;
    }

    /**
     * Renvoie l'insecte du haut
     *
     * @return Insect
     */
    public Insect getTopInsect() {
        return this.insects.get(this.insects.size() - 1);
    }

    /**
     * Test si deux cellules sont égales
     *
     * @param obj Object
     * @return boolean
     */
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

    /**
     * Renvoie le hashcode de la cellule
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.insects);
    }

    /**
     * Clone la cellule
     *
     * @return HexCell
     */
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