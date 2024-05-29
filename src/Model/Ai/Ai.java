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

    /////////////// methodes calcul heuristique /////////////////////

    /**
     * Renvoie le nombre d'insectes autour de la reine d'un joueur
     * @param p joueur
     * @param g grille de jeu
     * @return double
     */
    public double beeNeighbors(Player p, HexGrid g){
        int result = 0;
        for (HexCoordinate h : g.getGrid().keySet()) {
            HexCell cell = g.getCell(h);
            ArrayList<Insect> insects = cell.getInsects();
            for(Insect i :insects){
                if (i instanceof Bee) {
                    if (i.getPlayer().equals(p)) {
                        // on ne considere pas les cas ou l abeille est entouree de 2 pieces ou moins
                        result = (g.getNeighborsCoordinates(h).size()) - 1;
                        if(result > 0)
                        {
                            result --;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Renvoie le nombre d'insectes en jeu d'un joueur
     * @param p joueur
     * @param g grille de jeu
     * @return double
     */
    public double insectsCount(Player p, HexGrid g){
        double result = 0;
        int ant, bee, beetle, spider, grasshopper;
        int turn = p.getTurn();
        if(turn <= 4){
            ant = 2;
            spider = 4;
            beetle = 4;
            grasshopper = 2;
            //on l'empeche de jouer sa reine abeille tour 1 pour limiter les parties match nul
            if(turn == 1)
            {
                bee = -9999;
            }
            else
            {
                bee = 5;
            }

        }
        else{
            ant = 3;
            spider = 1;
            beetle = 2;
            grasshopper = 2;
            bee = 5;
        }

        for (HexCoordinate h : g.getGrid().keySet()) {
            HexCell cell = g.getCell(h);
            Insect insect = cell.getTopInsect();
            if(insect.getPlayer().equals(p)){
                if (insect instanceof Bee) {
                    result += bee;
                }
                if (insect instanceof Ant) {
                    result += ant;
                }
                if (insect instanceof Beetle) {
                    result += beetle;
                }
                if (insect instanceof Grasshopper) {
                    result += grasshopper;
                }
                if (insect instanceof Spider) {
                    result += spider;
                }
            }
        }
        return result;
    }

    /**
     * Renvoie le nombre de déplacement possible des pieces d'un joueur ainsi que le nombre de cases sur lesquelles il peut placer des insectes
     * @param p joueur
     * @param g grille de jeu
     * @return double
     */
    public double insectFree(Player p, HexGrid g){
        double moveCount = 0;

        for (Class<? extends Insect> i : p.getTypes()) {
            ArrayList<HexCoordinate> possibleCoordinates = this.gameActionHandler.getMoveController().generatePlayableInsertionCoordinates(i, p);
            moveCount += possibleCoordinates.size();
        }
        for (HexCoordinate hex : g.getGrid().keySet()) {
            HexCell cell = g.getCell(hex);
            Insect insect = cell.getTopInsect();

            if (insect.getPlayer().equals(p)) {
                // le deplacement des fourmis et des araignees est de 1
                int ratio = 1;
                if (insect instanceof Bee) {
                    ratio = 10;
                }
                if (insect instanceof Beetle || insect instanceof Grasshopper) {
                    ratio = 2;
                }

                ArrayList<HexCoordinate> possibleCoordinates = insect.getPossibleMovesCoordinates(hex, g, p);
                moveCount += (possibleCoordinates.size() * ratio);
            }
        }
        return moveCount;
    }
    //////////////////////////////////////////////////////////////////////////

    /**
     * appelle le constructeur du type d'Ia donné en argument
     * @param gameActionHandler GameActionHandler
     * @param ia nom de l'ia
     * @param p joueur
     * @return double
     */
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

}
