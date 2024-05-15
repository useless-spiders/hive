package Controleur;

import Modele.Move;
import Modele.HexCell;
import Modele.HexGrid;
import Modele.History;
import Modele.Insect.Bee;
import Modele.Insect.Beetle;
import Modele.Insect.Insect;
import Modele.Player;
import Pattern.GameActionHandler;
import Structures.HexCoordinate;
import Structures.Log;
import Vue.Display;
import Structures.HexMetrics;

import javax.swing.*;
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
    private ArrayList<HexCoordinate> playableCells;
    private int lastX, lastY;
    private History history;
    private Insect insect;


    public Game(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
        this.initPlayers();

        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
        this.playableCells = new ArrayList<>();
        this.history = new History();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public HexGrid getGrid() {
        return this.hexGrid;
    }

    @Override
    public ArrayList<HexCoordinate> getPlayableCells() {
        return playableCells;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
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
        checkLoser();
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

        if (!isInsectCellClicked) { //On clique sur un insecte à déplacer
            if (insect.getPlayer().equals(currentPlayer)) {
                isInsectCellClicked = true;
                hexClicked = hexagon;
                playableCells = insect.getPossibleMovesCells(hexClicked, hexGrid);
                // rendre transparente la case
                display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
            } else {
                Log.addMessage("Ce pion ne vous appartient pas");
            }

        } else {
            HexCell cellClicked = hexGrid.getCell(hexClicked);
            if (cellClicked.getTopInsect().getClass() == Beetle.class && !hexagon.equals(hexClicked)) { //On clique sur un insecte cible d'un scarabée
                handleInsectMoved(hexagon);

            } else { //On clique sur un insecte déjà sélectionné
                isInsectCellClicked = false;
                display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
                this.playableCells.clear();
            }
        }
    }

    private void handleInsectMoved(HexCoordinate hexagon) {
        if (playableCells.contains(hexagon)) {
            HexCell cellClicked = hexGrid.getCell(hexClicked);
            Insect movedInsect = cellClicked.getTopInsect();

            cellClicked.removeTopInsect();
            if (cellClicked.getInsects().isEmpty()) {
                hexGrid.removeCell(hexClicked);
            }

            if (hexGrid.getCell(hexagon) != null) {
                hexGrid.getCell(hexagon).addInsect(movedInsect);
            } else {
                hexGrid.addCell(hexagon, movedInsect);
            }

            isInsectCellClicked = false;
            display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
            playableCells.clear();
            switchPlayer();

            // Add the move to the history
            Move move = new Move(movedInsect, hexClicked, hexagon);
            history.addMove(move);
        } else {
            Log.addMessage("Déplacement impossible");
        }
    }

    private void handleInsectPlaced(HexCoordinate hexagon) {
        if(this.insect != null){ //Clic sur une case vide sans insect selectionné
            if (this.insect.getPlayer().equals(currentPlayer)) { // Vérifie si le joueur actuel est le propriétaire de l'insecte

                if (this.insect.isPlacable(hexagon, hexGrid)) {
                    if (currentPlayer.canAddInsect(this.insect.getClass())) { // Vérifie si le joueur actuel peut ajouter un insecte
                        if (this.insect instanceof Bee) {
                            currentPlayer.setBeePlaced(true);
                        }
                        if (currentPlayer.isBeePlaced() || currentPlayer.getTurn() < 4) { // Vérifie que la reine a été placé durant les 4 premiers tours
                            currentPlayer.playInsect(this.insect.getClass());
                            hexGrid.addCell(hexagon, this.insect);

                            //Modifier le compteur des boutons
                            JLabel label = display.getDisplayBankInsects().getLabel();
                            label.setText(String.valueOf(this.currentPlayer.getInsectCount(insect.getClass())));

                            isInsectButtonClicked = false;
                            playableCells.clear();
                            switchPlayer();

                            // Add the placement to the history
                            Move move = new Move(this.insect, null, hexagon);
                            history.addMove(move);
                        } else {
                            Log.addMessage("Vous devez placer l'abeille avant de placer d'autres insectes");
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

        ArrayList<HexCoordinate> playableCells = getPlayableCells();

        HexCoordinate newHoverCell = HexMetrics.pixelToHex(mouseX, mouseY);
        if (!newHoverCell.equals(hoverCell) && playableCells.contains(newHoverCell)) {
            hoverCell = newHoverCell;
            display.getDisplayPlayableHex().updateHoverCell(hoverCell);

            display.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        lastX = e.getX();
        lastY = e.getY();
        int mouseX = e.getX() - HexMetrics.getViewOffsetX();
        int mouseY = e.getY() - HexMetrics.getViewOffsetY();

        HexCoordinate hexagon = HexMetrics.pixelToHex(mouseX, mouseY);
        HexCell cell = hexGrid.getCell(hexagon);
        if (cell != null) { //on clique sur une case existante pour la déplacer ou bien pour être un insecte cible du scarabée
            handleCellClicked(cell, hexagon);
        } else if (isInsectCellClicked) { //on clique sur une case vide pour déplacer une case sélectionnée
            handleInsectMoved(hexagon);
        } else if (isInsectButtonClicked) { //on clique sur une case vide pour déposer une nouvelle case
            handleInsectPlaced(hexagon);
        }

        display.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - lastX;
        int dy = e.getY() - lastY;
        HexMetrics.updateViewPosition(dx, dy);
        lastX = e.getX();
        lastY = e.getY();

        display.repaint();
    }

    @Override
    public void clicInsectButton(Class<? extends Insect> insectClass, Player player) {

        this.isInsectButtonClicked = true;
        this.isInsectCellClicked = false;
        this.playableCells.clear();
        if(player == this.currentPlayer){
            this.insect = this.currentPlayer.getInsect(insectClass);
            if(this.insect == null){
                Log.addMessage("Vous avez atteint le nombre maximum de pions de ce type");
            }
        }
        else{
            Log.addMessage("Pas le bon joueur !");
        }

        if (player.equals(currentPlayer) && this.currentPlayer.canAddInsect(insectClass)) {
            if (this.currentPlayer.getTurn() <= 1 && hexGrid.getGrid().isEmpty()) {
                Log.addMessage(" debut : tour " + this.currentPlayer.getTurn());
                this.playableCells.clear();
            } else if (this.currentPlayer.getTurn() <= 1 && !hexGrid.getGrid().isEmpty()) {
                this.playableCells = this.currentPlayer.getInsect(insectClass).getPossibleInsertionCoordinatesT1(hexGrid);
            } else {
                Log.addMessage("suite : tour " + this.currentPlayer.getTurn());
                this.playableCells = this.currentPlayer.getInsect(insectClass).getPossibleInsertionCoordinates(hexGrid);
            }
        }
        display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
        display.repaint();
    }

    @Override
    public void cancelMove() {
        Move move = history.cancelMove();
        if (move != null) {
            this.currentPlayer.decrementTurn();
            switchPlayer();
            this.currentPlayer.decrementTurn();

            HexCoordinate from = move.getPreviousCoor();
            HexCoordinate to = move.getNewCoor();
            Insect insect = move.getInsect();

            if (insect instanceof Bee) {
                currentPlayer.setBeePlaced(false);
            }

            if(hexGrid.getCell(to) != null){
                hexGrid.getCell(to).removeTopInsect();
            }
            if(hexGrid.getCell(to) == null || hexGrid.getCell(to).getInsects().isEmpty()){
                hexGrid.removeCell(to);
            }

            if (from != null) {
                if (hexGrid.getCell(from) != null) {
                    hexGrid.getCell(from).addInsect(insect);
                } else {
                    hexGrid.addCell(from, insect);
                }
            }

            if (from == null) {
                this.currentPlayer.unplayInsect(insect);
            }
            display.repaint();
        }
    }

    @Override
    public void redoMove() {
        Move move = history.redoMove();
        if (move != null) {
            HexCoordinate from = move.getPreviousCoor();
            HexCoordinate to = move.getNewCoor();
            Insect insect = move.getInsect();

            if (insect instanceof Bee) {
                currentPlayer.setBeePlaced(true);
            }

            if (from != null) {
                if(hexGrid.getCell(from) != null){
                    hexGrid.getCell(from).removeTopInsect();
                }
                if(hexGrid.getCell(from) == null || hexGrid.getCell(from).getInsects().isEmpty()){
                    hexGrid.removeCell(from);
                }
            }

            if (hexGrid.getCell(to) != null) {
                hexGrid.getCell(to).addInsect(insect);
            } else {
                hexGrid.addCell(to, insect);
            }

            if (from == null) {
                this.currentPlayer.playInsect(insect.getClass());
            }
            switchPlayer();
            display.repaint();
        }
    }
}


//TODO : faire un bouton pour recentrer sur le jeu
//TODO : separer des trucs dans game, et verifier que si on enleve tout le graphique ça fonctionne toujours