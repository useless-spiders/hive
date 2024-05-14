package Modele.Insect;

import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;

import Modele.HexGrid;

public class Beetle extends Insect {

    public static int MAX = 2;

    public Beetle(Player player) {
        super(player);
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCells(int x, int y, HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        if (canMoveInsect(g, this.getPlayer())) {
            String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
            int[] dx = {0, 1, 1, 0, -1, -1};
            int[] dy = {-1, -1, 0, 1, 1, 0};

            for (int i = 0; i < directions.length; i++) {
                if (g.isHiveConnectedAfterMove(new HexCoordinate(x, y), new HexCoordinate(x + dx[i], y + dy[i]))) {
                    String dir = directions[((((i - 1) % directions.length) + directions.length) % directions.length)];
                    if (((g.getAdj(x, y, dir) != null) || (g.getAdj(x, y, directions[((i + 1) % directions.length)]) != null))) {
                        coordinates.add(new HexCoordinate(x + dx[i], y + dy[i]));
                    }
                    if((g.getAdj(x, y, directions[i]) != null)){
                        coordinates.add(new HexCoordinate(x + dx[i], y + dy[i]));
                    }
                }
            }
        }
        return coordinates;
    }
}