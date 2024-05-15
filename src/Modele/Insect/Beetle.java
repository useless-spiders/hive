package Modele.Insect;

import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;
import java.util.Map;

import Modele.HexGrid;

public class Beetle extends Insect {

    public Beetle(Player player) {
        super(player);
    }

    /*@Override
    public ArrayList<HexCoordinate> getPossibleMovesCells(HexCoordinate current, HexGrid g) {
        int x = current.getX();
        int y = current.getY();
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        if (this.canMoveInsect(g, this.getPlayer())) {
            String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
            int[] dx = {0, 1, 1, 0, -1, -1};
            int[] dy = {-1, -1, 0, 1, 1, 0};

            for (int i = 0; i < directions.length; i++) {
                if (g.isHiveConnectedAfterMove(current, new HexCoordinate(x + dx[i], y + dy[i]))) {
                    String dir = directions[((((i - 1) % directions.length) + directions.length) % directions.length)];
                    if (((g.getCell(g.getNeighbor(current, dir)) != null) || (g.getCell(g.getNeighbor(current, directions[((i + 1) % directions.length)])) != null))) {
                        coordinates.add(new HexCoordinate(x + dx[i], y + dy[i]));
                    }
                    if((g.getCell(g.getNeighbor(current, directions[i])) != null)){
                        coordinates.add(new HexCoordinate(x + dx[i], y + dy[i]));
                    }
                }
            }
        }
        return coordinates;
    }*/

    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCells(HexCoordinate current, HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        if (this.canMoveInsect(g, this.getPlayer())) {
            Map<HexCoordinate, String> neighbors = g.getNeighbors(current, false);
            for (Map.Entry<HexCoordinate, String> entry : neighbors.entrySet()) {
                HexCoordinate neighbor = entry.getKey();
                String direction = entry.getValue();
                if (g.isHiveConnectedAfterMove(current, neighbor)) {
                    HexCoordinate rightNeighbor = g.getNeighbor(current, g.getClockwiseDirection(direction));
                    HexCoordinate leftNeighbor = g.getNeighbor(current, g.getCounterClockwiseDirection(direction));
                    if (g.getCell(leftNeighbor) != null || g.getCell(rightNeighbor) != null) {
                        coordinates.add(neighbor);
                    }
                    if (g.getCell(neighbor) != null) {
                        coordinates.add(neighbor);
                    }
                }
            }
        }
        return coordinates;
    }
}