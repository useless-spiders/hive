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
import Structure.ViewMetrics;
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
    private HexCoordinate hoverCell;
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

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

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
    public ArrayList<HexCoordinate> getPlayableCoordinates() {
        return this.playableCoordinates;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setDisplayGame(DisplayGame displayGame) {
        this.displayGame = displayGame;
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
            pageManager.getMainDisplay().getDisplayWin().updateWinner(winner);
            pageManager.gameAndWin();
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
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
        } else {
            this.currentPlayer = this.player1;
        }
        this.displayGame.repaint();
    }

    public void delay(long time) {
        this.delay.start();
    }

    private void aiTurn() {
        this.displayGame.repaint();
        delay(1000);
        this.displayGame.repaint();
    }

    private void initPlayers() {
        this.player1 = new Player("white", "Inspecteur blanco");
        this.player2 = new Player("black", "Barbe noir");

        Random random = new Random();
        this.currentPlayer = random.nextBoolean() ? player1 : player2;
    }

    private void handleCellClicked(HexCell cell, HexCoordinate hexagon) { //Clic sur un insecte du plateau
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

    private void handleInsectMoved(HexCoordinate hexagon) {
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

    private void handleInsectPlaced(HexCoordinate hexagon) {
        if (this.insect != null) { //Clic sur une case vide sans insect selectionné
            if (this.insect.getPlayer().equals(this.currentPlayer)) { // Vérifie si le joueur actuel est le propriétaire de l'insecte
                if (this.insect.isPlacable(hexagon, hexGrid)) {
                    if (this.currentPlayer.canAddInsect(this.insect.getClass())) { // Vérifie si le joueur actuel peut ajouter un insecte
                        if (this.currentPlayer.checkBeePlacement(this.insect)) {

                            // Force le premier placement a être au centre
                            if(this.currentPlayer.getTurn() == 1 && this.getGrid().getGrid().isEmpty()){
                                hexagon = HexMetrics.hexCenterCoordinate(this.displayGame.getWidth(), this.displayGame.getHeight());
                            }

                            Move move = new Move(this.insect, null, hexagon);
                            this.hexGrid.applyMove(move, this.currentPlayer);

                            //Modifier le compteur des boutons
                            this.displayGame.getDisplayBankInsects().updateAllLabels();

                            this.isInsectButtonClicked = false;
                            this.playableCoordinates.clear();
                            this.switchPlayer();
                            this.history.addMove(move);
                        } else {
                            Log.addMessage("Vous devez placer l'abeille au 4e tour");
                        }
                    } else {
                        Log.addMessage("Vous avez atteint le nombre maximum de pions de ce type");
                    }
                } else {
                    Log.addMessage("placement impossible !");
                }
            } else {
                Log.addMessage("Ce n'est pas votre tour");
            }
        }
    }

    public void mouseMoved(int x, int y) {
        HexCoordinate newHoverCell = HexMetrics.pixelToHex(x, y);
        if (!newHoverCell.equals(this.hoverCell) && this.playableCoordinates.contains(newHoverCell)) {
            this.hoverCell = newHoverCell;
            this.displayGame.getDisplayPlayableHex().updateHoverCell(this.hoverCell);

            this.displayGame.repaint();
        }
    }

    public void mousePressed(int x, int y) {
        if(this.currentPlayer.getTurn() <= 1 && (this.iaPlayer1 != null || this.iaPlayer2 != null))
        {
            this.aiTurn();
        }
        HexCoordinate hexagon = HexMetrics.pixelToHex(x, y);
        HexCell cell = this.hexGrid.getCell(hexagon);

        if (cell != null) { //on clique sur une case existante pour la déplacer ou bien pour être un insecte cible du scarabée
            this.handleCellClicked(cell, hexagon);
        } else if (this.isInsectCellClicked) { //on clique sur une case vide pour déplacer une case sélectionnée
            this.handleInsectMoved(hexagon);
        } else if (this.isInsectButtonClicked) { //on clique sur une case vide pour déposer une nouvelle case
            this.handleInsectPlaced(hexagon);
        }

        this.displayGame.repaint();
    }

    public void mouseDragged(int x, int y) {
        ViewMetrics.updateViewPosition(x, y);
        this.displayGame.repaint();
    }

    @Override
    public void clicInsectButton(Class<? extends Insect> insectClass, Player player) {

        this.isInsectButtonClicked = true;
        this.isInsectCellClicked = false;
        this.playableCoordinates.clear();
        if (player == this.currentPlayer) {
            this.insect = this.currentPlayer.getInsect(insectClass);
            if (this.insect == null) {
                Log.addMessage("Vous avez atteint le nombre maximum de pions de ce type");
            }
        } else {
            Log.addMessage("Pas le bon joueur !");
        }

        if (player.equals(this.currentPlayer) && this.currentPlayer.canAddInsect(insectClass)) {
            if (this.currentPlayer.getTurn() <= 1 && this.hexGrid.getGrid().isEmpty()) {
                Log.addMessage(" debut : tour " + this.currentPlayer.getTurn());
                this.playableCoordinates.clear();
            } else if (this.currentPlayer.getTurn() <= 1 && !this.hexGrid.getGrid().isEmpty()) {
                this.playableCoordinates = this.currentPlayer.getInsect(insectClass).getPossibleInsertionCoordinatesT1(this.hexGrid);
            } else if (!this.currentPlayer.checkBeePlacement(this.insect)) {
                this.playableCoordinates.clear();
            } else {
                Log.addMessage("suite : tour " + this.currentPlayer.getTurn());
                this.playableCoordinates = this.currentPlayer.getInsect(insectClass).getPossibleInsertionCoordinates(this.hexGrid);
            }
        }
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
            this.switchPlayer();
            this.displayGame.getDisplayBankInsects().updateAllLabels();
            this.displayGame.getDisplayHexGrid().updateInsectClickState(false, this.hexClicked);
            this.displayGame.repaint();
        } else {
            Log.addMessage("no move to redo");
        }
    }
}


//TODO : faire un bouton pour recentrer sur le jeu
//TODO : separer des trucs dans game, et verifier que si on enleve tout le graphique ça fonctionne toujours