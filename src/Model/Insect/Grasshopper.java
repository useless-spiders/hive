package Model.Insect;

import Model.Player;
import Structure.HexCoordinate;

import java.util.ArrayList;
import java.util.Map;

import Model.HexGrid;

public class Grasshopper extends Insect {

    public Grasshopper(Player player) {
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
                HexCoordinate next = neighbor;
                while (g.getCell(next) != null) {
                    next = g.getNeighborCoordinates(next, direction);
                }
                if(next != neighbor && g.isHiveConnectedAfterMove(current, next)){
                    coordinates.add(next);
                }
            }
        }
        return coordinates;
    }
}