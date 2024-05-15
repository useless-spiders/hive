package Modele.Insect;

import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;
import java.util.Map;

import Modele.HexGrid;

public class Grasshopper extends Insect {

    public Grasshopper(Player player) {
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