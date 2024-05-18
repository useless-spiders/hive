package Model.Ai;

import Model.HexGrid;
import Model.History;
import Model.Move;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.Log;

public abstract class Ai {
    Player aiPlayer;
    HexGrid grid;
    History history;

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

}
