package Model.Ai;

import Model.HexGrid;
import Model.Move;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.Log;

import java.util.ArrayList;
import java.util.Random;

public class AiRandom extends Ai { //Random

    Player other;

    public AiRandom(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayerController().getPlayer1() == aiPlayer) {
            this.other = this.gameActionHandler.getPlayerController().getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayerController().getPlayer1();
        }
    }

    @Override
    double heuristic(HexGrid g) {
        return 0;
    }

    @Override
    public Move chooseMove() {
        ArrayList<Move> moves = this.gameActionHandler.getMoveController().getMoves(this.gameActionHandler.getGrid(), this.aiPlayer);
        Log.addMessage("Taille de Moves pour J1 : " + moves.size());

        ArrayList<Move> moves2 = this.gameActionHandler.getMoveController().getMoves(this.gameActionHandler.getGrid().clone(), this.aiPlayer);
        Log.addMessage("Taille de Moves pour J1 : " + moves2.size());

        Log.addMessage("FINNNNNNNNNNN");

        if (moves.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomMoveIndex = random.nextInt(moves.size());
        return moves.get(randomMoveIndex);
    }

}
