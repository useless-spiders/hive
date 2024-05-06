package Modele.Insect;

import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;

import Modele.HexGrid;

public class Grasshopper extends Insect {

    private static final int MAX = 3;

    public Grasshopper(Player player) {
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
            String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
            int[] dx = {0, 1, 1, 0, -1, -1};
            int[] dy = {-1, -1, 0, 1, 1, 0};

            for (int i = 0; i < directions.length; i++) {
                int nx = x, ny = y;
                while (g.getAdj(nx, ny, directions[i]) != null) {
                    nx += dx[i];
                    ny += dy[i];
                }
                if ((nx != x || ny != y) && g.isHiveConnectedAfterMove(new HexCoordinate(x, y), new HexCoordinate(nx + dx[i], ny + dy[i]))) {
                    nx += dx[i];
                    ny += dy[i];
                    coordinates.add(new HexCoordinate(nx, ny));
                }
            }

        }
        return coordinates;
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleInsertionCells(HexGrid g) { // A faire
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        return coordinates;
    }

}