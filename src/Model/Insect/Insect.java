package Model.Insect;

import Model.HexCell;
import Model.HexGrid;
import Model.Player;
import Structure.HexCoordinate;

import java.io.Serializable;
import java.util.*;

/**
 * Classe abstraite pour les insectes
 */
public abstract class Insect implements Cloneable, Serializable {
    private final Player player;

    /**
     * Constructeur
     *
     * @param player Joueur
     */
    public Insect(Player player) {
        this.player = player;
    }

    /**
     * Renvoie les coordonnées possibles pour le déplacement
     *
     * @param current Coordonnées actuelles
     * @param g       Grille
     * @return ArrayList
     */
    public ArrayList<HexCoordinate> getPossibleMovesCoordinates(HexCoordinate current, HexGrid g) {
        return this.getPossibleMovesCoordinates(current, g, this.getPlayer());
    }

    /**
     * Renvoie les coordonnées possibles pour le déplacement
     *
     * @param current Coordonnées actuelles
     * @param g       Grille
     * @param p       Joueur
     * @return ArrayList
     */
    public abstract ArrayList<HexCoordinate> getPossibleMovesCoordinates(HexCoordinate current, HexGrid g, Player p);

    /**
     * Renvoie les coordonnées possibles pour l'insertion
     *
     * @param g Grille
     * @return ArrayList
     */
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
                        if (g.getCell(k) != null && !g.getCell(k).getTopInsect().getPlayer().equals(this.player)) {
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

    /**
     * Renvoie les coordonnées possibles pour l'insertion
     *
     * @param g Grille
     * @return ArrayList
     */
    public ArrayList<HexCoordinate> getPossibleInsertionCoordinatesT1(HexGrid g) {
        Iterator<HexCoordinate> it = g.getGrid().keySet().iterator();
        return new ArrayList<>(g.getNeighborsCoordinates(it.next(), false).keySet());
    }

    /**
     * Renvoie le joueur
     *
     * @return Player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Vérifie si l'insecte peut se déplacer
     *
     * @param g      Grille
     * @param player Joueur
     * @return boolean
     */
    protected boolean canMoveInsect(HexGrid g, Player player) {
        for (HexCell cell : g.getGrid().values()) {
            for (Insect insect : cell.getInsects()) {
                if (insect.getClass() == Bee.class && insect.getPlayer() == player) {
                    // on peut déplacer des insectes que si la reine est posée
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Test si deux insectes sont égaux
     *
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Insect insect = (Insect) obj;
        // Compare the class names and the players for equality
        return Objects.equals(this.getClass().getSimpleName(), insect.getClass().getSimpleName()) &&
                Objects.equals(this.player, insect.player);
    }

    /**
     * Renvoie le hashcode de l'insecte
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getClass().getSimpleName(), this.player);
    }

    /**
     * Clone l'insecte
     *
     * @return Insect
     */
    @Override
    public Insect clone() {
        try {
            return (Insect) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}