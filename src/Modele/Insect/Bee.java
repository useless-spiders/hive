package Modele.Insect;

import Modele.HexCell;
import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;
import java.util.Map;

import Modele.HexGrid;

public class Bee extends Insect {

    public Bee(Player player) {
        super(player);
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCells(HexCoordinate current, HexGrid g) {
        int x = current.getX();
        int y = current.getY();
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        if (this.canMoveInsect(g, this.getPlayer())) {
            String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
            int[] dx = {0, 1, 1, 0, -1, -1};
            int[] dy = {-1, -1, 0, 1, 1, 0};

            for (int i = 0; i < directions.length; i++) {
                if (g.getCell(g.getNeighbor(current, directions[i])) == null && g.isHiveConnectedAfterMove(current, new HexCoordinate(x + dx[i], y + dy[i]))) {
                    //on teste les trous
                    String dir = directions[((((i - 1) % directions.length) + directions.length) % directions.length)];
                    if (((g.getCell(g.getNeighbor(current, dir)) == null) && (g.getCell(g.getNeighbor(current, directions[((i + 1) % directions.length)])) != null)) || ((g.getCell(g.getNeighbor(current, dir)) != null) && (g.getCell(g.getNeighbor(current, directions[((i + 1) % directions.length)])) == null))) {
                        coordinates.add(new HexCoordinate(x + dx[i], y + dy[i]));
                    }
                }
            }
        }
        return coordinates;
    }
}