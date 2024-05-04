package Controleur;

import Modele.HexCell;
import Modele.HexGrid;
import Modele.Insect.Insect;
import Modele.Player;
import Structures.HexCoordinate;
import Vue.Display;
import Vue.HexMetrics;
import Pattern.InsectButtonListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Game extends MouseAdapter implements InsectButtonListener {
    private HexGrid hexGrid;
    private Display display;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean isInsectButtonClicked;
    private boolean isInsectCellClicked;
    private HexCoordinate hexClicked;
    private Insect insect;

    public static void start(JFrame frame) {
        HexGrid hexGrid = new HexGrid();
        Game g = new Game(hexGrid, null);
        Display display = new Display(hexGrid, frame, g);

        g.setDisplay(display);
        frame.add(display);
        display.addMouseListener(g);
    }

    public Game(HexGrid hexGrid, Display display) {
        this.hexGrid = hexGrid;
        this.display = display;
        this.initPlayers();

        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
    }


    public void setDisplay(Display display) {
        this.display = display;
    }

    public void switchPlayer() {
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
        if (cell.getTopInsect().getPlayer().equals(currentPlayer)) {
            isInsectCellClicked = true;
            hexClicked = hexagon;
        } else {
            System.out.println("Ce pion ne vous appartient pas");
        }
    }

    private void handleInsectMoved(HexCoordinate hexagon) {
        HexCell cell = hexGrid.getCell(hexClicked.getX(), hexClicked.getY());
        hexGrid.removeCell(hexClicked.getX(), hexClicked.getY());
        hexGrid.addCell(hexagon.getX(), hexagon.getY(), cell.getTopInsect());
        isInsectCellClicked = false;
        switchPlayer();
    }

    private void handleInsectPlaced(HexCoordinate hexagon) {
        if (this.insect.getPlayer().equals(currentPlayer)) {
            hexGrid.addCell(hexagon.getX(), hexagon.getY(), this.insect);
            isInsectButtonClicked = false;
            switchPlayer();
        } else {
            System.out.println("Ce n'est pas votre tour");
        }
    }

    public HexCoordinate findHex(int mouseX, int mouseY) {
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

        if (cell != null) {
            handleCellClicked(cell, hexagon);
        } else if (isInsectCellClicked) {
            handleInsectMoved(hexagon);
        } else if (isInsectButtonClicked) {
            handleInsectPlaced(hexagon);
        }

        display.repaint();
    }

    @Override
    public void clicInsectButton(Insect insect) {
        this.isInsectButtonClicked = true;
        this.isInsectCellClicked = false;
        this.insect = insect;
    }
}
