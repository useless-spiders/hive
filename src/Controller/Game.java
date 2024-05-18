package Controller;

import Model.Ia.Ia;
import Model.Move;
import Model.HexCell;
import Model.HexGrid;
import Model.History;
import Model.Insect.Beetle;
import Model.Insect.Insect;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.Log;
import View.DisplayGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class Game implements GameActionHandler, ActionListener {
    private HexGrid hexGrid;
    private DisplayGame displayGame;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean isInsectButtonClicked;
    private boolean isInsectCellClicked;
    private HexCoordinate hexClicked;
    private ArrayList<HexCoordinate> playableCoordinates;
    private History history;
    private Insect insect;
    private Ia iaPlayer1;
    private Ia iaPlayer2;
    private Timer delay;
    private PageManager pageManager;


    public Game() {
        this.hexGrid = new HexGrid();
        this.initPlayers();

        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
        this.playableCoordinates = new ArrayList<>();
        this.history = new History();
        this.pageManager = new PageManager(this);
        this.delay = new Timer(1000, this);
        /////////A COMMENTER POUR PVP//////////////
        setPlayer(2, "IADifficile");
        //////////////////////////////////////////
        if (this.currentPlayer.getTurn() <= 1 && (this.iaPlayer1 != null || this.iaPlayer2 != null)) {
            this.aiTurn();
        }
    }

    private void aiTurn(){
        this.delay.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Ia ia = null;
        if (this.iaPlayer1 != null && this.player1 == this.currentPlayer) {
            ia = iaPlayer1;
        } else {
            if (this.iaPlayer2 != null && this.player2 == this.currentPlayer) {
                ia = iaPlayer2;
            }
        }
        if (ia != null) {
            ia.playMove();
            switchPlayer();
            this.displayGame.repaint();
            this.delay.stop();
        }
    }

    @Override
    public Player getPlayer1() {
        return this.player1;
    }

    @Override
    public Player getPlayer2() {
        return this.player2;
    }

    @Override
    public HexGrid getGrid() {
        return this.hexGrid;
    }

    public History getHistory() {
        return this.history;
    }

    public boolean checkCurrentPlayerIsIa() {
        return (this.iaPlayer1 != null && this.currentPlayer == this.player1) || (this.iaPlayer2 != null && this.currentPlayer == this.player2);
    }

    @Override
    public boolean getIsInsectButtonClicked() {
        return this.isInsectButtonClicked;
    }

    @Override
    public boolean getIsInsectCellClicked() {
        return this.isInsectCellClicked;
    }

    @Override
    public ArrayList<HexCoordinate> getPlayableCoordinates() {
        return this.playableCoordinates;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public void setDisplayGame(DisplayGame displayGame) {
        this.displayGame = displayGame;
    }

    @Override
    public DisplayGame getDisplayGame(){
        return this.displayGame;
    }

    private Player checkLoser() {
        boolean lPlayer1 = this.hexGrid.checkLoser(player1);
        boolean lPlayer2 = this.hexGrid.checkLoser(player2);
        if (lPlayer1 && lPlayer2) {
            Log.addMessage("Egalité !");
            return null; // A MODIFIER POUR EGALITE
        } else {
            if (lPlayer1) {
                Log.addMessage("Le joueur " + player1.getColor() + " a perdu !");
                return this.player2;
            } else if (lPlayer2) {
                Log.addMessage("Le joueur " + player2.getColor() + " a perdu !");
                return this.player1;
            }
        }
        return null;
    }


    public void setPlayer(int player, String name) {
        switch (name) {
            case "IAFacile":
                if (player == 1) {
                    this.iaPlayer1 = Ia.nouvelle(this, "Aleatoire", this.player1);
                } else {
                    this.iaPlayer2 = Ia.nouvelle(this, "Aleatoire", this.player2);
                }
                break;
            case "IADifficile":
                if (player == 1) {
                    this.iaPlayer1 = Ia.nouvelle(this, "1", this.player1);
                } else {
                    this.iaPlayer2 = Ia.nouvelle(this, "1", this.player2);
                }
                break;

            default:
                break;
        }
    }

    private void switchPlayer() {
        Player winner = this.checkLoser();
        if (winner != null) {
            this.pageManager.getMainDisplay().getDisplayWin().updateWinner(winner);
            this.pageManager.gameAndWin();
        } else {
            this.currentPlayer.incrementTurn();
            if (this.currentPlayer == this.player1) {
                this.currentPlayer = this.player2;
            } else {
                this.currentPlayer = this.player1;
            }
        }
        this.aiTurn();
    }

    private void switchPlayerHistory() {
        this.displayGame.repaint();
        this.currentPlayer.incrementTurn();
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
        } else {
            this.currentPlayer = this.player1;
        }
        this.displayGame.repaint();
    }

    private void initPlayers() {
        this.player1 = new Player("white", "Inspecteur blanco");
        this.player2 = new Player("black", "Barbe noir");

        Random random = new Random();
        this.currentPlayer = random.nextBoolean() ? player1 : player2;
    }

    public ArrayList<HexCoordinate> generatePlayableCoordinates(Class<? extends Insect> insectClass, Player player){
        ArrayList<HexCoordinate> playableCoordinates = new ArrayList<>();
        if (player.equals(this.currentPlayer)) {
            this.insect = this.currentPlayer.getInsect(insectClass);
            if (this.insect != null) {
                if (this.currentPlayer.canAddInsect(this.insect.getClass())) {
                    if (this.currentPlayer.checkBeePlacement(this.insect)) {
                        if (this.currentPlayer.getTurn() <= 1) {
                            if (this.hexGrid.getGrid().isEmpty()) {
                                Log.addMessage(" debut : tour " + this.currentPlayer.getTurn());
                                playableCoordinates.add(HexMetrics.hexCenterCoordinate(this.displayGame.getWidth(), this.displayGame.getHeight()));
                            } else {
                                playableCoordinates = this.currentPlayer.getInsect(this.insect.getClass()).getPossibleInsertionCoordinatesT1(this.hexGrid);
                            }
                        } else {
                            Log.addMessage("suite : tour " + this.currentPlayer.getTurn());
                            playableCoordinates = this.currentPlayer.getInsect(this.insect.getClass()).getPossibleInsertionCoordinates(this.hexGrid);
                        }
                    } else {
                        Log.addMessage("Vous devez placer l'abeille avant de placer un autre insecte");
                    }
                } else {
                    Log.addMessage("Vous avez atteint le nombre maximum de pions de ce type");
                }
            } else {
                Log.addMessage("Vous n'avez plus de pions de ce type");
            }
        } else {
            Log.addMessage("Pas le bon joueur !");
        }
        return playableCoordinates;
    }

    @Override
    public void handleCellClicked(HexCell cell, HexCoordinate hexagon) { //Clic sur un insecte du plateau
        Insect insect = cell.getTopInsect();

        if (!this.isInsectCellClicked) { //On clique sur un insecte à déplacer
            if (insect.getPlayer().equals(currentPlayer)) {
                this.isInsectCellClicked = true;
                this.hexClicked = hexagon;
                this.playableCoordinates = insect.getPossibleMovesCoordinates(this.hexClicked, this.hexGrid);
                if (this.playableCoordinates.isEmpty() && !this.currentPlayer.isBeePlaced()) {
                    Log.addMessage("Aucun déplacement autorisé car l'abeille n'est pas sur le plateau");
                }
                // rendre transparente la case
                this.displayGame.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);

                // on affiche la pile
                if (hexGrid.getGrid().get(hexClicked).getInsects().size() >= 2) {
                    this.displayGame.getDisplayStack().updateStackClickState(isInsectCellClicked, hexClicked);
                }
            } else {
                Log.addMessage("Ce pion ne vous appartient pas");
            }

        } else {
            HexCell cellClicked = this.hexGrid.getCell(this.hexClicked);
            if (cellClicked.getTopInsect().getClass() == Beetle.class && !hexagon.equals(this.hexClicked)) { //On clique sur un insecte cible d'un scarabée
                this.handleInsectMoved(hexagon);
            } else { //On clique sur un insecte déjà sélectionné
                this.isInsectCellClicked = false;
                this.displayGame.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
                this.displayGame.getDisplayStack().updateStackClickState(isInsectCellClicked, hexClicked);
                this.playableCoordinates.clear();
            }
        }
    }

    @Override
    public void handleInsectMoved(HexCoordinate hexagon) {
        if (this.playableCoordinates.contains(hexagon)) {
            HexCell cellClicked = this.hexGrid.getCell(this.hexClicked);
            Insect movedInsect = cellClicked.getTopInsect();
            Move move = new Move(movedInsect, this.hexClicked, hexagon);

            this.hexGrid.applyMove(move, this.currentPlayer);
            this.isInsectCellClicked = false;
            this.displayGame.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
            this.displayGame.getDisplayStack().updateStackClickState(isInsectCellClicked, hexClicked);
            this.playableCoordinates.clear();
            this.switchPlayer();
            this.history.addMove(move);
        } else {
            Log.addMessage("Déplacement impossible");
        }
    }

    @Override
    public void handleInsectPlaced(HexCoordinate hexagon) {
        if (this.playableCoordinates.contains(hexagon)) {

            Move move = new Move(this.insect, null, hexagon);
            this.hexGrid.applyMove(move, this.currentPlayer);

            //Modifier le compteur des boutons
            this.displayGame.getDisplayBankInsects().updateAllLabels();

            this.isInsectButtonClicked = false;
            this.playableCoordinates.clear();
            this.switchPlayer();
            this.history.addMove(move);
        } else {
            Log.addMessage("placement impossible !");
        }
    }

    @Override
    public void clicInsectButton(Class<? extends Insect> insectClass, Player player) {
        this.isInsectButtonClicked = true;
        this.isInsectCellClicked = false;

        this.playableCoordinates = this.generatePlayableCoordinates(insectClass, player);

        this.displayGame.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
        this.displayGame.repaint();
    }

    @Override
    public void cancelMove() {
        if (this.history.canCancel()) {
            this.playableCoordinates.clear();
            Move move = this.history.cancelMove();
            this.currentPlayer.decrementTurn();
            this.switchPlayerHistory();
            this.currentPlayer.decrementTurn();
            this.hexGrid.unapplyMove(move, this.currentPlayer);
            this.displayGame.getDisplayBankInsects().updateAllLabels();
            this.displayGame.getDisplayHexGrid().updateInsectClickState(false, this.hexClicked);
            this.displayGame.getDisplayStack().updateStackClickState(isInsectCellClicked, hexClicked);
            this.displayGame.repaint();
            if(this.checkCurrentPlayerIsIa())
            {
                this.cancelMove();
            }
        } else {
            Log.addMessage("no move to cancel");
        }
    }

    @Override
    public void redoMove() {
        if (this.history.canRedo()) {
            this.playableCoordinates.clear();
            Move move = this.history.redoMove();
            this.hexGrid.applyMove(move, this.currentPlayer);
            this.switchPlayerHistory();
            this.displayGame.getDisplayBankInsects().updateAllLabels();
            this.displayGame.getDisplayHexGrid().updateInsectClickState(false, this.hexClicked);
            this.displayGame.repaint();
            if(this.checkCurrentPlayerIsIa())
            {
                this.redoMove();
            }
        } else {
            Log.addMessage("no move to redo");
        }
    }
}