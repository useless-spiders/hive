package View;

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
    private DisplayInfoInGame displayInfoInGame;

    private JFrame frame;
    private GameActionHandler controller;
    private PageActionHandler controllerPage;
    private JLabel turnLabel;

    public DisplayGame(JFrame frame, PageActionHandler controllerPage, GameActionHandler controller){
        this.frame = frame;
        this.controller = controller;
        this.controllerPage = controllerPage;

        //Pour construire le jeu
        buildGame();

        //Pour afficher le jeu
        JPanel container = new JPanel(new BorderLayout()); // Créer un conteneur JPanel
        container.add(this, BorderLayout.CENTER); // Ajouter le display au centre du conteneur
        frame.add(container); // Ajouter le conteneur au JFrame
        frame.pack(); // Pack le JFrame
    }

    public void buildGame(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.displayGameBackground = new DisplayGameBackground(frame);
        this.displayHexGrid = new DisplayHexGrid(this.controller);
        this.displayBankInsects = new DisplayBankInsects(this, this.controller);
        this.displayPlayableHex = new DisplayPlayableHex(this.controller);
        this.displayMenuInParty = new DisplayMenuInParty(this, gbc, this.controller, this.controllerPage);
        this.displayInfoInGame = new DisplayInfoInGame(this);
        this.displayStack = new DisplayStack(this.controller);

    }

    public DisplayHexGrid getDisplayHexGrid() {
        return this.displayHexGrid;
    }
    public DisplayPlayableHex getDisplayPlayableHex() {
        return this.displayPlayableHex;
    }
    public DisplayBankInsects getDisplayBankInsects() {
        return this.displayBankInsects;
    }
    public DisplayStack getDisplayStack() {
        return this.displayStack;
    }
    public DisplayInfoInGame getDisplayInfoInGame(){return this.displayInfoInGame;}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Afficher le background du jeu
        this.displayGameBackground.paintGameBackground(g);

        displayInfoInGame.updatePrintInfo(this.controller.getCurrentPlayer().getName(), this.controller.getCurrentPlayer().getTurn());

        //Pour le "dragging"
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(ViewMetrics.getViewOffsetX(), ViewMetrics.getViewOffsetY());
        this.displayHexGrid.paintHexGrid(g2d);
        this.displayPlayableHex.paintPlayableHex(g2d);
        this.displayStack.paintStack(g2d);

        // Centrer le composant au milieu du GridBagConstraints
        int x = (getWidth() - this.displayHexGrid.getPreferredSize().width) / 2;
        int y = (getHeight() - this.displayHexGrid.getPreferredSize().height) / 2;
        g2d.translate(x, y);
    }
}
