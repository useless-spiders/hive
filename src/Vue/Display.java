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
    private DisplayPlayableHex displayPlayableHex;
    private DisplayConfigParty displayConfigParty;
    private DisplayBankInsects displayBankInsects;
    private DisplayMenuInParty displayMenuInParty;
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
        this.displayPlayableHex = new DisplayPlayableHex();
        this.displayMenuInParty = new DisplayMenuInParty(frame);

        //TODO:afficher la config de la partie
        //frame.getContentPane().removeAll();
        //this.displayConfigParty = new DisplayConfigParty(frame);

    }

    public DisplayHexGrid getDisplayHexGrid() {
        return displayHexGrid;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawString("Tour de : " + this.controller.getCurrentPlayer().getColor(), 10, 10);
        this.displayHexGrid.paintHexGrid(g);
        this.displayPlayableHex.paintPlayableHex(g, controller);
    }
}
