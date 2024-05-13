package Vue;

import Modele.HexGrid;
import Pattern.GameActionHandler;
import Structures.HexCoordinate;
import Structures.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Display extends JComponent {
    private static final String IMAGE_PATH = "res/Images/";
    private static final String BACKGROUND_PATH = "res/Backgrounds/";

    private DisplayHexGrid displayHexGrid;
    private DisplayPlayableHex displayPlayableHex;
    private DisplayConfigParty displayConfigParty;
    private DisplayBankInsects displayBankInsects;
    private DisplayMenuInParty displayMenuInParty;
    private DisplayOpening displayOpening;

    private JFrame frame;
    private GameActionHandler controller;

    public static Image loadImage(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(IMAGE_PATH + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public static ImageIcon loadIcon(String nom) {
        try {
            return new ImageIcon(IMAGE_PATH + nom);
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'icon " + nom);
            System.exit(1);
            return null;
        }
    }

    public static Image loadBackground(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(BACKGROUND_PATH + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger le fond " + nom);
            System.exit(1);
            return null;
        }
    }

    public Display(HexGrid grid, JFrame frame, GameActionHandler controller){
        this.frame = frame;
        displayGame(controller, grid);
    }

    public void cleanFrame(){
        frame.getContentPane().removeAll();
    }

    public void diplayMenuSelectLvl(){
        this.displayConfigParty = new DisplayConfigParty(frame);
    }

    public void displayGame(GameActionHandler controller, HexGrid grid){
        this.displayHexGrid = new DisplayHexGrid(grid);
        this.displayBankInsects = new DisplayBankInsects(frame, controller);
        this.controller = controller;
        this.displayPlayableHex = new DisplayPlayableHex(controller);
        this.displayMenuInParty = new DisplayMenuInParty(frame, controller);
        this.displayOpening = new DisplayOpening(frame);
    }

    public DisplayHexGrid getDisplayHexGrid() {
        return displayHexGrid;
    }
    public DisplayPlayableHex getDisplayPlayableHex() {
        return displayPlayableHex;
    }

    @Override
    protected void paintComponent(Graphics g) {
        //Afficher l'opening
        /*this.displayOpening.paintOpening(g);
        Font font = new Font("Times New Roman", Font.BOLD, 20); // Définir la police, le style et la taille
        g.setFont(font); // Appliquer la police définie
        String text = "Tour de : " + this.controller.getCurrentPlayer().getName();
        int x = 30; // Position x
        int y = 15; // Position y
        g.drawString(text, x, y);*/

        g.drawString("Tour de : " + this.controller.getCurrentPlayer().getName(), 10, 10);

        //Pour le "dragging"
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(HexMetrics.getViewOffsetX(), HexMetrics.getViewOffsetY());

        this.displayHexGrid.paintHexGrid(g2d);
        this.displayPlayableHex.paintPlayableHex(g2d);
    }
}
