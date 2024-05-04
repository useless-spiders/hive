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
        this.player1 = new Player();
        this.player2 = new Player();

        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;

        // Randomly select the starting player
        Random random = new Random();
        this.currentPlayer = random.nextBoolean() ? player1 : player2;
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

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        HexCoordinate hexagon = findHex(mouseX, mouseY);

        System.out.println(this.insect);

        // Si on sélectionne un hexagone sur la grille
        if (hexGrid.getCell(hexagon.getX(), hexagon.getY()) != null) {
            clicInsectCell();
            hexClicked = hexagon;
            //illuminer cet hexagone sélectionné

            // si on veut DEPLACER un pion à l'emplacement cliqué
        } else if (isInsectCellClicked) {
            //Désilluminer l'hexagone
            //Illuminer les cases possibles
            HexCell cell = hexGrid.getCell(hexClicked.getX(), hexClicked.getY());
            hexGrid.removeCell(hexClicked.getX(), hexClicked.getY());
            hexGrid.addCell(hexagon.getX(), hexagon.getY(), cell.getTopInsect());
            isInsectCellClicked = false;

            // si on veut DEPOSER un nouveau pion à l'emplacement cliqué
        } else if (isInsectButtonClicked) {
            hexGrid.addCell(hexagon.getX(), hexagon.getY(), this.insect);
            isInsectButtonClicked = false;
            //Désilluminer les cases possibles
        }

        // Nouvel affichage de la grille
        display.repaint();
    }

    public void clicInsectCell() {
        isInsectCellClicked = true;
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
    public void clicInsectButton(Insect insect) {
        this.isInsectButtonClicked = true;
        this.insect = insect;
    }
}
