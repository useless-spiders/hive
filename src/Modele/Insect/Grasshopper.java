package Modele.Insect;

import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;

import Modele.HexGrid;

public class Grasshopper extends Insect {

    public Grasshopper(Player player) {
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
                int nx = x, ny = y;
                while (g.getCell(g.getNeighbor(new HexCoordinate(nx, ny), directions[i])) != null) {
                    nx += dx[i];
                    ny += dy[i];
                }
                if ((nx != x || ny != y) && g.isHiveConnectedAfterMove(current, new HexCoordinate(nx + dx[i], ny + dy[i]))) {
                    nx += dx[i];
                    ny += dy[i];
                    coordinates.add(new HexCoordinate(nx, ny));
                }
            }

        }
        return coordinates;
    }
}