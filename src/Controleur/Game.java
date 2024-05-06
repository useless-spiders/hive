package Controleur;

import Modele.HexCell;
import Modele.HexGrid;
import Modele.Insect.Insect;
import Modele.Player;
import Pattern.GameActionHandler;
import Structures.HexCoordinate;
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


    public void setDisplay(Display display) {
        this.display = display;
    }

    private void switchPlayer() {
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
        System.out.println(currentPlayer.getColor() + " player's turn");
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
            System.out.println("Ce pion ne vous appartient pas");
        }
    }

    private void handleInsectMoved(HexCoordinate hexagon) {
        if(playableCells.contains(hexagon)) {
            HexCell cell = hexGrid.getCell(hexClicked.getX(), hexClicked.getY());
            hexGrid.removeCell(hexClicked.getX(), hexClicked.getY());
            hexGrid.addCell(hexagon.getX(), hexagon.getY(), cell.getTopInsect());
            isInsectCellClicked = false;
            playableCells.clear();
            switchPlayer();
        } else {
            System.out.println("Déplacement impossible");
        }
    }

    private void handleInsectPlaced(HexCoordinate hexagon) {
        if (this.insect.getPlayer().equals(currentPlayer)) { // Vérifie si le joueur actuel est le propriétaire de l'insecte
            if(currentPlayer.canAddInsect(this.insect)) { // Vérifie si le joueur actuel peut ajouter un insecte
                hexGrid.addCell(hexagon.getX(), hexagon.getY(), this.insect);
                isInsectButtonClicked = false;
                switchPlayer();
            } else {
                System.out.println("Vous avez atteint le nombre maximum de pions de ce type");
            }
        } else {
            System.out.println("Ce n'est pas votre tour");
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
