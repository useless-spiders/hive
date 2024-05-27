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

public class MoveController {
    private GameActionHandler gameActionHandler;

    public MoveController(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    public ArrayList<Move> getMoves(HexGrid grid, Player p) {
        Log.addMessage("Grille : " + grid);
        Log.addMessage("Joueur : " + p);
        ArrayList<Move> moves = new ArrayList<>();

        for (Class<? extends Insect> i : p.getTypes()) {
            Insect insect = p.getInsect(i);
            ArrayList<HexCoordinate> possibleCoordinates = this.generatePlayableInsertionCoordinates(i, p);
            for (HexCoordinate h : possibleCoordinates) {
                moves.add(new Move(insect, null, h));
            }
        }

        if (!moves.isEmpty()) {
            Log.addMessage("IA " + p.getName() + " a " + moves.size() + " coups possibles d'insertions");
        }
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
        if (!moves.isEmpty()) {
            Log.addMessage("IA " + p.getName() + " a " + moves.size() + " coups possibles");
        }
        Log.addMessage("Taille de la grille : " + grid.getGrid().size());
        return moves;
    }

    public ArrayList<HexCoordinate> generatePlayableInsertionCoordinates(Class<? extends Insect> insectClass, Player player) {
        ArrayList<HexCoordinate> playableCoordinates = new ArrayList<>();
        Insect insect = player.getInsect(insectClass);
        if (insect != null) {
            if (player.canAddInsect(insect.getClass())) {
                if (player.checkBeePlacement(insect)) {
                    if (player.getTurn() <= 1) {
                        if (this.gameActionHandler.getGrid().getGrid().isEmpty()) {
                            playableCoordinates.add(HexMetrics.hexCenterCoordinate(this.gameActionHandler.getDisplayGame().getWidth(), this.gameActionHandler.getDisplayGame().getHeight()));
                        } else {
                            playableCoordinates = player.getInsect(insect.getClass()).getPossibleInsertionCoordinatesT1(this.gameActionHandler.getGrid());
                        }
                    } else {
                        playableCoordinates = player.getInsect(insect.getClass()).getPossibleInsertionCoordinates(this.gameActionHandler.getGrid());
                    }
                } else {
                    Log.addMessage("Vous devez placer l'abeille avant de placer un autre insecte");
                }
            } else {
                Log.addMessage("Vous avez atteint le nombre maximum de pions de ce type");
            }
        } else {
            Log.addMessage("Vous n'avez plus de pions de ce type");
        }
        return playableCoordinates;
    }
}
