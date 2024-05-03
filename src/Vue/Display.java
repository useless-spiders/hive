package Vue;

import Modele.HexGrid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Display extends JComponent {
    private DisplayHexGrid displayHexGrid;

    public static Image loadImage(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(nom)));
        } catch (Exception e) {
            System.err.println("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public Display(HexGrid grid) {
        this.displayHexGrid = new DisplayHexGrid(grid);
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.displayHexGrid.paintHexGrid(g);
    }

}
