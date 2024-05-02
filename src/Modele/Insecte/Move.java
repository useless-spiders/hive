package Modele.Insecte;

import Structures.HexCoordinate;
import java.util.ArrayList;
import Modele.HexGrid;

public interface Move {

    public ArrayList<HexCoordinate> jouable(int x, int y, HexGrid g);
}