package Modele.Insect;

import Modele.HexGrid;
import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

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
    public ArrayList<HexCoordinate> getPossibleMovesCells(int x, int y, HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        HashSet<HexCoordinate> visited = new HashSet<>();
        if (canMoveInsect(g, this.getPlayer())) {
            getPossibleMovesCellsHelper(x, y, g, 3, coordinates, new HexCoordinate(x, y), visited);
        }
        return coordinates;
    }

    private void getPossibleMovesCellsHelper(int x, int y, HexGrid g, int steps, ArrayList<HexCoordinate> coordinates, HexCoordinate original, HashSet<HexCoordinate> visited) {
        HexCoordinate current = new HexCoordinate(x, y);
        if (steps == 0 && !current.equals(original)) {
            coordinates.add(current);
            return;
        }

        if (steps > 0) {
            String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
            int[] dx = {0, 1, 1, 0, -1, -1};
            int[] dy = {-1, -1, 0, 1, 1, 0};

            for (int i = 0; i < directions.length; i++) {
                HexCoordinate next = new HexCoordinate(x + dx[i], y + dy[i]);
                if (!visited.contains(next) && g.getAdj(x, y, directions[i]) == null && g.isHiveConnectedAfterMove(original, next)) {
                    visited.add(next);
                    getPossibleMovesCellsHelper(next.getX(), next.getY(), g, steps - 1, coordinates, original, visited);
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