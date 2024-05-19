package Model;

import Model.Insect.Insect;
import Structure.HexCoordinate;

import java.io.Serializable;

public class Move implements Serializable {
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