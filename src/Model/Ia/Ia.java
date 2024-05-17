package Model.Ia;

import Controller.Game;
import Model.HexGrid;
import Model.History;
import Model.Move;
import Model.Player;
import Structure.Log;

public abstract class Ia {
    Player us;
    HexGrid grid;
    History history;

    public static Ia nouvelle(Game g, String ia, Player p) {
        Ia resultat = null;
        switch (ia) {
            case "Aleatoire":
                resultat = new IaAleatoire(g, p);
                break;
            case "1":
                resultat = new Ia1(g, p);
                break;
            default:
                Log.addMessage("IA de type " + ia + " non supportée");
        }
        return resultat;
    }

    public Player getPlayer() {
        return this.us;
    }

    Move chooseMove() {
        return null;
    }

    public void playMove() {
        chooseMove();
    }

}
