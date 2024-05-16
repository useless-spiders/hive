package Controller;

import Model.Move;
import Model.HexCell;
import Model.HexGrid;
import Model.History;
import Model.Insect.Beetle;
import Model.Insect.Insect;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.Log;
import View.Display;
import Structure.HexMetrics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class Game extends MouseAdapter implements GameActionHandler, MouseMotionListener {
    private HexGrid hexGrid;
    private Display display;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean isInsectButtonClicked;
    private boolean isInsectCellClicked;
    private HexCoordinate hexClicked;
    private HexCoordinate hoverCell;
    private ArrayList<HexCoordinate> playableCoordinates;
    private int lastX, lastY;
    private History history;
    private Insect insect;


    public Game(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
        this.initPlayers();

        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
        this.playableCoordinates = new ArrayList<>();
        this.history = new History();
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

    @Override
    public ArrayList<HexCoordinate> getPlayableCoordinates() {
        return this.playableCoordinates;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    private void checkLoser() {
        boolean lPlayer1 = this.hexGrid.checkLoser(player1);
        boolean lPlayer2 = this.hexGrid.checkLoser(player2);
        if (lPlayer1 && lPlayer2) {
            Log.addMessage("Egalité !");
        } else {
            if (lPlayer1) {
                Log.addMessage("Le joueur " + player1.getColor() + " a perdu !");
            } else if (lPlayer2) {
                Log.addMessage("Le joueur " + player2.getColor() + " a perdu !");
            }
        }
    }

    private void switchPlayer() {
        this.checkLoser();
        this.currentPlayer.incrementTurn();
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
        } else {
            this.currentPlayer = this.player1;
        }
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
                if(this.playableCoordinates.isEmpty() && !this.currentPlayer.isBeePlaced()){
                    Log.addMessage("Aucun déplacement autorisé car l'abeille n'est pas sur le plateau");
                }
                // rendre transparente la case
                this.display.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
            } else {
                Log.addMessage("Ce pion ne vous appartient pas");
            }

        } else {
            HexCell cellClicked = this.hexGrid.getCell(this.hexClicked);
            if (cellClicked.getTopInsect().getClass() == Beetle.class && !hexagon.equals(this.hexClicked)) { //On clique sur un insecte cible d'un scarabée
                this.handleInsectMoved(hexagon);
            } else { //On clique sur un insecte déjà sélectionné
                this.isInsectCellClicked = false;
                this.display.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
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
            this.display.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
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
                            Move move = new Move(this.insect, null, hexagon);
                            this.hexGrid.applyMove(move, this.currentPlayer);

                            //Modifier le compteur des boutons
                            this.display.getDisplayBankInsects().updateAllLabels();

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

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX() - HexMetrics.getViewOffsetX();
        int mouseY = e.getY() - HexMetrics.getViewOffsetY();

        HexCoordinate newHoverCell = HexMetrics.pixelToHex(mouseX, mouseY);
        if (!newHoverCell.equals(this.hoverCell) && this.playableCoordinates.contains(newHoverCell)) {
            this.hoverCell = newHoverCell;
            this.display.getDisplayPlayableHex().updateHoverCell(this.hoverCell);

            this.display.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        this.lastX = e.getX();
        this.lastY = e.getY();
        int mouseX = e.getX() - HexMetrics.getViewOffsetX();
        int mouseY = e.getY() - HexMetrics.getViewOffsetY();

        HexCoordinate hexagon = HexMetrics.pixelToHex(mouseX, mouseY);
        HexCell cell = this.hexGrid.getCell(hexagon);
        if (cell != null) { //on clique sur une case existante pour la déplacer ou bien pour être un insecte cible du scarabée
            this.handleCellClicked(cell, hexagon);
        } else if (this.isInsectCellClicked) { //on clique sur une case vide pour déplacer une case sélectionnée
            this.handleInsectMoved(hexagon);
        } else if (this.isInsectButtonClicked) { //on clique sur une case vide pour déposer une nouvelle case
            this.handleInsectPlaced(hexagon);
        }

        this.display.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - this.lastX;
        int dy = e.getY() - this.lastY;
        HexMetrics.updateViewPosition(dx, dy);
        this.lastX = e.getX();
        this.lastY = e.getY();

        this.display.repaint();
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
        this.display.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
        this.display.repaint();
    }

    @Override
    public void cancelMove() {
        if (this.history.canCancel()) {
            this.playableCoordinates.clear();
            Move move = this.history.cancelMove();
            this.currentPlayer.decrementTurn();
            this.switchPlayer();
            this.currentPlayer.decrementTurn();
            this.hexGrid.unapplyMove(move, this.currentPlayer);
            this.display.getDisplayBankInsects().updateAllLabels();
            this.display.getDisplayHexGrid().updateInsectClickState(false, this.hexClicked);
            this.display.repaint();
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
            this.display.getDisplayBankInsects().updateAllLabels();
            this.display.getDisplayHexGrid().updateInsectClickState(false, this.hexClicked);
            this.display.repaint();
        } else {
            Log.addMessage("no move to redo");
        }
    }
}


//TODO : faire un bouton pour recentrer sur le jeu
//TODO : separer des trucs dans game, et verifier que si on enleve tout le graphique ça fonctionne toujours