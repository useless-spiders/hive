package View;

import Model.HexGrid;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.ViewMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayGame extends JPanel { // Étendre JPanel plutôt que JComponent

    private DisplayGameBackground displayGameBackground;
    private DisplayHexGrid displayHexGrid;
    private DisplayPlayableHex displayPlayableHex;
    private DisplayBankInsects displayBankInsects;
    private DisplayMenuInGame displayMenuInGame;
    private DisplayStack displayStack;
    private DisplayInfoInGame displayInfoInGame;

    private JFrame frameGame;
    private GameActionHandler gameActionHandler;
    private PageActionHandler pageActionHandler;
    private JLabel turnLabel;

    public DisplayGame(JFrame frameGame, PageActionHandler pageActionHandler, GameActionHandler gameActionHandler){
        this.frameGame = frameGame;
        this.gameActionHandler = gameActionHandler;
        this.pageActionHandler = pageActionHandler;

        //Pour construire le jeu
        buildGame();

        //Pour afficher le jeu
        JPanel container = new JPanel(new BorderLayout()); // Créer un conteneur JPanel
        container.add(this, BorderLayout.CENTER); // Ajouter le display au centre du conteneur
        frameGame.add(container); // Ajouter le conteneur au JFrame
        frameGame.pack(); // Pack le JFrame
    }

    public void buildGame(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.displayGameBackground = new DisplayGameBackground(frameGame);
        this.displayHexGrid = new DisplayHexGrid(this.gameActionHandler);
        this.displayBankInsects = new DisplayBankInsects(this, this.gameActionHandler);
        this.displayPlayableHex = new DisplayPlayableHex(this.gameActionHandler);
        this.displayMenuInGame = new DisplayMenuInGame(this, gbc, this.gameActionHandler, this.pageActionHandler);
        this.displayInfoInGame = new DisplayInfoInGame(this);
        this.displayStack = new DisplayStack(this.gameActionHandler);
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
    public DisplayMenuInGame getDisplayMenuInGame(){return this.displayMenuInGame;}

    public void centerGame() {
        HexGrid hexGrid = gameActionHandler.getGrid();
        if (!hexGrid.getGrid().isEmpty()) {
            // Récupérer le 1er hexagone de la grille et convertir en pixel
            HexCoordinate hexCoordinate = hexGrid.getGrid().keySet().iterator().next();
            Point hexCoordinatePoint = HexMetrics.hexToPixel(hexCoordinate);
            hexCoordinatePoint.x += ViewMetrics.getViewOffsetX();
            hexCoordinatePoint.y += ViewMetrics.getViewOffsetY();

            // Calculer le centre de la vue en pixels
            HexCoordinate hexCenter = HexMetrics.hexCenterCoordinate(this.getWidth(), this.getHeight());
            Point hexCenterPoint = HexMetrics.hexToPixel(hexCenter);

            // Soustraire hexCoordinate à hexCenter
            int dx = hexCenterPoint.x - hexCoordinatePoint.x;
            int dy = hexCenterPoint.y - hexCoordinatePoint.y;

            ViewMetrics.updateViewPosition(dx, dy);

            this.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Afficher le background du jeu
        this.displayGameBackground.paintGameBackground(g);

        displayInfoInGame.updatePrintInfo(this.gameActionHandler.getCurrentPlayer().getName(), this.gameActionHandler.getCurrentPlayer().getTurn());

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
