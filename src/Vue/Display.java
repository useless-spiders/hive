package Vue;

import Modele.HexGrid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;


public class Display extends JComponent {
    private JFrame frame;
    private DisplayHexGrid displayHexGrid;

    public static Image charge(String nom) {
        try {
            return ImageIO.read(new FileInputStream(nom));
        } catch (Exception e) {
            System.err.println("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public Display(HexGrid grid, JFrame frame) {
        this.frame = frame;
        this.displayHexGrid = new DisplayHexGrid(grid);
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.displayHexGrid.paintHexGrid(g);
    }

}
