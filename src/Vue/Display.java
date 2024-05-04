package Vue;

import Modele.HexGrid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Display extends JComponent {
    private DisplayHexGrid displayHexGrid;
    private DisplayConfigParty displayConfigParty;
    private DisplayBankInsects displayBankInsects;
    private JFrame frame;

    public static Image loadImage(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(nom)));
        } catch (Exception e) {
            System.err.println("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public Display(HexGrid grid, JFrame frame) {
        this.displayHexGrid = new DisplayHexGrid(grid);
        this.frame = frame;

        //TODO:afficher la config de la partie
        //DisplayConfigParty displayConfigParty = new DisplayConfigParty(frame);
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.displayHexGrid.paintHexGrid(g);
    }

}
