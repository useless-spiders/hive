package Model.Insect;

import Model.Player;
import Structure.HexCoordinate;

import java.util.ArrayList;
import java.util.Map;

import Model.HexGrid;

public class Bee extends Insect {

    public Bee(Player player) {
        super(player);
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCoordinates(HexCoordinate current, HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        if (this.canMoveInsect(g, this.getPlayer())) {
            Map<HexCoordinate, String> neighbors = g.getNeighborsCoordinates(current, false);
            for (Map.Entry<HexCoordinate, String> entry : neighbors.entrySet()) {
                HexCoordinate neighbor = entry.getKey();
                String direction = entry.getValue();
                if (g.getCell(neighbor) == null && g.isHiveConnectedAfterMove(current, neighbor)) {
                    HexCoordinate rightNeighbor = g.getNeighborCoordinates(current, g.getClockwiseDirection(direction));
                    HexCoordinate leftNeighbor = g.getNeighborCoordinates(current, g.getCounterClockwiseDirection(direction));
                    if ((g.getCell(rightNeighbor) == null && g.getCell(leftNeighbor) != null) || (g.getCell(rightNeighbor) != null && g.getCell(leftNeighbor) == null)) {
                        coordinates.add(neighbor);
                    }
                }
            }
        }
        return coordinates;
    }


}