package Modele.Insect;

import Modele.HexCell;
import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import Modele.HexGrid;

public class Ant extends Insect {

    public Ant(Player player) {
        super(player);
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCells(HexCoordinate current, HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        HashSet<HexCoordinate> visited = new HashSet<>();
        if (this.canMoveInsect(g, this.getPlayer())) {
            this.getPossibleMovesCellsHelper(current, g, coordinates, current, visited);
        }
        return coordinates;
    }

    private void getPossibleMovesCellsHelper(HexCoordinate current, HexGrid g, ArrayList<HexCoordinate> coordinates, HexCoordinate original, HashSet<HexCoordinate> visited) {
        if (!current.equals(original)) {
            coordinates.add(current);
        }


        Map<HexCoordinate, String> neighbors = g.getNeighbors(current, false);
        for (Map.Entry<HexCoordinate, String> entry : neighbors.entrySet()) {
            HexCoordinate neighbor = entry.getKey();
            String direction = entry.getValue();

            if (!visited.contains(neighbor) && g.getCell(neighbor) == null && g.isHiveConnectedAfterMove(original, neighbor)) {
                HexCoordinate adjCoord = g.getNeighbor(current, g.getCounterClockwiseDirection(direction));
                HexCoordinate adjCoord2 = g.getNeighbor(current, g.getClockwiseDirection(direction));

                HexCell adj = g.getCell(adjCoord);
                HexCell adj2 = g.getCell(adjCoord2);

                if (original.getX() == adjCoord.getX() & original.getY() == adjCoord.getY()) {
                    adj = null;
                }

                if (original.getX() == adjCoord2.getX() & original.getY() == adjCoord2.getY()) {
                    adj2 = null;
                }

                if ((adj == null && adj2 != null) || (adj != null && adj2 == null)) {
                    visited.add(neighbor);
                    this.getPossibleMovesCellsHelper(neighbor, g, coordinates, original, visited);
                }
            }

        }
    }

}