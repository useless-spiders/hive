package Model.Ai;

import Controller.Game;
import Model.HexGrid;
import Model.History;
import Model.Move;
import Model.Player;
import Structure.Log;

public abstract class Ai {
    Player us;
    HexGrid grid;
    History history;

    public static Ai nouvelle(Game g, String ia, Player p) {
        Ai resultat = null;
        switch (ia) {
            case "AiRandom":
                resultat = new AiRandom(g, p);
                break;
            case "Ai1":
                resultat = new Ai1(g, p);
                break;
            default:
                Log.addMessage("IA de type " + ia + " non supportée");
        }
        return resultat;
    }

    public Player getPlayer() {
        return this.us;
    }

    public Move chooseMove() {
        return null;
    }

}
