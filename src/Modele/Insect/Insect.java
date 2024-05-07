package Modele.Insect;

import Modele.HexCell;
import Modele.HexGrid;
import Structures.HexCoordinate;
import Structures.Log;
import Modele.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Insect {
    private Player player;

    public Insect(Player player) {
        this.player = player;
    }

    public abstract int getMax();

    public abstract ArrayList<HexCoordinate> getPossibleMovesCells(int x, int y, HexGrid g);

    public abstract ArrayList<HexCoordinate> getPossibleInsertionCells(HexGrid g);

    public String getImageName() {
        return this.getClass().getSimpleName() + "_" + this.player.getColor() + ".png";
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
        System.out.println("Aucun déplacement autorisé car l'abeille n'est pas sur le plateau");
        return false;
    }

    public boolean isPlacable(HexCoordinate placement, HexGrid g) {
        Log.addMessage("tour insect :" + this.getPlayer().getTurn());
        if (this.getPlayer().getTurn() > 1) {
            if (g.getCell(placement.getX(), placement.getY()) != null) {
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
                if (colorNeighbor != this.getPlayer().getColor()) {
                    return false;
                }
            }
            return true;
        } else {
            if (g.getGrid().isEmpty()) {
                return true;
            } else {
                HashMap<HexCoordinate, String> neighbors = g.getNeighbors(placement);
                if (neighbors.isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
}