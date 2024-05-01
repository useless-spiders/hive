package Controleur;

import Modele.HexGrid;
import Vue.Display;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends MouseAdapter {
    private HexGrid grid;
    private Display display;

    public static void start(JFrame frame) {
        HexGrid grid = new HexGrid();
        Display display = new Display(grid);
        Game j = new Game(grid, display);
        frame.add(display);
        display.addMouseListener(j);
    }

    public Game(HexGrid grid, Display display) {
        this.grid = grid;
        this.display = display;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Cordonnées du clic
        int x = e.getX();
        int y = e.getY();
    }
}
