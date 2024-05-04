package Modele.Insect;

import Modele.HexGrid;
import Structures.HexCoordinate;
import java.util.ArrayList;

public abstract class Insect {
    public abstract int getMax();
    public abstract String getImageName();
    public abstract ArrayList<HexCoordinate> playableCells(int x, int y, HexGrid g);
}