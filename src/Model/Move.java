package Model;

import Model.Insect.Insect;
import Structure.HexCoordinate;

import java.io.Serializable;

/**
 * Classe pour les mouvements
 */
public class Move implements Serializable {
    private Insect insect;
    private HexCoordinate previousCoor;
    private HexCoordinate newCoor;

    /**
     * Constructeur
     * @param insect Insecte
     * @param previousCoor Ancienne coordonnée
     * @param newCoor Nouvelle coordonnée
     */
    public Move(Insect insect, HexCoordinate previousCoor, HexCoordinate newCoor) {
        this.insect = insect;
        this.previousCoor = previousCoor;
        this.newCoor = newCoor;
    }

    /**
     * Renvoie l'insecte
     * @return Insect
     */
    public Insect getInsect() {
        return this.insect;
    }

    /**
     * Renvoie l'ancienne coordonnée, si null, alors l'insecte est en train d'être placé
     * @return HexCoordinate
     */
    public HexCoordinate getPreviousCoor() {
        return this.previousCoor;
    }

    /**
     * Renvoie la nouvelle coordonnée
     * @return HexCoordinate
     */
    public HexCoordinate getNewCoor() {
        return this.newCoor;
    }
}