package Modele.Insect;

import Modele.HexCell;
import Modele.HexGrid;
import Structures.HexCoordinate;
import Structures.Log;
import Modele.Player;

import java.util.*;

public abstract class Insect implements Cloneable {
    private Player player;

    public Insect(Player player) {
        this.player = player;
    }

    public abstract ArrayList<HexCoordinate> getPossibleMovesCells(HexCoordinate current, HexGrid g);

    public ArrayList<HexCoordinate> getPossibleInsertionCoordinates(HexGrid g) {
        ArrayList<HexCoordinate> coords = new ArrayList<>();
        Set<HexCoordinate> coordinates = g.getGrid().keySet();

        for (HexCoordinate h : coordinates) {
            HashMap<HexCoordinate, String> neighbors = g.getNeighbors(h, false);
            for (HexCoordinate neighbor : neighbors.keySet()) {
                if (g.getCell(neighbor) == null) {
                    boolean sameColor = true;
                    HashMap<HexCoordinate, String> neighborOfNeighbor = g.getNeighbors(neighbor);
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
        return new ArrayList<>(g.getNeighbors(it.next(), false).keySet());
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
        Log.addMessage("Aucun déplacement autorisé car l'abeille n'est pas sur le plateau");
        return false;
    }

    public boolean isPlacable(HexCoordinate placement, HexGrid g) {
        Log.addMessage("tour insect :" + this.getPlayer().getTurn() + " " + this.getPlayer().getName());
        if (this.getPlayer().getTurn() > 1) {
            if (g.getCell(placement) != null) {
                return false;
            }
            HashMap<HexCoordinate, String> neighbors = g.getNeighbors(placement);
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
                HashMap<HexCoordinate, String> neighbors = g.getNeighbors(placement);
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