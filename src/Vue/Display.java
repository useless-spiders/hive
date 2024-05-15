package Vue;

import Controleur.PageManager;
import Modele.HexGrid;
import Modele.Insect.Insect;
import Modele.Player;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structures.HexMetrics;
import Structures.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Display extends JPanel { // Étendre JPanel plutôt que JComponent
    private static final String IMAGE_PATH = "res/Images/";
    private static final String BACKGROUND_PATH = "res/Backgrounds/";

    private DisplayHexGrid displayHexGrid;
    private DisplayPlayableHex displayPlayableHex;
    private DisplayConfigParty displayConfigParty;
    private DisplayBankInsects displayBankInsects;
    private DisplayMenuInParty displayMenuInParty;
    private DisplayOpening displayOpening;
    private DisplayStack displayStack;

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

    public Display(HexGrid grid, JFrame frame, GameActionHandler controller, PageManager pageManager, PageActionHandler controllerPage){
        this.frame = frame;
        this.controller = controller;
        displayGame(grid, pageManager, controllerPage);
        //displayMenuSelectLvl();

        JPanel container = new JPanel(new BorderLayout()); // Créer un conteneur JPanel
        container.add(this, BorderLayout.CENTER); // Ajouter le display au centre du conteneur

        frame.add(container); // Ajouter le conteneur au JFrame
        frame.pack(); // Pack le JFrame
    }

    public void cleanFrame(){
        frame.getContentPane().removeAll();
    }

    public void displayGame(HexGrid grid, PageManager pageManager, PageActionHandler controllerPage){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.displayHexGrid = new DisplayHexGrid(grid);
        this.displayBankInsects = new DisplayBankInsects(this, gbc, controller);
        this.displayPlayableHex = new DisplayPlayableHex(controller);
        this.displayMenuInParty = new DisplayMenuInParty(this, gbc, controller, pageManager, controllerPage);
        this.displayStack = new DisplayStack(grid);
        setOpaque(false);
    }

    public DisplayHexGrid getDisplayHexGrid() {
        return displayHexGrid;
    }
    public DisplayPlayableHex getDisplayPlayableHex() {
        return displayPlayableHex;
    }
    public DisplayBankInsects getDisplayBankInsects() {
        return displayBankInsects;
    }
    public DisplayStack getDisplayStack() {
        return displayStack;
    }

    public static String getImageName(Class<? extends Insect> insectClass, Player player) {
        return insectClass.getSimpleName() + "_" + player.getColor() + ".png";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        this.displayStack.paintStack(g2d);

        // Centrer le composant au milieu du GridBagConstraints
        int x = (getWidth() - displayHexGrid.getPreferredSize().width) / 2;
        int y = (getHeight() - displayHexGrid.getPreferredSize().height) / 2;
        g2d.translate(x, y);
    }
}
