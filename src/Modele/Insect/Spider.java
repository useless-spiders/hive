package Modele.Insect;

import Modele.HexGrid;
import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;
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
        if (canMoveInsect(g, this.getPlayer())) {
            getPossibleMovesCellsHelper(x, y, g, 3, coordinates);
        }
        return coordinates;
    }

    private void getPossibleMovesCellsHelper(int x, int y, HexGrid g, int steps, ArrayList<HexCoordinate> coordinates) {
        if (steps == 0) {
            return;
        }

        String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
        int[] dx = {0, 1, 1, 0, -1, -1};
        int[] dy = {-1, -1, 0, 1, 1, 0};

        for (int i = 0; i < directions.length; i++) {
            if (g.getAdj(x, y, directions[i]) == null) {
                if (steps == 1) {
                    coordinates.add(new HexCoordinate(x + dx[i], y + dy[i]));
                }
                getPossibleMovesCellsHelper(x + dx[i], y + dy[i], g, steps - 1, coordinates);
            }
        }
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleInsertionCells(HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();

        return coordinates;
    }
}