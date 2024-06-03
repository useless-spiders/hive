package Model.Insect;

import Model.HexCell;
import Model.HexGrid;
import Model.Player;
import Structure.HexCoordinate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 * Classe pour les fourmis
 */
public class Ant extends Insect {

    /**
     * Constructeur
     *
     * @param player Joueur
     */
    public Ant(Player player) {
        super(player);
    }

    /**
     * Renvoie les coordonnées possibles pour le déplacement
     *
     * @param current Coordonnées actuelles
     * @param g       Grille
     * @param p       Joueur
     * @return ArrayList
     */
    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCoordinates(HexCoordinate current, HexGrid g, Player p) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        HashSet<HexCoordinate> visited = new HashSet<>();
        if (this.canMoveInsect(g, p)) {
            this.getPossibleMovesCoordinatesHelper(current, g, coordinates, current, visited);
        }
        return coordinates;
    }

    /**
     * Renvoie les coordonnées possibles pour le déplacement
     *
     * @param current     Coordonnées actuelles
     * @param g           Grille
     * @param coordinates Liste des coordonnées
     * @param original    Coordonnées d'origine
     * @param visited     Liste des coordonnées visitées
     */
    private void getPossibleMovesCoordinatesHelper(HexCoordinate current, HexGrid g, ArrayList<HexCoordinate> coordinates, HexCoordinate original, HashSet<HexCoordinate> visited) {
        if (!current.equals(original)) {
            coordinates.add(current);
        }


        Map<HexCoordinate, String> neighbors = g.getNeighborsCoordinates(current, false);
        for (Map.Entry<HexCoordinate, String> entry : neighbors.entrySet()) {
            HexCoordinate neighbor = entry.getKey();
            String direction = entry.getValue();

            if (!visited.contains(neighbor) && g.getCell(neighbor) == null && g.isHiveConnectedAfterMove(original, neighbor)) {
                HexCoordinate adjCoord = g.getNeighborCoordinates(current, g.getCounterClockwiseDirection(direction));
                HexCoordinate adjCoord2 = g.getNeighborCoordinates(current, g.getClockwiseDirection(direction));

                HexCell adj = g.getCell(adjCoord);
                HexCell adj2 = g.getCell(adjCoord2);

                if (original.equals(adjCoord)) {
                    adj = null;
                }

                if (original.equals(adjCoord2)) {
                    adj2 = null;
                }

                if ((adj == null && adj2 != null) || (adj != null && adj2 == null)) {
                    visited.add(neighbor);
                    this.getPossibleMovesCoordinatesHelper(neighbor, g, coordinates, original, visited);
                }
            }

        }
    }

}