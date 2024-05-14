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

    public abstract int getMax();

    public abstract ArrayList<HexCoordinate> getPossibleMovesCells(HexCoordinate current, HexGrid g);

    public ArrayList<HexCoordinate> getPossibleInsertionCells(HexGrid g) {
        ArrayList<HexCoordinate> possibleInsertionCells = new ArrayList<>();
        Set<HexCoordinate> coordinates = g.getGrid().keySet();
        for (HexCoordinate h : coordinates) {
            String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
            int[] dx = {0, 1, 1, 0, -1, -1};
            int[] dy = {-1, -1, 0, 1, 1, 0};
            for (int i = 0; i < dx.length; i++) {
                if (g.getNeighbor(h, directions[i]) == null) {
                    //voisin d'une case de la grille
                    HexCoordinate current = new HexCoordinate(h.getX() + dx[i], h.getY() + dy[i]);

                    HashMap<HexCoordinate, String> neighbors = g.getNeighbors(current);
                    boolean sameColor = true;
                    Set<HexCoordinate> keys = neighbors.keySet();
                    if (!keys.isEmpty()) {
                        for (HexCoordinate k : keys) {
                            if (g.getCell(k).getTopInsect().getPlayer().getColor() != this.getPlayer().getColor()) {
                                sameColor = false;
                            }
                        }
                        if (sameColor) {
                            possibleInsertionCells.add(current);
                        }
                    }
                }
            }
        }
        return possibleInsertionCells;
    }

    public ArrayList<HexCoordinate> getPossibleInsertionCellT1(HexGrid g) {
        ArrayList<HexCoordinate> possibleInsertionCells = new ArrayList<>();
        Set<HexCoordinate> coordinates = g.getGrid().keySet();
        int x, y;
        for (HexCoordinate h : coordinates) {
            x = h.getX();
            y = h.getY();
            possibleInsertionCells.add(new HexCoordinate(x, y - 1));
            possibleInsertionCells.add(new HexCoordinate(x + 1, y - 1));
            possibleInsertionCells.add(new HexCoordinate(x + 1, y));
            possibleInsertionCells.add(new HexCoordinate(x, y + 1));
            possibleInsertionCells.add(new HexCoordinate(x - 1, y + 1));
            possibleInsertionCells.add(new HexCoordinate(x - 1, y));
        }
        return possibleInsertionCells;
    }

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