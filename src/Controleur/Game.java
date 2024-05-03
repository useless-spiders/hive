package Controleur;

import Modele.HexGrid;
import Modele.Insect.Spider;
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

    public static void start(JFrame frame) {
        //TODO:afficher la config de la partie
        //DisplayConfigParty displayConfigParty = new DisplayConfigParty(frame);
        HexGrid hexGrid = new HexGrid();
        Display display = new Display(hexGrid);
        Game j = new Game(hexGrid, display);
        DisplayBankBug displayBankBug = new DisplayBankBug(frame);
        //pour l'instant il faut commenter la ligne suivant pour afficher les configs
        frame.add(display);
        display.addMouseListener(j);
    }

    public Game(HexGrid hexGrid, Display display) {
        this.hexGrid = hexGrid;
        this.display = display;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        double minDistance = Double.MAX_VALUE;
        int closestCol = 0;
        int closestRow = 0;

        for (int x = -10; x <= 10; x++) {  // Ajustez ces valeurs en fonction de la taille de votre grille
            for (int y = -10; y <= 10; y++) {
                Point center = HexMetrics.calculateHexCenter(x, y);
                double distance = Point.distance(mouseX, mouseY, center.x, center.y);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCol = x;
                    closestRow = y;
                }
            }
        }

        System.out.println("L'hexagone cliqué est: col=" + closestCol + ", row=" + closestRow);

        //remplissage de la grille
        hexGrid.addCell(closestCol, closestRow, new Spider());
        display.repaint();

    }
}
