package Controleur;

import Modele.Move;
import Modele.HexCell;
import Modele.HexGrid;
import Modele.History;
import Modele.Insect.Bee;
import Modele.Insect.Beetle;
import Modele.Insect.Insect;
import Modele.Player;
import Modele.Ia.Ia;
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
    private Ia iaPlayer1;
    private Ia iaPlayer2;
    private Insect insect;

    public Game(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
        this.initPlayers();

        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
        this.playableCells = new ArrayList<>();
        this.history = new History();
        /////////A COMMENTER POUR PVP//////////////
        setPlayer(2, "IAFacile");
        //////////////////////////////////////////
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer(int player, String name)
    {
        switch(name)
        {
            case "IAFacile":
                if(player==1)
                {
                    this.iaPlayer1 = Ia.nouvelle(this, "Aleatoire", player1);
                }
                else
                {
                    this.iaPlayer2 = Ia.nouvelle(this, "Aleatoire", player2);
                }
                break;
    
    
            case "IADificile":
            //pas encore implementee//
                break;
    
            default:
                break;
        }
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
        display.repaint();
        checkLoser();
        this.currentPlayer.incrementTurn();
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
            tourIa();
        } else {
            this.currentPlayer = this.player1;
        }
        display.repaint();
    }

    private void tourIa()
    {
        if(this.player1==this.currentPlayer)
        {
            this.iaPlayer1.playMove();
        }
        else
        {
            this.iaPlayer2.playMove();
        }
        switchPlayer();
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
            Move move = new Move(movedInsect,hexClicked,hexagon);

            hexGrid.applyMove(move, currentPlayer);
            isInsectCellClicked = false;
            display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
            playableCells.clear();
            switchPlayer();
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
                        if(currentPlayer.isBeePlaced() || currentPlayer.getTurn() < 4){
                            Move move = new Move(this.insect,null, hexagon);
                            hexGrid.applyMove(move, currentPlayer);

                            //Modifier le compteur des boutons
                            display.getDisplayBankInsects().updateAllLabels();

                            isInsectButtonClicked = false;
                            playableCells.clear();
                            switchPlayer();
                            history.addMove(move);
                        }
                        else {
                            Log.addMessage("Vous devez placer l'abeille avant de placer d'autres insectes");
                        }
                    }
                    else {
                        Log.addMessage("Vous avez atteint le nombre maximum de pions de ce type");
                    }
                }
                else {
                    Log.addMessage("placement impossible !");
                }
            }
            else {
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

        ////////////BESOIN D UN CLIC POUR DEMARER PARTIESI L IA EST PREMIERE A JOUER//////////////////////////////////////
        if(this.iaPlayer1!= null && this.currentPlayer == this.player1)
        {

            tourIa();
        }
        else
        {
            if(this.iaPlayer2!= null && this.currentPlayer == this.player2)
            {
                tourIa();
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            else
            {
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
        }
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
        if (history.canCancel()) {
            Move move = history.cancelMove();
            this.currentPlayer.decrementTurn();
            switchPlayer();
            this.currentPlayer.decrementTurn();
            hexGrid.unapplyMove(move, currentPlayer);
            display.getDisplayBankInsects().updateAllLabels();
            display.repaint();
        }
        else{
            Log.addMessage("no move to cancel");
        }
    }

    @Override
    public void redoMove() {
        if(history.canRedo()){
            Move move = history.redoMove();
            hexGrid.applyMove(move, currentPlayer);
            switchPlayer();
            display.getDisplayBankInsects().updateAllLabels();
            display.repaint();
        }
        else{
            Log.addMessage("no move to redo");
        }
    }
}


//TODO : faire un bouton pour recentrer sur le jeu
//TODO : separer des trucs dans game, et verifier que si on enleve tout le graphique ça fonctionne toujours