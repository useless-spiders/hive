package Model.Ai;

import Model.HexGrid;
import Model.History;
import Model.Insect.Insect;
import Model.Move;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.Log;

import java.util.ArrayList;

public abstract class Ai {
    Player aiPlayer;
    HexGrid grid;
    History history;
    GameActionHandler gameActionHandler;

    public static Ai nouvelle(GameActionHandler gameActionHandler, String ia, Player p) {
        Ai resultat = null;
        switch (ia) {
            case "AiRandom":
                resultat = new AiRandom(gameActionHandler, p);
                break;
            case "Ai1":
                resultat = new Ai1(gameActionHandler, p);
                break;
            default:
                Log.addMessage("IA de type " + ia + " non supportée");
        }
        return resultat;
    }

    public abstract Move chooseMove();

    protected ArrayList<Move> getMoves(Player p) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Insect i : p.getStock()) {
            if (p.canAddInsect(i.getClass())) {
                Insect insect = p.getInsect(i.getClass());
                ArrayList<HexCoordinate> possibleCells = this.gameActionHandler.generatePlayableCoordinates(i.getClass(), p);
                for (HexCoordinate h : possibleCells) {
                    moves.add(new Move(insect, null, h));
                }
            }
        }
        return moves;
    }

}
