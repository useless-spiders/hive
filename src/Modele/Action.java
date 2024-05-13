package Modele;

import Modele.Insect.Insect;
import Structures.HexCoordinate;

public class Action {
    private Insect insect;
    private HexCoordinate previousCoor;
    private HexCoordinate newCoor;

    public Action(Insect insect, HexCoordinate previousCoor, HexCoordinate newCoor) {
        this.insect = insect;
        this.previousCoor = previousCoor;
        this.newCoor = newCoor;
    }

    public Insect getInsect() {
        return insect;
    }

    // if null, then the insect is being placed
    public HexCoordinate getPreviousCoor() {
        return previousCoor;
    }

    public HexCoordinate getNewCoor() {
        return newCoor;
    }
}