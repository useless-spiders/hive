package Controller;

import Global.Configuration;
import Model.Ai.Ai;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.Log;

import java.util.ArrayList;
import java.util.Random;

public class PlayerController {
    private GameActionHandler gameActionHandler;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public PlayerController(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    public void initPlayers() {
        Random random = new Random();
        int color = random.nextInt(2);
        this.player1 = new Player("Inspecteur blanco");
        this.player2 = new Player("Barbe noir");
        this.player1.setColor(color);
        this.player2.setColor((color + 1) % 2);

        if (this.player1.getColor() == Configuration.PLAYER_WHITE) {
            this.currentPlayer = this.player1;
        } else {
            this.currentPlayer = this.player2;
        }
    }

    public void switchPlayer() {
        this.gameActionHandler.getGameActionListener().setPlayableCoordinates(new ArrayList<>());
        int winner = this.checkLoser();
        if (winner != -1) {
            this.gameActionHandler.getPageController().getDisplayMain().getDisplayWin().updateWinner(winner);
            this.gameActionHandler.getDisplayGame().repaint(); //Pour afficher le dernier coup
            this.gameActionHandler.getPageController().gameAndWin();
        } else {
            this.currentPlayer.incrementTurn();
            if (this.currentPlayer == this.player1) {
                this.gameActionHandler.getDisplayGame().getDisplayBankInsects().switchBorderJ1ToJ2();
                this.currentPlayer = this.player2;
            } else {
                this.gameActionHandler.getDisplayGame().getDisplayBankInsects().switchBorderJ2ToJ1();
                this.currentPlayer = this.player1;
            }
            if (this.currentPlayer.isAi()) {
                this.gameActionHandler.getAiController().getDelay().start();
            }
            if(this.gameActionHandler.getMoveController().getMoves(this.gameActionHandler.getGrid(), this.currentPlayer).isEmpty()){
                Log.addMessage("Le joueur ne peut rien faire, on change donc de joueur !");
                this.switchPlayer();
            }
        }
    }

    public int checkLoser() {
        boolean lPlayer1 = this.gameActionHandler.getGrid().checkLoser(player1);
        boolean lPlayer2 = this.gameActionHandler.getGrid().checkLoser(player2);
        if (lPlayer1 && lPlayer2) {
            Log.addMessage("Egalité !");
            return 0;
        } else {
            if (lPlayer1) {
                Log.addMessage("Le joueur " + player1.getColor() + " a perdu !");
                return 2; // return winner
            } else if (lPlayer2) {
                Log.addMessage("Le joueur " + player2.getColor() + " a perdu !");
                return 1; // return winner
            }
        }
        return -1;
    }

    public void setPlayer(int player, String name) {
        if (player == 1) {
            this.player1.setAi(Ai.nouvelle(this.gameActionHandler, name, this.player1));
        } else {
            this.player2.setAi(Ai.nouvelle(this.gameActionHandler, name, this.player2));
        }
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void resetPlayers(){
        this.player1.reset();
        this.player2.reset();
    }
}
