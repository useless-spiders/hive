package Model.Ai;

import java.util.ArrayList;
import java.util.Random;
import Model.HexGrid;
import Model.Move;
import Model.Player;
import Pattern.GameActionHandler;

public class Ai1 extends Ai { //1 Coup

    Player other;

    public Ai1(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayer1() == aiPlayer) {
            this.other = this.gameActionHandler.getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayer1();
        }
    }

    @Override
    double heuristic(HexGrid g) {
        double result = 0;
        result -= beeNeighbors(this.aiPlayer, g)*0.9;
        result += beeNeighbors(this.other, g)*0.9;
        result += insectsCount(this.aiPlayer, g)*0.1;
        result -= insectsCount(this.other, g)*0.1;
        return result;
    }

    public Move chooseMove() {
        HexGrid g = this.gameActionHandler.getGrid().clone();
        ArrayList<Move> toPlay = new ArrayList<>();
        double score;
        double score_max = 0;
        Random randomNumbers = new Random();
        Player us_c = this.aiPlayer.clone();
        for (Move m : getMoves(g, this.aiPlayer)) {
            g.applyMove(m, us_c);
            score = heuristic(g);
            if (score > score_max) {
                score_max = score;
                toPlay.clear();
                toPlay.add(m);
            }
            if (score == score_max) {
                toPlay.add(m);
            }
            g.unapplyMove(m, us_c);
        }
        if (toPlay.isEmpty()) {
            return null;
        }

        return toPlay.get(randomNumbers.nextInt(toPlay.size()));
    }

}
