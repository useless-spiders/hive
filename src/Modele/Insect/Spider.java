package Modele.Insect;

import Modele.HexGrid;
import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;

public class Spider extends Insect {

    private static final int MAX = 2;

    public Spider(Player player) {
        super(player);
    }

    @Override
    public int getMax() {
        return MAX;
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCells(int x, int y, HexGrid g) { //A faire
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        if (canMoveInsect(g, this.getPlayer())) {
            if (g.getAdj(x, y, "NO") == null) coordinates.add(new HexCoordinate(x, y - 1));
            if (g.getAdj(x, y, "NE") == null) coordinates.add(new HexCoordinate(x + 1, y - 1));
            if (g.getAdj(x, y, "E") == null) coordinates.add(new HexCoordinate(x + 1, y));
            if (g.getAdj(x, y, "SE") == null) coordinates.add(new HexCoordinate(x, y + 1));
            if (g.getAdj(x, y, "SO") == null) coordinates.add(new HexCoordinate(x - 1, y + 1));
            if (g.getAdj(x, y, "O") == null) coordinates.add(new HexCoordinate(x - 1, y));
        }
        return coordinates;
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleInsertionCells(HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();

        return coordinates;
    }
}