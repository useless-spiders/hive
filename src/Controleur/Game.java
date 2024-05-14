package Controleur;

import Modele.Move;
import Modele.HexCell;
import Modele.HexGrid;
import Modele.History;
import Modele.Insect.Bee;
import Modele.Insect.Beetle;
import Modele.Insect.Insect;
import Modele.Player;
import Modele.Ia.Ia;
import Pattern.GameActionHandler;
import Structures.HexCoordinate;
import Structures.Log;
import Vue.Display;
import Structures.HexMetrics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class Game extends MouseAdapter implements GameActionHandler, MouseMotionListener {
    private HexGrid hexGrid;
    private Display display;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean isInsectButtonClicked;
    private boolean isInsectCellClicked;
    private HexCoordinate hexClicked;
    private HexCoordinate hoverCell;
    private Insect insect;
    private ArrayList<HexCoordinate> playableCells;
    private int lastX, lastY;
    private History history;
    private Ia ia;

    public static void start(JFrame frame) {
        HexGrid hexGrid = new HexGrid();
        Game g = new Game(hexGrid);
        Display display = new Display(hexGrid, frame, g);

        g.setDisplay(display);

        JPanel container = new JPanel(new BorderLayout()); // Créer un conteneur JPanel
        container.add(display, BorderLayout.CENTER); // Ajouter le display au centre du conteneur

        frame.add(container); // Ajouter le conteneur au JFrame
        frame.pack(); // Pack le JFrame

        display.addMouseListener(g);
        display.addMouseMotionListener(g);

    }

    public Game(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
        this.initPlayers();

        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
        this.playableCells = new ArrayList<>();
        this.history = new History();
        /////////A COMMENTER POUR PVP//////////////
        this.ia = Ia.nouvelle(this, "Aleatoire", player2);
        //////////////////////////////////////////
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public HexGrid getGrid() {
        return this.hexGrid;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    private void checkLoser() {
        boolean lPlayer1 = this.hexGrid.checkLoser(player1);
        boolean lPlayer2 = this.hexGrid.checkLoser(player2);
        if (lPlayer1 && lPlayer2) {
            Log.addMessage("Egalité !");
        } else {
            if (lPlayer1) {
                Log.addMessage("Le joueur " + player1.getColor() + " a perdu !");
            } else if (lPlayer2) {
                Log.addMessage("Le joueur " + player2.getColor() + " a perdu !");
            }
        }
    }

    private void switchPlayer() {
        display.repaint();
        checkLoser();
        this.currentPlayer.incrementTurn();
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
            tourIa();
        } else {
            this.currentPlayer = this.player1;
        }
        display.repaint();
    }

    private void tourIa()
    {
        this.ia.playMove();
        switchPlayer();
    }

    private void initPlayers() {
        this.player1 = new Player("white", "Inspecteur blanco");
        this.player2 = new Player("black", "Barbe noir");

        Random random = new Random();
        this.currentPlayer = random.nextBoolean() ? player1 : player2;
    }

    private void handleCellClicked(HexCell cell, HexCoordinate hexagon) {
        Insect insect = cell.getTopInsect();

        if (isInsectCellClicked == false) { //On clique sur un insecte à déplacer
            if (insect.getPlayer().equals(currentPlayer)) {
                isInsectCellClicked = true;
                hexClicked = hexagon;
                playableCells = insect.getPossibleMovesCells(hexClicked.getX(), hexClicked.getY(), hexGrid);
                // rendre transparente la case
                display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
            } else {
                Log.addMessage("Ce pion ne vous appartient pas");
            }

        } else {
            HexCell cellClicked = hexGrid.getCell(hexClicked.getX(), hexClicked.getY());
            if (cellClicked.getTopInsect().getClass() == Beetle.class && !hexagon.equals(hexClicked)) { //On clique sur un insecte cible d'un scarabée
                handleInsectMoved(hexagon);

            } else { //On clique sur un insecte déjà sélectionné
                isInsectCellClicked = false;
                display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
                this.playableCells.clear();
            }
        }
    }

    private void handleInsectMoved(HexCoordinate hexagon) {
        if (playableCells.contains(hexagon)) {
            HexCell cellClicked = hexGrid.getCell(hexClicked.getX(), hexClicked.getY());
            Insect movedInsect = cellClicked.getTopInsect();

            cellClicked.removeTopInsect();
            if (cellClicked.getInsects().isEmpty()) {
                hexGrid.removeCell(hexClicked.getX(), hexClicked.getY());
            }

            if (hexGrid.getCell(hexagon) != null) {
                hexGrid.getCell(hexagon).addInsect(movedInsect);
            } else {
                hexGrid.addCell(hexagon.getX(), hexagon.getY(), movedInsect);
            }

            isInsectCellClicked = false;
            display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
            playableCells.clear();
            switchPlayer();

            // Add the move to the history
            Move move = new Move(movedInsect, hexClicked, hexagon);
            history.addMove(move);
        } else {
            Log.addMessage("Déplacement impossible");
        }
    }

    private void handleInsectPlaced(HexCoordinate hexagon) {
        if (this.insect.getPlayer().equals(currentPlayer)) { // Vérifie si le joueur actuel est le propriétaire de l'insecte

            if (this.insect.isPlacable(hexagon, hexGrid)) {
                if (currentPlayer.canAddInsect(this.insect)) { // Vérifie si le joueur actuel peut ajouter un insecte
                    if (this.insect instanceof Bee) {
                        currentPlayer.setBeePlaced(true);
                    }
                    if (currentPlayer.isBeePlaced() || currentPlayer.getTurn() < 4) { // Vérifie que la reine a été placé durant les 4 premiers tours
                        currentPlayer.addInsect(this.insect);
                        hexGrid.addCell(hexagon.getX(), hexagon.getY(), this.insect);
                        isInsectButtonClicked = false;
                        playableCells.clear();
                        switchPlayer();

                        // Add the placement to the history
                        Move move = new Move(this.insect, null, hexagon);
                        history.addMove(move);
                    } else {
                        Log.addMessage("Vous devez placer l'abeille avant de placer d'autres insectes");
                    }
                } else {
                    Log.addMessage("Vous avez atteint le nombre maximum de pions de ce type");
                }
            } else {
                Log.addMessage("placement impossible !");
            }
        } else {
            Log.addMessage("Ce n'est pas votre tour");
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX() - HexMetrics.getViewOffsetX();
        int mouseY = e.getY() - HexMetrics.getViewOffsetY();

        ArrayList<HexCoordinate> playableCells = getPlayableCells();

        HexCoordinate newHoverCell = HexMetrics.pixelToHex(mouseX, mouseY);
        if (!newHoverCell.equals(hoverCell) && playableCells.contains(newHoverCell)) {
            hoverCell = newHoverCell;
            display.getDisplayPlayableHex().updateHoverCell(hoverCell);

            display.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        lastX = e.getX();
        lastY = e.getY();
        int mouseX = e.getX() - HexMetrics.getViewOffsetX();
        int mouseY = e.getY() - HexMetrics.getViewOffsetY();

        HexCoordinate hexagon = HexMetrics.pixelToHex(mouseX, mouseY);
        HexCell cell = hexGrid.getCell(hexagon.getX(), hexagon.getY());
        if (cell != null) { //on clique sur une case existante pour la déplacer ou bien pour être un insecte cible du scarabée
            handleCellClicked(cell, hexagon);
        } else if (isInsectCellClicked) { //on clique sur une case vide pour déplacer une case sélectionnée
            handleInsectMoved(hexagon);
        } else if (isInsectButtonClicked) { //on clique sur une case vide pour déposer une nouvelle case
            handleInsectPlaced(hexagon);
        }

        display.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - lastX;
        int dy = e.getY() - lastY;
        HexMetrics.updateViewPosition(dx, dy);
        lastX = e.getX();
        lastY = e.getY();

        display.repaint();
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void clicInsectButton(Insect insect) {
        System.out.println("click bouton");
        this.isInsectButtonClicked = true;
        this.isInsectCellClicked = false;
        this.insect = insect;
        this.playableCells.clear();
        if (insect.getPlayer().equals(currentPlayer) && this.currentPlayer.canAddInsect(insect)) {
            if (this.currentPlayer.getTurn() <= 1 && hexGrid.getGrid().isEmpty()) {
                this.playableCells.clear();
            } else if (this.currentPlayer.getTurn() <= 1 && !hexGrid.getGrid().isEmpty()) {
                this.playableCells = insect.getPossibleInsertionCellT1(hexGrid);
            } else {
                this.playableCells = insect.getPossibleInsertionCells(hexGrid);
            }
        }
        display.getDisplayHexGrid().updateInsectClickState(isInsectCellClicked, hexClicked);
        display.repaint();
    }

    @Override
    public ArrayList<HexCoordinate> getPlayableCells() {
        return playableCells;
    }

    @Override
    public void cancelMove() {
        Move move = history.cancelMove();
        if (move != null) {
            this.currentPlayer.decrementTurn();
            switchPlayer();
            this.currentPlayer.decrementTurn();

            HexCoordinate from = move.getPreviousCoor();
            HexCoordinate to = move.getNewCoor();
            Insect insect = move.getInsect();

            if (insect instanceof Bee) {
                currentPlayer.setBeePlaced(false);
            }

            if(hexGrid.getCell(to.getX(), to.getY()) != null){
                hexGrid.getCell(to.getX(), to.getY()).removeTopInsect();
            }
            if(hexGrid.getCell(to.getX(), to.getY()) == null || hexGrid.getCell(to.getX(), to.getY()).getInsects().isEmpty()){
                hexGrid.removeCell(to.getX(), to.getY());
            }

            if (from != null) {
                if (hexGrid.getCell(from.getX(), from.getY()) != null) {
                    hexGrid.getCell(from.getX(), from.getY()).addInsect(insect);
                } else {
                    hexGrid.addCell(from.getX(), from.getY(), insect);
                }
            }

            this.currentPlayer.removeInsect(insect);
            if (from != null) {
                this.currentPlayer.addInsect(insect);
            }
            display.repaint();
        }
    }

    @Override
    public void redoMove() {
        Move move = history.redoMove();
        if (move != null) {
            HexCoordinate from = move.getPreviousCoor();
            HexCoordinate to = move.getNewCoor();
            Insect insect = move.getInsect();

            if (insect instanceof Bee) {
                currentPlayer.setBeePlaced(true);
            }

            if (from != null) {
                if(hexGrid.getCell(from.getX(), from.getY()) != null){
                    hexGrid.getCell(from.getX(), from.getY()).removeTopInsect();
                }
                if(hexGrid.getCell(from.getX(), from.getY()) == null || hexGrid.getCell(from.getX(), from.getY()).getInsects().isEmpty()){
                    hexGrid.removeCell(from.getX(), from.getY());
                }
            }

            if (hexGrid.getCell(to.getX(), to.getY()) != null) {
                hexGrid.getCell(to.getX(), to.getY()).addInsect(insect);
            } else {
                hexGrid.addCell(to.getX(), to.getY(), insect);
            }

            this.currentPlayer.removeInsect(insect);
            if (from != null) {
                this.currentPlayer.addInsect(insect);
            }
            switchPlayer();
            display.repaint();
        }
    }
}


//TODO : faire un bouton pour recentrer sur le jeu
//TODO : separer des trucs dans game, et verifier que si on enleve tout le graphique ça fonctionne toujours