package View;

import Model.HexGrid;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.RessourceLoader;
import Structure.ViewMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayGame extends JPanel { // Étendre JPanel plutôt que JComponent

    private final Image background;
    private final JFrame frameGame;
    private final GameActionHandler gameActionHandler;
    private final RessourceLoader ressourceLoader;
    private DisplayHexGrid displayHexGrid;
    private DisplayPlayableHex displayPlayableHex;
    private DisplayBankInsects displayBankInsects;
    private DisplayMenuInGame displayMenuInGame;
    private DisplayStack displayStack;
    private DisplayInfoInGame displayInfoInGame;

    /**
     * Constructeur de la classe DisplayGame.
     *
     * @param frameGame         JFrame
     * @param gameActionHandler GameActionHandler
     */
    public DisplayGame(JFrame frameGame, GameActionHandler gameActionHandler) {
        this.frameGame = frameGame;
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);
        this.background = this.ressourceLoader.loadBackground("Game_background.png");

        //Pour construire le jeu
        this.buildGame();

        //Pour afficher le jeu
        JPanel container = new JPanel(new BorderLayout()); // Créer un conteneur JPanel
        container.add(this, BorderLayout.CENTER); // Ajouter le display au centre du conteneur
        frameGame.add(container); // Ajouter le conteneur au JFrame
        frameGame.pack(); // Pack le JFrame
    }

    /**
     * Construit les composants du jeu et les ajoute au panneau principal.
     */
    public void buildGame() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.displayHexGrid = new DisplayHexGrid(this.gameActionHandler);
        this.displayPlayableHex = new DisplayPlayableHex(this.gameActionHandler);
        this.displayInfoInGame = new DisplayInfoInGame(this, gbc, this.gameActionHandler);
        this.displayBankInsects = new DisplayBankInsects(this, gbc, this.gameActionHandler);
        this.displayMenuInGame = new DisplayMenuInGame(this, gbc, this.gameActionHandler);

        this.displayStack = new DisplayStack(this.gameActionHandler);
        this.displayBankInsects.updateButtons();
    }

    /**
     * Retourne le JFrame du jeu.
     *
     * @return JFrame
     */
    public JFrame getFrameGame() {
        return this.frameGame;
    }

    /**
     * Retourne l'objet DisplayHexGrid.
     *
     * @return DisplayHexGrid
     */
    public DisplayHexGrid getDisplayHexGrid() {
        return this.displayHexGrid;
    }

    /**
     * Retourne l'objet DisplayPlayableHex.
     *
     * @return DisplayPlayableHex
     */
    public DisplayPlayableHex getDisplayPlayableHex() {
        return this.displayPlayableHex;
    }

    /**
     * Retourne l'objet DisplayBankInsects.
     *
     * @return DisplayBankInsects
     */
    public DisplayBankInsects getDisplayBankInsects() {
        return this.displayBankInsects;
    }

    /**
     * Retourne l'objet DisplayStack.
     *
     * @return DisplayStack
     */
    public DisplayStack getDisplayStack() {
        return this.displayStack;
    }

    /**
     * Retourne l'objet DisplayMenuInGame.
     *
     * @return DisplayMenuInGame
     */
    public DisplayMenuInGame getDisplayMenuInGame() {
        return this.displayMenuInGame;
    }

    /**
     * Centre le jeu en fonction des coordonnées hexagonales de la grille.
     */
    public void centerGame() {
        HexGrid hexGrid = this.gameActionHandler.getGrid();
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

    /**
     * Redéfinit la méthode paintComponent pour dessiner les composants du jeu: le plateau de le background
     *
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.displayMenuInGame.updateButtons();

        //Afficher le background du jeu
        g.drawImage(this.background, 0, 0, this.frameGame.getWidth(), this.frameGame.getHeight(), this);

        this.displayInfoInGame.updatePrintInfo();

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
