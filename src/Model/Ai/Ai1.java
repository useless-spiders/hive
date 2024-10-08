package Model.Ai;

import Model.HexGrid;
import Model.Move;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.Log;

import java.util.ArrayList;
import java.util.Random;

public class Ai1 extends Ai { //1 Coup

    Player other;

    /**
     * Constructeur
     */
    public Ai1(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayerController().getPlayer1().equals(this.aiPlayer)) {
            this.other = this.gameActionHandler.getPlayerController().getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayerController().getPlayer1();
        }
    }

    /**
     * Calcule l'heuristique pour une grille donnée
     *
     * @param g grille de jeu
     * @return double
     */
    @Override
    double heuristic(HexGrid g) {
        double result = 0;
        result -= beeNeighbors(this.aiPlayer, g) * 0.9;
        result += beeNeighbors(this.other, g) * 0.9;
        result += insectsCount(this.aiPlayer, g) * 0.1;
        result -= insectsCount(this.other, g) * 0.1;
        return result;
    }


    /**
     * Choisis le coup à jouer pour par l'Ia
     *
     * @return coup à jouer
     */
    @Override
    public Move chooseMove() {
        HexGrid g = this.gameActionHandler.getGrid().clone();
        ArrayList<Move> toPlay = new ArrayList<>();
        double score;
        double score_max = -9999;
        Random randomNumbers = new Random();
        Player us_c = this.aiPlayer.clone();
        for (Move m : this.gameActionHandler.getMoveController().getMoves(g, this.aiPlayer)) {
            g.applyMove(m, us_c);
            if (!g.checkLoser(us_c)) {
                if (g.checkLoser(this.other)) {
                    Log.addMessage("on a gagné");
                    score = 9999;
                } else {
                    score = this.heuristic(g);
                }

                if (score > score_max) {
                    score_max = score;
                    toPlay.clear();
                    toPlay.add(m);
                }
                if (score == score_max) {
                    toPlay.add(m);
                }
            } else {
                Log.addMessage("on a perdu");
            }
            g.unapplyMove(m, us_c);
        }
        if (toPlay.isEmpty()) {
            return null;
        }

        return toPlay.get(randomNumbers.nextInt(toPlay.size()));
    }

}
