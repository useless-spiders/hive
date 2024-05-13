package Controleur;

import Modele.Action;
import Modele.HexCell;
import Modele.HexGrid;
import Modele.History;
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
    private Insect insect;
    private ArrayList<HexCoordinate> playableCells;
    private int lastX, lastY;
    private History history;

    public static void start(JFrame frame) {
        HexGrid hexGrid = new HexGrid();
        Game g = new Game(hexGrid);
        Display display = new Display(hexGrid, frame, g);

        g.setDisplay(display);
        frame.add(display);
        display.addMouseListener(g);
        display.addMouseMotionListener(g);
    }

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
            Insect movedInsect = cell.getTopInsect();
            hexGrid.removeCell(hexClicked.getX(), hexClicked.getY());
            hexGrid.addCell(hexagon.getX(), hexagon.getY(), movedInsect);
            isInsectCellClicked = false;
            playableCells.clear();
            switchPlayer();

            // Add the move to the history
            Modele.Action action = new Action(movedInsect, hexClicked, hexagon);
            history.addAction(action);
        } else {
            Log.addMessage("Déplacement impossible");
        }
    }

    private void handleInsectPlaced(HexCoordinate hexagon) {
        if (this.insect.getPlayer().equals(currentPlayer)) { // Vérifie si le joueur actuel est le propriétaire de l'insecte

            if (this.insect.isPlacable(hexagon, hexGrid)) {
                if (currentPlayer.canAddInsect(this.insect)) { // Vérifie si le joueur actuel peut ajouter un insecte
                    if (this.insect instanceof Bee) {
                        currentPlayer.setBeePlaced(true);
                    }
                    if (currentPlayer.isBeePlaced() || currentPlayer.getTurn() < 4) { // Vérifie que la reine a été placé durant les 4 premiers tours
                        currentPlayer.addInsect(this.insect);
                        hexGrid.addCell(hexagon.getX(), hexagon.getY(), this.insect);
                        isInsectButtonClicked = false;
                        playableCells.clear();
                        switchPlayer();

                        // Add the placement to the history
                        Action action = new Action(this.insect, null, hexagon);
                        history.addAction(action);
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

    private HexCoordinate findHex(int mouseX, int mouseY) {
        double minDistance = Double.MAX_VALUE;
        HexCoordinate closestHex = null;

        for (int x = -22; x <= 22; x++) {
            for (int y = -22; y <= 22; y++) {
                Point center = HexMetrics.calculateHexCenter(x, y);
                double distance = Point.distance(mouseX, mouseY, center.x, center.y);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestHex = new HexCoordinate(x, y);
                }
            }
        }
        return closestHex;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX() - HexMetrics.getViewOffsetX();
        int mouseY = e.getY() - HexMetrics.getViewOffsetY();

        ArrayList<HexCoordinate> playableCells = getPlayableCells();

        HexCoordinate newHoverCell = findHex(mouseX, mouseY);
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
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - lastX;
        int dy = e.getY() - lastY;
        HexMetrics.updateViewPosition(dx, dy);
        lastX = e.getX();
        lastY = e.getY();

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
        if(insect.getPlayer().equals(currentPlayer) && this.currentPlayer.canAddInsect(insect)){
            if(this.currentPlayer.getTurn() <= 1 && hexGrid.getGrid().isEmpty())
            {
                Log.addMessage(" debut : tour " + this.currentPlayer.getTurn());
                this.playableCells.clear();
            }
            else if(this.currentPlayer.getTurn() <= 1 && !hexGrid.getGrid().isEmpty()){
                this.playableCells = insect.getPossibleInsertionCellT1(hexGrid);
            }
            else
            {
                Log.addMessage("suite : tour " + this.currentPlayer.getTurn());
                //MODIF
                this.playableCells = insect.getPossibleInsertionCells(hexGrid);
            }
        }
        display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
        display.repaint();
    }

    @Override
    public ArrayList<HexCoordinate> getPlayableCells() {
        return playableCells;
    }

    @Override
    public void cancelAction() {
        Action action = history.cancelAction();
        if (action != null) {
            this.currentPlayer.decrementTurn();
            switchPlayer();
            this.currentPlayer.decrementTurn();

            HexCoordinate from = action.getPreviousCoor();
            HexCoordinate to = action.getNewCoor();
            Insect insect = action.getInsect();

            if (insect instanceof Bee) {
                currentPlayer.setBeePlaced(false);
            }
            hexGrid.removeCell(to.getX(), to.getY());
            this.currentPlayer.removeInsect(insect);
            if (from != null) {
                hexGrid.addCell(from.getX(), from.getY(), insect);
                this.currentPlayer.addInsect(insect);
            }
            display.repaint();
        }
    }

    @Override
    public void redoAction() {
        Action action = history.redoAction();
        if (action != null) {
            HexCoordinate from = action.getPreviousCoor();
            HexCoordinate to = action.getNewCoor();
            Insect insect = action.getInsect();

            if (insect instanceof Bee) {
                currentPlayer.setBeePlaced(true);
            }
            hexGrid.addCell(to.getX(), to.getY(), insect);
            this.currentPlayer.addInsect(insect);
            if (from != null) {
                hexGrid.removeCell(from.getX(), from.getY());
                this.currentPlayer.removeInsect(insect);
            }
            switchPlayer();
            display.repaint();
        }
    }
}
