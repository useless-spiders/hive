package Model.Ai;

import java.util.ArrayList;
import java.util.Random;

import Model.Player;

import Model.Move;
import Pattern.GameActionHandler;

public class AiRandom extends Ai {

    Random r;

    public AiRandom(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.grid = this.gameActionHandler.getGrid();
        this.history = this.gameActionHandler.getHistory();
        this.r = new Random();
        this.aiPlayer = p;
    }

    @Override
    public Move chooseMove() {
        ArrayList<Move> moves = getMoves(this.aiPlayer);

        if (moves.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomMoveIndex = random.nextInt(moves.size());
        return moves.get(randomMoveIndex);
    }

}
