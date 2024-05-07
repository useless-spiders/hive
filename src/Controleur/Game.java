package Controleur;

import Modele.HexCell;
import Modele.HexGrid;
import Modele.Insect.Bee;
import Modele.Insect.Insect;
import Modele.Player;
import Pattern.GameActionHandler;
import Structures.HexCoordinate;
import Structures.Log;
import Vue.Display;
import Vue.HexMetrics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game extends MouseAdapter implements GameActionHandler {
    private HexGrid hexGrid;
    private Display display;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean isInsectButtonClicked;
    private boolean isInsectCellClicked;
    private HexCoordinate hexClicked;
    private Insect insect;
    private ArrayList<HexCoordinate> playableCells = new ArrayList<>();

    public static void start(JFrame frame) {
        HexGrid hexGrid = new HexGrid();
        Game g = new Game(hexGrid);
        Display display = new Display(hexGrid, frame, g);

        g.setDisplay(display);
        frame.add(display);
        display.addMouseListener(g);
    }

    public Game(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
        this.initPlayers();

        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
    }

    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    private void switchPlayer() {
        this.currentPlayer.incrementTurn();
        Log.addMessage("tour " + this.currentPlayer.getTurn());
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
        } else {
            this.currentPlayer = this.player1;
        }
    }

    private void initPlayers() {
        this.player1 = new Player("white");
        this.player2 = new Player("black");

        Random random = new Random();
        this.currentPlayer = random.nextBoolean() ? player1 : player2;
    }

    private void handleCellClicked(HexCell cell, HexCoordinate hexagon) {
        Insect insect = cell.getTopInsect();
        if (insect.getPlayer().equals(currentPlayer)) {
            if (isInsectCellClicked == false) {
                isInsectCellClicked = true;
                hexClicked = hexagon;
                playableCells = insect.getPossibleMovesCells(hexClicked.getX(), hexClicked.getY(), hexGrid);
                // rendre transparente la case
                display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
            } else {
                isInsectCellClicked = false;
                display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
                this.playableCells.clear();
            }
        } else {
            Log.addMessage("Ce pion ne vous appartient pas");
        }
    }

    private void handleInsectMoved(HexCoordinate hexagon) {
        if (playableCells.contains(hexagon)) {
            HexCell cell = hexGrid.getCell(hexClicked.getX(), hexClicked.getY());
            hexGrid.removeCell(hexClicked.getX(), hexClicked.getY());
            hexGrid.addCell(hexagon.getX(), hexagon.getY(), cell.getTopInsect());
            isInsectCellClicked = false;
            playableCells.clear();
            switchPlayer();
        } else {
            Log.addMessage("Déplacement impossible");
        }
    }

    private void handleInsectPlaced(HexCoordinate hexagon) {
        if (this.insect.getPlayer().equals(currentPlayer)) { // Vérifie si le joueur actuel est le propriétaire de l'insecte

            if(this.insect.isPlacable(hexagon, hexGrid))
            {
                if (currentPlayer.canAddInsect(this.insect)) { // Vérifie si le joueur actuel peut ajouter un insecte
                    if(this.insect instanceof Bee) {
                        currentPlayer.setBeePlaced(true);
                    }
                    if (currentPlayer.isBeePlaced() || currentPlayer.getTurn() < 4) { // Vérifie que la reine a été placé durant les 4 premiers tours
                        hexGrid.addCell(hexagon.getX(), hexagon.getY(), this.insect);
                        isInsectButtonClicked = false;
                        switchPlayer();
                    } else {
                        Log.addMessage("Vous devez placer l'abeille avant de placer d'autres insectes");
                    }
                } else {
                    Log.addMessage("Vous avez atteint le nombre maximum de pions de ce type");
                }
            }
            else
            {
                Log.addMessage("placement impossible !");
            }
        } else {
            Log.addMessage("Ce n'est pas votre tour");
        }
    }

    private HexCoordinate findHex(int mouseX, int mouseY) {
        double minDistance = Double.MAX_VALUE;
        int closestRow = 0;
        int closestCol = 0;

        for (int x = -22; x <= 22; x++) {  // Ajustez ces valeurs en fonction de la taille de votre grille
            for (int y = -22; y <= 22; y++) {
                Point center = HexMetrics.calculateHexCenter(x, y);
                double distance = Point.distance(mouseX, mouseY, center.x, center.y);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestRow = x;
                    closestCol = y;
                }
            }
        }

        //System.out.println("L'hexagone cliqué est: row=" + closestRow + ", col=" + closestCol);
        return new HexCoordinate(closestRow, closestCol);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        HexCoordinate hexagon = findHex(mouseX, mouseY);
        HexCell cell = hexGrid.getCell(hexagon.getX(), hexagon.getY());

        if (cell != null) { //on clique sur une case existante
            handleCellClicked(cell, hexagon);
        } else if (isInsectCellClicked) { //on clique sur une case vide pour déplacer une case sélectionnée
            handleInsectMoved(hexagon);
        } else if (isInsectButtonClicked) { //on clique sur une case vide pour déposer une nouvelle case
            handleInsectPlaced(hexagon);
        }

        display.repaint();
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void clicInsectButton(Insect insect) {
        this.isInsectButtonClicked = true;
        this.isInsectCellClicked = false;
        this.insect = insect;
        this.playableCells.clear();
        display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
        display.repaint();
    }

    @Override
    public ArrayList<HexCoordinate> getPlayableCells() {
        return playableCells;
    }
}
