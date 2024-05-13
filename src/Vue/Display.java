package Vue;

import Modele.HexGrid;
import Pattern.GameActionHandler;
import Structures.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Display extends JPanel { // Étendre JPanel plutôt que JComponent
    private static final String IMAGE_PATH = "res/Images/";

    private DisplayHexGrid displayHexGrid;
    private DisplayPlayableHex displayPlayableHex;
    private DisplayConfigParty displayConfigParty;
    private DisplayBankInsects displayBankInsects;
    private DisplayMenuInParty displayMenuInParty;
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

    public Display(HexGrid grid, JFrame frame, GameActionHandler controller){
        this.controller = controller;
        displayGame(grid);
    }

    public void cleanFrame(){
        removeAll(); // Utiliser removeAll() pour supprimer tous les composants du JPanel
    }

    public void diplayMenuSelectLvl(){
        this.displayConfigParty = new DisplayConfigParty((JFrame) SwingUtilities.getWindowAncestor(this));
    }

    public void displayGame(HexGrid grid){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.displayHexGrid = new DisplayHexGrid(grid);
        this.displayBankInsects = new DisplayBankInsects(this, gbc, controller);
        this.displayPlayableHex = new DisplayPlayableHex(controller);
        this.displayMenuInParty = new DisplayMenuInParty(this, gbc, controller);

        setOpaque(false);
    }

    public DisplayHexGrid getDisplayHexGrid() {
        return displayHexGrid;
    }

    public DisplayPlayableHex getDisplayPlayableHex() {
        return displayPlayableHex;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Tour de : " + this.controller.getCurrentPlayer().getName(), 10, 10);

        // Pour le "dragging"
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(HexMetrics.getViewOffsetX(), HexMetrics.getViewOffsetY());

        this.displayHexGrid.paintHexGrid(g2d);
        this.displayPlayableHex.paintPlayableHex(g2d);

        // Centrer le composant au milieu du GridBagConstraints
        int x = (getWidth() - displayHexGrid.getPreferredSize().width) / 2;
        int y = (getHeight() - displayHexGrid.getPreferredSize().height) / 2;
        g2d.translate(x, y);
    }
}