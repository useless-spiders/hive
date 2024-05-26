package Controller;

import Model.HexGrid;
import Model.Move;
import Model.SaveLoad;
import Pattern.GameActionHandler;
import Structure.HexMetrics;
import Structure.Log;
import Structure.ViewMetrics;

import java.util.ArrayList;

public class SaveLoadController {
    private GameActionHandler gameActionHandler;

    public SaveLoadController(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    public void saveGame() {
        try {
            String fileName = SaveLoad.saveGame(this.gameActionHandler.getHistoryController().getHistory(), this.gameActionHandler.getPlayerController().getPlayer1(), this.gameActionHandler.getPlayerController().getPlayer2(), this.gameActionHandler.getPlayerController().getCurrentPlayer());
            Log.addMessage("Partie sauvegardée dans le fichier : " + fileName);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean loadGame(String fileName) {
        try {
            this.gameActionHandler.getGameActionListener().setPlayableCoordinates(new ArrayList<>());
            this.gameActionHandler.getGameActionListener().setIsInsectButtonClicked(false);
            this.gameActionHandler.getGameActionListener().setIsInsectCellClicked(false);
            this.gameActionHandler.getGameActionListener().setHexClicked(null);

            SaveLoad saveLoad = SaveLoad.loadGame(fileName);
            this.gameActionHandler.getHistoryController().setHistory(saveLoad.getHistory());
            this.gameActionHandler.getPlayerController().setPlayer1(saveLoad.getPlayer1());
            this.gameActionHandler.getPlayerController().setPlayer2(saveLoad.getPlayer2());
            this.gameActionHandler.getPlayerController().setCurrentPlayer(saveLoad.getCurrentPlayer());


            // Create a new HexGrid
            this.gameActionHandler.setGrid(new HexGrid());

            // Apply each move in the history to the hexGrid
            for (Move move : this.gameActionHandler.getHistoryController().getHistory().getHistory()) {
                this.gameActionHandler.getGrid().applyMove(move, move.getInsect().getPlayer());
            }

            if (this.gameActionHandler.getPlayerController().getPlayer1().isAi()) {
                this.gameActionHandler.getPlayerController().getPlayer1().getAi().setGameActionHandler(this.gameActionHandler);
            }
            if (this.gameActionHandler.getPlayerController().getPlayer2().isAi()) {
                this.gameActionHandler.getPlayerController().getPlayer1().getAi().setGameActionHandler(this.gameActionHandler);
            }
            if (this.gameActionHandler.getPlayerController().getPlayer1().isAi() || this.gameActionHandler.getPlayerController().getPlayer2().isAi()) {
                this.gameActionHandler.getAiController().getDelay().start();
            }

            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateBorderBank();
            HexMetrics.resetHexMetricsWidth();
            ViewMetrics.resetViewPosition();
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateButtons();
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateAllLabels();
            this.gameActionHandler.getDisplayGame().repaint();

            Log.addMessage("Partie chargée depuis le fichier : " + fileName);
            return true;
        } catch (Exception ex) {
            Log.addMessage("Erreur lors du chargement de la partie : " + ex.getMessage());
            return false;
        }
    }
}
