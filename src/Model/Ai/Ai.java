package Model.Ai;

import Model.Insect.Insect;
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
                ArrayList<HexCoordinate> possibleCells = this.gameActionHandler.generatePlayableInsertionCoordinates(i.getClass(), p);
                for (HexCoordinate h : possibleCells) {
                    moves.add(new Move(insect, null, h));
                }
            }
        }
        for(HexCoordinate hex : this.gameActionHandler.getGrid().getGrid().keySet()){
            if(this.gameActionHandler.getGrid().getCell(hex) != null && this.gameActionHandler.getGrid().getCell(hex).getTopInsect().getPlayer() == p){
                Insect insect = this.gameActionHandler.getGrid().getCell(hex).getTopInsect();
                ArrayList<HexCoordinate> possibleCells = insect.getPossibleMovesCoordinates(hex, this.gameActionHandler.getGrid());
                for (HexCoordinate h : possibleCells) {
                    moves.add(new Move(insect, hex, h));
                }
            }
        }
        return moves;
    }

}
