package Controller;

import Model.HexCell;
import Model.HexGrid;
import Model.Insect.Insect;
import Model.Move;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.Log;

import java.util.ArrayList;

/**
 * Controleur pour les mouvements
 */
public class MoveController {
    private GameActionHandler gameActionHandler;

    /**
     * Constructeur
     * @param gameActionHandler GameActionHandler
     */
    public MoveController(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    /**
     * Renvoie les mouvements possibles
     * @param grid HexGrid
     * @param p Player
     * @return ArrayList<Move>
     */
    public ArrayList<Move> getMoves(HexGrid grid, Player p) {
        //Log.addMessage("Grille : " + grid);
        //Log.addMessage("Joueur : " + p);
        ArrayList<Move> moves = new ArrayList<>();

        // On récupère les insertions possibles
        for (Class<? extends Insect> i : p.getTypes()) {
            Insect insect = p.getInsect(i);
            ArrayList<HexCoordinate> possibleCoordinates = this.generatePlayableInsertionCoordinates(i, p);
            for (HexCoordinate h : possibleCoordinates) {
                moves.add(new Move(insect, null, h));
            }
        }

        //if (!moves.isEmpty()) {
        //    Log.addMessage("IA " + p.getName() + " a " + moves.size() + " coups possibles d'insertions");
        //}

        // On récupère les déplacements possibles
        for (HexCoordinate hex : grid.getGrid().keySet()) {
            HexCell cell = grid.getCell(hex);
            Insect insect = cell.getTopInsect();

            if (insect.getPlayer().equals(p)) {
                ArrayList<HexCoordinate> possibleCoordinates = insect.getPossibleMovesCoordinates(hex, grid, p);
                for (HexCoordinate h : possibleCoordinates) {
                    moves.add(new Move(insect, hex, h));
                }
            }
        }
        //if (!moves.isEmpty()) {
        //    Log.addMessage("IA " + p.getName() + " a " + moves.size() + " coups possibles");
        //}
        //Log.addMessage("Taille de la grille : " + grid.getGrid().size());
        return moves;
    }

    /**
     * Renvoie les coordonnées d'insertion possibles
     * @param insectClass Class<? extends Insect>
     * @param player Player
     * @return ArrayList<HexCoordinate>
     */
    public ArrayList<HexCoordinate> generatePlayableInsertionCoordinates(Class<? extends Insect> insectClass, Player player) {
        ArrayList<HexCoordinate> playableCoordinates = new ArrayList<>();
        Insect insect = player.getInsect(insectClass);
        if (insect != null) {
            if (player.canAddInsect(insect.getClass())) {
                if (player.checkBeePlacement(insect)) {
                    if (player.getTurn() <= 1) {
                        if (this.gameActionHandler.getGrid().getGrid().isEmpty()) { // on force la première insertion au centre de l'affichage
                            playableCoordinates.add(HexMetrics.hexCenterCoordinate(this.gameActionHandler.getDisplayGame().getWidth(), this.gameActionHandler.getDisplayGame().getHeight()));
                        } else { // Les insertions possibles au tour 1
                            playableCoordinates = player.getInsect(insect.getClass()).getPossibleInsertionCoordinatesT1(this.gameActionHandler.getGrid());
                        }
                    } else { // Les insertions possibles à partir du tour 2
                        playableCoordinates = player.getInsect(insect.getClass()).getPossibleInsertionCoordinates(this.gameActionHandler.getGrid());
                    }
                } else {
                    Log.addMessage(this.gameActionHandler.getMessages().getString("move.bee.insertion"));
                }
            } else {
                Log.addMessage(this.gameActionHandler.getMessages().getString("move.insect.max.error"));
            }
        } else {
            Log.addMessage(this.gameActionHandler.getMessages().getString("move.insect.error"));
        }
        return playableCoordinates;
    }
}
