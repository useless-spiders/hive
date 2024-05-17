package Model.Insect;

import Model.HexCell;
import Model.HexGrid;
import Structure.HexCoordinate;
import Structure.Log;
import Model.Player;

import java.io.Serializable;
import java.util.*;

public abstract class Insect implements Cloneable, Serializable {
    private Player player;

    public Insect(Player player) {
        this.player = player;
    }

    public abstract ArrayList<HexCoordinate> getPossibleMovesCoordinates(HexCoordinate current, HexGrid g);

    public ArrayList<HexCoordinate> getPossibleInsertionCoordinates(HexGrid g) {
        ArrayList<HexCoordinate> coords = new ArrayList<>();
        Set<HexCoordinate> coordinates = g.getGrid().keySet();

        for (HexCoordinate h : coordinates) {
            HashMap<HexCoordinate, String> neighbors = g.getNeighborsCoordinates(h, false);
            for (HexCoordinate neighbor : neighbors.keySet()) {
                if (g.getCell(neighbor) == null) {
                    boolean sameColor = true;
                    HashMap<HexCoordinate, String> neighborOfNeighbor = g.getNeighborsCoordinates(neighbor);
                    for (HexCoordinate k : neighborOfNeighbor.keySet()) {
                        if (g.getCell(k) != null && !Objects.equals(g.getCell(k).getTopInsect().getPlayer().getColor(), this.getPlayer().getColor())) {
                            sameColor = false;
                            break;
                        }
                    }
                    if (sameColor) {
                        coords.add(neighbor);
                    }
                }
            }
        }
        return coords;
    }

    public ArrayList<HexCoordinate> getPossibleInsertionCoordinatesT1(HexGrid g) {
        Iterator<HexCoordinate> it = g.getGrid().keySet().iterator();
        return new ArrayList<>(g.getNeighborsCoordinates(it.next(), false).keySet());
    }

    public Player getPlayer() {
        return this.player;
    }

    // can't move any insect if the Bee is not in the grid
    protected boolean canMoveInsect(HexGrid g, Player player) {
        for (HexCell cell : g.getGrid().values()) {
            for (Insect insect : cell.getInsects()) {
                if (insect.getPlayer() == player && insect.getClass() == Bee.class) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPlacable(HexCoordinate placement, HexGrid g) {
        if (this.getPlayer().getTurn() > 1) {
            if (g.getCell(placement) != null) {
                return false;
            }
            HashMap<HexCoordinate, String> neighbors = g.getNeighborsCoordinates(placement);
            if ((neighbors.isEmpty())) {
                return false;
            }
            for (Map.Entry<HexCoordinate, String> neighbor : neighbors.entrySet()) {
                HexCoordinate hC = neighbor.getKey();
                String colorNeighbor = g.getCell(hC).getTopInsect().getPlayer().getColor();
                Log.addMessage(colorNeighbor);
                Log.addMessage(this.getPlayer().getColor());
                if (!Objects.equals(colorNeighbor, this.getPlayer().getColor())) {
                    return false;
                }
            }
            return true;
        } else {
            if (g.getGrid().isEmpty()) {
                return true;
            } else {
                HashMap<HexCoordinate, String> neighbors = g.getNeighborsCoordinates(placement);
                return !neighbors.isEmpty();
            }
        }
    }

    @Override
    public Insect clone() {
        try {
            Insect newInsect = (Insect) super.clone();
            newInsect.player = this.player.clone();
            return newInsect;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}