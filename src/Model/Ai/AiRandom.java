package Model.Ai;

import java.util.ArrayList;
import java.util.Random;

import Model.Player;

import Model.Move;
import Pattern.GameActionHandler;
import Structure.Log;

public class AiRandom extends Ai {

    Player other;

    public AiRandom(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayer1() == aiPlayer) {
            this.other = this.gameActionHandler.getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayer1();
        }
    }

    @Override
    public Move chooseMove() {
        ArrayList<Move> moves = getMoves(this.gameActionHandler.getGrid(), this.aiPlayer);
        Log.addMessage("Taille de Moves pour J1 : " + moves.size());

        ArrayList<Move> moves2 = getMoves(this.gameActionHandler.getGrid().clone(), this.aiPlayer);
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
