package Controleur;

import Modele.HexGrid;
import Modele.Insect.Spider;
import Structures.HexCoordinate;
import Vue.Display;
import Vue.DisplayBankBug;
import Vue.DisplayConfigParty;
import Vue.HexMetrics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends MouseAdapter {
    private HexGrid hexGrid;
    private Display display;
    private boolean isBugButtonClicked;
    private boolean isBugCellClicked;
    private HexCoordinate hexClicked;

    public static void start(JFrame frame) {
        HexGrid hexGrid = new HexGrid();
        Display display = new Display(hexGrid, frame);
        Game j = new Game(hexGrid, display);

        //On devra mettre ca dans Display, mais Display ne peut pas prendre Game en argument donc ici pour le moment
        DisplayBankBug displayBankBug = new DisplayBankBug(frame, j);

        frame.add(display);
        display.addMouseListener(j);
    }

    public Game(HexGrid hexGrid, Display display) {
        this.hexGrid = hexGrid;
        this.display = display;
        this.isBugButtonClicked = false;
        this.isBugCellClicked = false;
        this.hexClicked = null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        HexCoordinate hexagon = findHex(mouseX, mouseY);


        // Si on sélectionne un hexagone sur la grille
        if (hexGrid.getCell(hexagon.getX(), hexagon.getY()) != null) {
            clicBugCell();
            hexClicked = hexagon;
            //illuminer cet hexagone sélectionné

            // si on veut DEPLACER un pion à l'emplacement cliqué
        } else if (isBugCellClicked == true) {
            //Désilluminer l'hexagone
            //Illuminer les cases possibles
            hexGrid.removeCell(hexClicked.getX(), hexClicked.getY());
            hexGrid.addCell(hexagon.getX(), hexagon.getY(), new Spider()); //MODIFIER SPIDER
            isBugCellClicked = false;

            // si on veut DEPOSER un nouveau pion à l'emplacement cliqué
        } else if (isBugButtonClicked == true) {
            hexGrid.addCell(hexagon.getX(), hexagon.getY(), new Spider()); //MODIFIER SPIDER
            isBugButtonClicked = false;
            //Désilluminer les cases possibles
        }

        // Nouvel affichage de la grille
        display.repaint();
    }

    public void clicBugButton() {
        isBugButtonClicked = true;
    }

    public void clicBugCell() {
        isBugCellClicked = true;
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
}
