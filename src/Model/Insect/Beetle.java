package Model.Insect;

import Model.Player;
import Structure.HexCoordinate;

import java.util.ArrayList;
import java.util.Map;

import Model.HexGrid;

public class Beetle extends Insect {

    public Beetle(Player player) {
        super(player);
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCoordinates(HexCoordinate current, HexGrid g, Player p) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        if (this.canMoveInsect(g, p)) {
            Map<HexCoordinate, String> neighbors = g.getNeighborsCoordinates(current, false);
            for (Map.Entry<HexCoordinate, String> entry : neighbors.entrySet()) {
                HexCoordinate neighbor = entry.getKey();
                String direction = entry.getValue();
                if (g.isHiveConnectedAfterMove(current, neighbor)) {
                    HexCoordinate rightNeighbor = g.getNeighborCoordinates(current, g.getClockwiseDirection(direction));
                    HexCoordinate leftNeighbor = g.getNeighborCoordinates(current, g.getCounterClockwiseDirection(direction));
                    if (g.getCell(leftNeighbor) != null || g.getCell(rightNeighbor) != null) {
                        coordinates.add(neighbor);
                    }
                    if (g.getCell(neighbor) != null || !g.getCell(current).getInsects().isEmpty()) {
                        coordinates.add(neighbor);
                    }
                }
            }
        }
        return coordinates;
    }
}