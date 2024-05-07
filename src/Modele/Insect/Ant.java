package Modele.Insect;

import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;
import java.util.HashSet;

import Modele.HexGrid;

public class Ant extends Insect {

    private static final int MAX = 3;

    public Ant(Player player) {
        super(player);
    }

    @Override
    public int getMax() {
        return MAX;
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCells(int x, int y, HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        HashSet<HexCoordinate> visited = new HashSet<>();
        if (canMoveInsect(g, this.getPlayer())) {
            //visited.add(new HexCoordinate(x, y));
            getPossibleMovesCellsHelper(x, y, g, coordinates, new HexCoordinate(x, y), visited);
        }
        return coordinates;
    }

    private void getPossibleMovesCellsHelper(int x, int y, HexGrid g, ArrayList<HexCoordinate> coordinates, HexCoordinate original, HashSet<HexCoordinate> visited) {
        HexCoordinate current = new HexCoordinate(x, y);
        if (!current.equals(original)) {
            coordinates.add(current);
        }

        String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
        int[] dx = {0, 1, 1, 0, -1, -1};
        int[] dy = {-1, -1, 0, 1, 1, 0};

        for (int i = 0; i < directions.length; i++) {
            HexCoordinate next = new HexCoordinate(x + dx[i], y + dy[i]);
            if (!visited.contains(next) && g.getAdj(x, y, directions[i]) == null && g.isHiveConnectedAfterMove(original, next)) 
            {
                //on teste les trous
                if((g.getAdj(x, y, directions[((((i-1)%directions.length)+directions.length)%directions.length)]) == null) || (g.getAdj(x, y, directions[((i+1)%directions.length)]) == null))
                {
                    visited.add(next);
                    getPossibleMovesCellsHelper(next.getX(), next.getY(), g, coordinates, original, visited);
                }
            }
        }
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleInsertionCells(HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();

        return coordinates;
    }
}