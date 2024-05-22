package Model.Ai;

import Model.HexCell;
import Model.HexGrid;
import Model.Insect.Ant;
import Model.Insect.Bee;
import Model.Insect.Beetle;
import Model.Insect.Grasshopper;
import Model.Insect.Insect;
import Model.Insect.Spider;
import Model.Move;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.Log;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Ai implements Serializable {
    protected Player aiPlayer;

    // transient indique que l'attribut ne doit pas être sérialisé (utile pour la sauvegarde)
    transient GameActionHandler gameActionHandler;

    public void setGameActionHandler(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    abstract double heuristic(HexGrid g);

    public double beeNeighbors(Player p, HexGrid g){
        for (HexCoordinate h : g.getGrid().keySet()) {
            HexCell cell = g.getCell(h);
            Insect insect = cell.getTopInsect();
            if (insect instanceof Bee) {
                if (insect.getPlayer() == p) {
                return g.getNeighborsCoordinates(h).size()/6;
                }
            }
        }
        return 0;
    }

    public double insectsCount(Player p, HexGrid g){
        double result = 0;
        for (HexCoordinate h : g.getGrid().keySet()) {
            HexCell cell = g.getCell(h);
            Insect insect = cell.getTopInsect();
            if(insect.getPlayer() == p){
                if (insect instanceof Ant) {
                    result += 3;
                }
                if (insect instanceof Beetle) {
                    result += 2;
                }
                if (insect instanceof Grasshopper) {
                    result += 2;
                }
                if (insect instanceof Spider) {
                    result += 1;
                }
            }
        }
        return result/22;
    }

    public static Ai nouvelle(GameActionHandler gameActionHandler, String ia, Player p) {
        Ai resultat = null;
        switch (ia) {
            case "AiRandom":
                resultat = new AiRandom(gameActionHandler, p);
                break;
            case "Ai1":
                resultat = new Ai1(gameActionHandler, p);
                break;
            case "Ai2":
                resultat = new Ai2(gameActionHandler, p);
                break;
            case "Ai3":
                resultat = new Ai3(gameActionHandler, p);
                break;
            default:
                Log.addMessage("IA de type " + ia + " non supportée");
        }
        return resultat;
    }

    public abstract Move chooseMove();

    protected ArrayList<Move> getMoves(HexGrid grid, Player p) {
        Log.addMessage("Grille : " + grid);
        Log.addMessage("Joueur : " + p);
        ArrayList<Move> moves = new ArrayList<>();

        for (Class<? extends Insect> i : p.getTypes()) {
            Insect insect = p.getInsect(i);
            ArrayList<HexCoordinate> possibleCells = this.gameActionHandler.generatePlayableInsertionCoordinates(i, p);
            for (HexCoordinate h : possibleCells) {
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
                ArrayList<HexCoordinate> possibleCells = insect.getPossibleMovesCoordinates(hex, grid, p);
                for (HexCoordinate h : possibleCells) {
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

}
