package Vue;

import Modele.HexGrid;
import Pattern.GameActionHandler;
import Structures.HexCoordinate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Display extends JComponent {
    private static final String IMAGE_PATH = "res/Images/";

    private DisplayHexGrid displayHexGrid;
    private DisplayConfigParty displayConfigParty;
    private DisplayBankInsects displayBankInsects;
    private JFrame frame;
    private GameActionHandler controller;

    public static Image loadImage(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(IMAGE_PATH + nom)));
        } catch (Exception e) {
            System.err.println("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public static ImageIcon loadIcon(String nom) {
        try {
            return new ImageIcon(IMAGE_PATH + nom);
        } catch (Exception e) {
            System.err.println("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public Display(HexGrid grid, JFrame frame, GameActionHandler controller){
        this.frame = frame;
        this.displayHexGrid = new DisplayHexGrid(grid);
        this.displayBankInsects = new DisplayBankInsects(frame, controller);
        this.controller = controller;

        //TODO:afficher la config de la partie
        //this.displayConfigParty = new DisplayConfigParty(frame);
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.displayHexGrid.paintHexGrid(g);
        g.drawString("Tour de : " + this.controller.getCurrentPlayer().getColor(), 10, 10);

        // Affiche les cases jouables
        for (HexCoordinate cell : controller.getPlayableCells()) {
            Point center = HexMetrics.calculateHexCenter(cell.getX(), cell.getY());
            g.drawImage(loadImage("Location.png"), center.x - HexMetrics.HEX_WIDTH / 2, center.y - HexMetrics.HEX_HEIGHT / 2, HexMetrics.HEX_WIDTH, HexMetrics.HEX_HEIGHT, null);
        }
    }

}
