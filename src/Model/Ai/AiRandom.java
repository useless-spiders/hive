package Model.Ai;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import Model.Player;
import Model.Insect.*;

import Model.Move;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;

public class AiRandom extends Ai {

    Random r;
    GameActionHandler gameActionHandler;

    public AiRandom(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.grid = this.gameActionHandler.getGrid();
        this.history = this.gameActionHandler.getHistory();
        this.r = new Random();
        this.aiPlayer = p;
    }

    private ArrayList<Move> getMoves(Player p) {
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
