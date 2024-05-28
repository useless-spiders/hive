package Controller;

import Global.Configuration;
import Model.Ai.Ai;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Controleur pour les joueurs
 */
public class PlayerController {
    private GameActionHandler gameActionHandler;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    /**
     * Constructeur
     * @param gameActionHandler GameActionHandler
     */
    public PlayerController(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    /**
     * Initialise les joueurs
     */
    public void initPlayers() {
        Random random = new Random();
        int color = random.nextInt(Configuration.PLAYER_NUMBER);
        this.player1 = new Player(Configuration.PLAYER_1);
        this.player2 = new Player(Configuration.PLAYER_2);
        this.player1.setColor(color);
        this.player2.setColor((color + 1) % Configuration.PLAYER_NUMBER);

        if (this.player1.getColor() == Configuration.PLAYER_WHITE) {
            this.currentPlayer = this.player1;
        } else {
            this.currentPlayer = this.player2;
        }
    }

    /**
     * Passe au joueur suivant et test si un joueur a perdu
     */
    public void switchPlayer() {
        this.gameActionHandler.getGameActionListener().setPlayableCoordinates(new ArrayList<>());
        int winner = this.getWinner();
        if (winner != -1) {
            this.gameActionHandler.getPageController().getDisplayMain().getDisplayWin().updateWinner(winner);
            this.gameActionHandler.getDisplayGame().repaint(); //Pour afficher le dernier coup
            this.gameActionHandler.getPageController().gameAndWin();
        } else {
            this.currentPlayer.incrementTurn();
            if (this.currentPlayer == this.player1) {
                this.currentPlayer = this.player2;
            } else {
                this.currentPlayer = this.player1;
            }
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateButtons();
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateBorderBank();
            if (this.currentPlayer.isAi()) {
                this.gameActionHandler.getAiController().getDelay().start();
            }
            if(this.gameActionHandler.getMoveController().getMoves(this.gameActionHandler.getGrid(), this.currentPlayer).isEmpty()){
                Log.addMessage("Le joueur ne peut rien faire, on change donc de joueur !");
                this.switchPlayer();
            }
        }
    }

    /**
     * Test si un joueur a perdu et renvoie le gagnant
     * @return int
     */
    public int getWinner() {
        boolean lPlayer1 = this.gameActionHandler.getGrid().checkLoser(player1);
        boolean lPlayer2 = this.gameActionHandler.getGrid().checkLoser(player2);
        if (lPlayer1 && lPlayer2) {
            Log.addMessage("Egalité !");
            return 0;
        } else {
            if (lPlayer1) {
                Log.addMessage("Le joueur " + player2.getColor() + " a gagné !");
                return 2; // return winner
            } else if (lPlayer2) {
                Log.addMessage("Le joueur " + player1.getColor() + " a gagné !");
                return 1; // return winner
            }
        }
        return -1;
    }

    /**
     * Change un joueur en IA
     * @param player int
     * @param name String
     */
    public void setAiPlayer(int player, String name) {
        if (player == 1) {
            this.player1.setName(name);
            this.player1.setAi(Ai.nouvelle(this.gameActionHandler, name, this.player1));
        } else {
            this.player2.setName(name);
            this.player2.setAi(Ai.nouvelle(this.gameActionHandler, name, this.player2));
        }
    }

    /**
     * Renvoie le joueur 1
     * @return Player
     */
    public Player getPlayer1() {
        return this.player1;
    }

    /**
     * Définit le joueur 1
     * @param player1 Player
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * Renvoie le joueur 2
     * @return Player
     */
    public Player getPlayer2() {
        return this.player2;
    }

    /**
     * Définit le joueur 2
     * @param player2 Player
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * Renvoie le joueur courant
     * @return Player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Définit le joueur courant
     * @param currentPlayer Player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Réinitialise les joueurs
     */
    public void resetPlayers(){
        this.player1.reset();
        this.player2.reset();
    }
}
