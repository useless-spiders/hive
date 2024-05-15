package Modele;

import Modele.Insect.Insect;
import Structures.HexCoordinate;

public class Move {
    private Insect insect;
    private HexCoordinate previousCoor;
    private HexCoordinate newCoor;

    public Move(Insect insect, HexCoordinate previousCoor, HexCoordinate newCoor) {
        this.insect = insect;
        this.previousCoor = previousCoor;
        this.newCoor = newCoor;
    }

    public Insect getInsect() {
        return this.insect;
    }

    // if null, then the insect is being placed
    public HexCoordinate getPreviousCoor() {
        return this.previousCoor;
    }

    public HexCoordinate getNewCoor() {
        return this.newCoor;
    }
}