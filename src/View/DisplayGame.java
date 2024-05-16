package View;

import Model.HexGrid;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.ViewMetrics;

import javax.swing.*;
import java.awt.*;
public class DisplayGame extends JPanel { // Étendre JPanel plutôt que JComponent

    private DisplayGameBackground displayGameBackground;
    private DisplayHexGrid displayHexGrid;
    private DisplayPlayableHex displayPlayableHex;
    private DisplayBankInsects displayBankInsects;
    private DisplayMenuInParty displayMenuInParty;
    private DisplayStack displayStack;

    private JFrame frame;
    private GameActionHandler controller;

    public DisplayGame(HexGrid grid, JFrame frame, GameActionHandler controller, PageActionHandler controllerPage){
        this.frame = frame;
        this.controller = controller;

        //Pour construire le jeu
        buildGame(grid, controllerPage);

        //Pour afficher le jeu
        JPanel container = new JPanel(new BorderLayout()); // Créer un conteneur JPanel
        container.add(this, BorderLayout.CENTER); // Ajouter le display au centre du conteneur
        frame.add(container); // Ajouter le conteneur au JFrame
        frame.pack(); // Pack le JFrame
    }

    public void buildGame(HexGrid grid, PageActionHandler controllerPage){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.displayGameBackground = new DisplayGameBackground(frame);
        this.displayHexGrid = new DisplayHexGrid(grid);
        this.displayBankInsects = new DisplayBankInsects(this, gbc, controller);
        this.displayPlayableHex = new DisplayPlayableHex(controller);
        this.displayMenuInParty = new DisplayMenuInParty(this, gbc, controller, controllerPage);
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
        //this.displayGameBackground.paintGameBackground(g);

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
