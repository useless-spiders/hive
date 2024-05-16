package View;

import Controller.PageManager;
import Model.HexGrid;
import Model.Insect.Insect;
import Model.Player;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.ViewMetrics;
import Structure.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DisplayGame extends JPanel { // Étendre JPanel plutôt que JComponent
    private static final String IMAGE_PATH = "res/Images/";
    private static final String BACKGROUND_PATH = "res/Backgrounds/";

    private DisplayGameBackground displayGameBackground;
    private DisplayHexGrid displayHexGrid;
    private DisplayPlayableHex displayPlayableHex;
    private DisplayBankInsects displayBankInsects;
    private DisplayMenuInParty displayMenuInParty;
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

    public DisplayGame(HexGrid grid, JFrame frame, GameActionHandler controller, PageManager pageManager, PageActionHandler controllerPage){
        this.frame = frame;
        this.controller = controller;

        //Pour construire le jeu
        buildGame(grid, pageManager, controllerPage);

        //Pour afficher le jeu
        JPanel container = new JPanel(new BorderLayout()); // Créer un conteneur JPanel
        container.add(this, BorderLayout.CENTER); // Ajouter le display au centre du conteneur
        frame.add(container); // Ajouter le conteneur au JFrame
        frame.pack(); // Pack le JFrame
    }

    public void buildGame(HexGrid grid, PageManager pageManager, PageActionHandler controllerPage){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.displayGameBackground = new DisplayGameBackground(frame);
        this.displayHexGrid = new DisplayHexGrid(grid);
        this.displayBankInsects = new DisplayBankInsects(this, gbc, controller);
        this.displayPlayableHex = new DisplayPlayableHex(controller);
        this.displayMenuInParty = new DisplayMenuInParty(this, gbc, controller, pageManager, controllerPage);
        this.displayStack = new DisplayStack(grid);
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

    private void printPlayer(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(45, 90, 200, 70); // Les coordonnées et la taille du rectangle

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14)); // Définir la police du texte et le style
        String[] lines = {
                "Informations jeu :",
                "Tour de : " + this.controller.getCurrentPlayer().getName()
        };
        int lineHeight = g.getFontMetrics().getHeight();
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], 50, 95 + (i * lineHeight) + 25);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Afficher le background du jeu
        this.displayGameBackground.paintGameBackground(g);

        //Affichage du joueur courant
        printPlayer(g);

        //Pour le "dragging"
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(ViewMetrics.getViewOffsetX(), ViewMetrics.getViewOffsetY());
        this.displayHexGrid.paintHexGrid(g2d);
        this.displayPlayableHex.paintPlayableHex(g2d);
        this.displayStack.paintStack(g2d);

        // Centrer le composant au milieu du GridBagConstraints
        int x = (getWidth() - displayHexGrid.getPreferredSize().width) / 2;
        int y = (getHeight() - displayHexGrid.getPreferredSize().height) / 2;
        g2d.translate(x, y);
    }
}
