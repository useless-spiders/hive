package Model.Insect;

import Model.Player;
import Structure.HexCoordinate;

import java.util.ArrayList;
import java.util.Map;

import Model.HexGrid;

/**
 * Classe pour les sauterelles
 */
public class Grasshopper extends Insect {

    /**
     * Constructeur
     * @param player Joueur
     */
    public Grasshopper(Player player) {
        super(player);
    }

    /**
     * Renvoie les coordonnées possibles pour le déplacement
     * @param current Coordonnées actuelles
     * @param g Grille
     * @param p Joueur
     * @return ArrayList
     */
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