package Controller;

import Model.*;
import Model.Ai.Ai;
import Model.Insect.Beetle;
import Model.Insect.Insect;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.HexMetrics;
import Structure.Log;
import Structure.ViewMetrics;
import View.DisplayGame;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game implements GameActionHandler {
    private HexGrid hexGrid;
    private DisplayGame displayGame;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean isInsectButtonClicked;
    private boolean isInsectCellClicked;
    private HexCoordinate hexClicked;
    private ArrayList<HexCoordinate> playableCoordinates;
    private History history;
    private Insect insect;
    private Timer delay;
    private PageManager pageManager;


    public Game() {
        this.hexGrid = new HexGrid();
        this.initPlayers();

        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
        this.playableCoordinates = new ArrayList<>();
        this.history = new History();
        this.pageManager = new PageManager(this);
        this.startAi();
    }

    public void setPlayer(int player, String name) {
        if (player == 1) {
            this.player1.setAi(Ai.nouvelle(this, name, this.player1));
        } else {
            this.player2.setAi(Ai.nouvelle(this, name, this.player2));
        }
    }

    @Override
    public void startAi() {
        if (this.currentPlayer.getTurn() <= 1 && (this.player1.isAi() || this.player2.isAi())) {
            this.delay = new Timer(1000, e -> new Thread(() -> {
                this.delay.stop();
                Ai ai = this.currentPlayer.getAi();
                if (ai != null) {
                    try {
                        Move iaMove = ai.chooseMove();
                        if (iaMove != null) {
                            SwingUtilities.invokeLater(() -> {
                                this.hexGrid.applyMove(iaMove, this.currentPlayer);
                                this.history.addMove(iaMove);
                                this.switchPlayer();
                                this.playableCoordinates.clear();
                                this.displayGame.getDisplayBankInsects().updateAllLabels();
                                this.displayGame.repaint();
                            });
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                Log.addMessage("L'IA n'a pas pu jouer, on arrete l'IA");
                            });
                        }
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            Log.addMessage("Erreur lors de l'exécution de l'IA dans le thread "+ex);
                        });
                    }
                }
            }).start());
            this.delay.start();
        }
    }

    @Override
    public void stopAi() {
        if (this.delay != null) {
            this.delay.stop();
        }
    }

    @Override
    public void changeStateAi() {
        if (this.delay != null) {
            if (this.delay.isRunning()) {
                this.delay.stop();
            } else {
                this.delay.start();
            }
        }
    }

    @Override
    public boolean isAiRunning(){
        return this.delay.isRunning();
    }

    @Override
    public History getHistory() {
        return this.history;
    }

    @Override
    public Player getPlayer1() {
        return this.player1;
    }

    @Override
    public Player getPlayer2() {
        return this.player2;
    }

    @Override
    public HexGrid getGrid() {
        return this.hexGrid;
    }

    @Override
    public boolean getIsInsectButtonClicked() {
        return this.isInsectButtonClicked;
    }

    @Override
    public boolean getIsInsectCellClicked() {
        return this.isInsectCellClicked;
    }

    @Override
    public ArrayList<HexCoordinate> getPlayableCoordinates() {
        return this.playableCoordinates;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public void setDisplayGame(DisplayGame displayGame) {
        this.displayGame = displayGame;
    }

    @Override
    public DisplayGame getDisplayGame() {
        return this.displayGame;
    }

    private void updateBorderBank() {
        if (this.currentPlayer == this.player1) {
            this.displayGame.getDisplayBankInsects().switchBorderJ2ToJ1();
        } else {
            this.displayGame.getDisplayBankInsects().switchBorderJ1ToJ2();
        }
    }

    private int checkLoser() {
        boolean lPlayer1 = this.hexGrid.checkLoser(player1);
        boolean lPlayer2 = this.hexGrid.checkLoser(player2);
        if (lPlayer1 && lPlayer2) {
            Log.addMessage("Egalité !");
            return 0;
        } else {
            if (lPlayer1) {
                Log.addMessage("Le joueur " + player1.getColor() + " a perdu !");
                return 2; // return winner
            } else if (lPlayer2) {
                Log.addMessage("Le joueur " + player2.getColor() + " a perdu !");
                return 1; // return winner
            }
        }
        return -1;
    }

    private void switchPlayer() {
        int winner = this.checkLoser();
        if (winner != -1) {
            this.pageManager.getDisplayMain().getDisplayWin().updateWinner(winner);
            this.displayGame.repaint(); //Pour afficher le dernier coup
            this.pageManager.gameAndWin();
        } else {
            this.currentPlayer.incrementTurn();
            if (this.currentPlayer == this.player1) {
                displayGame.getDisplayBankInsects().switchBorderJ1ToJ2();
                this.currentPlayer = this.player2;
            } else {
                displayGame.getDisplayBankInsects().switchBorderJ2ToJ1();
                this.currentPlayer = this.player1;
            }
            if (this.currentPlayer.isAi()) {
                this.delay.start();
            }
        }
    }

    private void switchPlayerHistory() {
        this.displayGame.repaint();
        this.currentPlayer.incrementTurn();
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
        } else {
            this.currentPlayer = this.player1;
        }
        this.displayGame.repaint();
    }

    private void initPlayers() {
        this.player1 = new Player("white", "Inspecteur blanco");
        this.player2 = new Player("black", "Barbe noir");

        Random random = new Random();
        this.currentPlayer = random.nextBoolean() ? player1 : player2;
    }

    public ArrayList<HexCoordinate> generatePlayableInsertionCoordinates(Class<? extends Insect> insectClass, Player player) {
        ArrayList<HexCoordinate> playableCoordinates = new ArrayList<>();
        this.insect = player.getInsect(insectClass);
        if (this.insect != null) {
            if (player.canAddInsect(this.insect.getClass())) {
                if (player.checkBeePlacement(this.insect)) {
                    if (player.getTurn() <= 1) {
                        if (this.hexGrid.getGrid().isEmpty()) {
                            playableCoordinates.add(HexMetrics.hexCenterCoordinate(this.displayGame.getWidth(), this.displayGame.getHeight()));
                        } else {
                            playableCoordinates = player.getInsect(this.insect.getClass()).getPossibleInsertionCoordinatesT1(this.hexGrid);
                        }
                    } else {
                        playableCoordinates = player.getInsect(this.insect.getClass()).getPossibleInsertionCoordinates(this.hexGrid);
                    }
                } else {
                    Log.addMessage("Vous devez placer l'abeille avant de placer un autre insecte");
                }
            } else {
                Log.addMessage("Vous avez atteint le nombre maximum de pions de ce type");
            }
        } else {
            Log.addMessage("Vous n'avez plus de pions de ce type");
        }
        return playableCoordinates;
    }

    @Override
    public void handleCellClicked(HexCell cell, HexCoordinate hexagon) { //Clic sur un insecte du plateau
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.currentPlayer.isAi()) {
            return;
        }
        Insect insect = cell.getTopInsect();

        if (!this.isInsectCellClicked) { //On clique sur un insecte à déplacer
            this.isInsectCellClicked = true;
            this.hexClicked = hexagon;
            // on affiche la pile
            if (this.hexGrid.getGrid().get(this.hexClicked).getInsects().size() >= 2) {
                this.displayGame.getDisplayStack().updateStackClickState(this.isInsectCellClicked, this.hexClicked);
            }

            if (insect.getPlayer().equals(currentPlayer)) {
                this.playableCoordinates = insect.getPossibleMovesCoordinates(this.hexClicked, this.hexGrid);
                if (this.playableCoordinates.isEmpty() && !this.currentPlayer.isBeePlaced()) {
                    Log.addMessage("Aucun déplacement autorisé car l'abeille n'est pas sur le plateau");
                }
                // rendre transparente la case
                this.displayGame.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);

            } else {
                Log.addMessage("Ce pion ne vous appartient pas");
                if (this.hexGrid.getGrid().get(this.hexClicked).getInsects().size() < 2) { //Si c'est une pile ennemie
                    this.isInsectCellClicked = false; //On déselectionne la pile ennemie affichée
                }
            }

        } else {
            HexCell cellClicked = this.hexGrid.getCell(this.hexClicked);
            if (cellClicked.getTopInsect().getClass() == Beetle.class && !hexagon.equals(this.hexClicked)) { //On clique sur un insecte cible d'un scarabée
                this.handleInsectMoved(hexagon);
            } else { //On clique sur un insecte déjà sélectionné
                //On retire la transparence du pion/pile et l'affichage de la pile
                this.isInsectCellClicked = false;
                this.displayGame.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
                this.displayGame.getDisplayStack().updateStackClickState(isInsectCellClicked, hexClicked);
                this.playableCoordinates.clear();
            }
        }
    }

    @Override
    public void handleInsectMoved(HexCoordinate hexagon) {
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.currentPlayer.isAi()) {
            return;
        }
        if (this.playableCoordinates.contains(hexagon)) {
            HexCell cellClicked = this.hexGrid.getCell(this.hexClicked);
            Insect movedInsect = cellClicked.getTopInsect();
            Move move = new Move(movedInsect, this.hexClicked, hexagon);

            this.hexGrid.applyMove(move, this.currentPlayer);
            this.isInsectCellClicked = false;
            this.switchPlayer();
            this.history.addMove(move);
        } else {
            Log.addMessage("Déplacement impossible");
        }
        //On retire la transparence du pion/pile et l'affichage de la pile
        this.isInsectCellClicked = false;
        this.displayGame.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
        this.displayGame.getDisplayStack().updateStackClickState(this.isInsectCellClicked, this.hexClicked);
        this.playableCoordinates.clear();
    }

    @Override
    public void handleInsectPlaced(HexCoordinate hexagon) {
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.currentPlayer.isAi()) {
            return;
        }
        if (this.playableCoordinates.contains(hexagon)) {

            Move move = new Move(this.insect, null, hexagon);
            this.hexGrid.applyMove(move, this.currentPlayer);

            //Modifier le compteur des boutons
            this.displayGame.getDisplayBankInsects().updateAllLabels();

            this.switchPlayer();
            this.history.addMove(move);
        } else {
            Log.addMessage("placement impossible !");
        }
        this.isInsectButtonClicked = false;
        this.displayGame.getDisplayBankInsects().updateButtonClickState(isInsectButtonClicked);
        this.playableCoordinates.clear();
    }

    @Override
    public void clicInsectButton(Class<? extends Insect> insectClass, Player player) {
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.currentPlayer.isAi()) {
            return;
        }
        if (this.isInsectButtonClicked) { //On désélectionne un bouton
            this.isInsectButtonClicked = false;
            this.playableCoordinates.clear();

        } else { //On clique sur un bouton
            this.isInsectButtonClicked = true;
            this.isInsectCellClicked = false;

            Log.addMessage(this.currentPlayer.getName() + " " + this.currentPlayer.getColor() + " " + this.currentPlayer.isAi() + " --- " + player.getName() + " " + player.getColor() + " " + player.isAi());

            // Pas sensé avoir besoin de ça !
            player.setName(this.currentPlayer.getName());

            if (this.currentPlayer.equals(player)) {
                this.playableCoordinates = this.generatePlayableInsertionCoordinates(insectClass, this.currentPlayer);
                if (this.playableCoordinates.isEmpty()) {
                    this.isInsectButtonClicked = false;
                }
            } else {
                Log.addMessage("Pas le bon joueur !");
            }
        }
        this.displayGame.getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
        this.displayGame.getDisplayBankInsects().updateButtonClickState(this.isInsectButtonClicked);
        this.displayGame.repaint();

    }

    @Override
    public void cancelMove() {
        if (this.history.canCancel()) {
            this.playableCoordinates.clear();
            Move move = this.history.cancelMove();
            this.currentPlayer.decrementTurn();
            this.switchPlayerHistory();
            this.currentPlayer.decrementTurn();
            this.hexGrid.unapplyMove(move, this.currentPlayer);
            this.updateBorderBank();
            this.displayGame.getDisplayBankInsects().updateAllLabels();
            this.displayGame.getDisplayHexGrid().updateInsectClickState(false, this.hexClicked);
            this.displayGame.getDisplayStack().updateStackClickState(isInsectCellClicked, hexClicked);
            this.displayGame.repaint();
            if (this.currentPlayer.isAi()) {
                this.cancelMove();
            }
        } else {
            Log.addMessage("no move to cancel");
        }
    }

    @Override
    public void redoMove() {
        if (this.history.canRedo()) {
            this.playableCoordinates.clear();
            Move move = this.history.redoMove();
            this.hexGrid.applyMove(move, this.currentPlayer);
            this.switchPlayerHistory();
            this.updateBorderBank();
            this.displayGame.getDisplayBankInsects().updateAllLabels();
            this.displayGame.getDisplayHexGrid().updateInsectClickState(false, this.hexClicked);
            this.displayGame.repaint();
            if (this.currentPlayer.isAi()) {
                this.redoMove();
            }
        } else {
            Log.addMessage("no move to redo");
        }
    }

    @Override
    public void saveGame() {
        try {
            String fileName = SaveLoad.saveGame(this.history, this.player1, this.player2, this.currentPlayer);
            Log.addMessage("Partie sauvegardée dans le fichier : " + fileName);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean loadGame(String fileName) {
        try {
            this.playableCoordinates.clear();
            this.isInsectButtonClicked = false;
            this.isInsectCellClicked = false;
            this.hexClicked = null;

            SaveLoad saveLoad = SaveLoad.loadGame(fileName);
            this.history = saveLoad.getHistory();
            this.player1 = saveLoad.getPlayer1();
            this.player2 = saveLoad.getPlayer2();
            this.currentPlayer = saveLoad.getCurrentPlayer();

            // Create a new HexGrid
            this.hexGrid = new HexGrid();

            // Apply each move in the history to the hexGrid
            for (Move move : this.history.getHistory()) {
                this.hexGrid.applyMove(move, move.getInsect().getPlayer());
            }

            if (this.player1.isAi()) {
                this.player1.getAi().setGameActionHandler(this);
            }
            if (this.player2.isAi()) {
                this.player2.getAi().setGameActionHandler(this);
            }
            if (this.player1.isAi() || this.player2.isAi()) {
                this.delay.start();
            }

            this.updateBorderBank();
            this.displayGame.getDisplayBankInsects().updateAllLabels();
            this.displayGame.repaint();

            Log.addMessage("Partie chargée depuis le fichier : " + fileName);
            return true;
        } catch (Exception ex) {
            Log.addMessage("Erreur lors du chargement de la partie : " + ex.getMessage());
            return false;
        }
    }

    @Override
    public void restartGameWithSamePlayers() {
        this.stopAi();
        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
        this.hexGrid = new HexGrid();
        this.history = new History();
        this.player1.reset();
        this.player2.reset();
        Random random = new Random();
        this.currentPlayer = random.nextBoolean() ? player1 : player2;
        this.updateBorderBank();
        HexMetrics.resetHexMetricsWidth();
        ViewMetrics.resetViewPosition();
        this.startAi();
        this.displayGame.getDisplayBankInsects().updateAllLabels();
        this.displayGame.repaint();
    }

    @Override
    public void startGame() {
        this.hexGrid = new HexGrid();
        this.initPlayers();

        this.isInsectButtonClicked = false;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
        this.playableCoordinates = new ArrayList<>();
        this.history = new History();
        this.startAi();
        this.updateBorderBank();
        HexMetrics.resetHexMetricsWidth();
        ViewMetrics.resetViewPosition();
        this.displayGame.getDisplayBankInsects().updateAllLabels();
        this.displayGame.repaint();
    }
}