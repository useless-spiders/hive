package Modele.Insect;

import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;

import Modele.HexGrid;

public class Bee extends Insect {

    private static final int MAX = 1;

    public Bee(Player player) {
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
                if (g.getAdj(x, y, directions[i]) == null && g.isHiveConnectedAfterMove(new HexCoordinate(x, y), new HexCoordinate(x + dx[i], y + dy[i]))) {
                    //on teste les trous
                    if((g.getAdj(x, y, directions[((((i-1)%directions.length)+directions.length)%directions.length)]) == null) && (g.getAdj(x, y, directions[((i+1)%directions.length)]) == null))
                    {
                        coordinates.add(new HexCoordinate(x + dx[i], y + dy[i]));
                    }
                }
            }
        }
        return coordinates;
    }

    public ArrayList<HexCoordinate> getPossibleInsertionCells(HexGrid g) { //A faire
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();

        return coordinates;
    }
}