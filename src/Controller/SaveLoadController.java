package Controller;

import Model.HexGrid;
import Model.Move;
import Model.SaveLoad;
import Pattern.GameActionHandler;
import Structure.HexMetrics;
import Structure.Log;
import Structure.ViewMetrics;

import java.text.MessageFormat;


/**
 * Controleur pour la sauvegarde et le chargement de partie
 */
public class SaveLoadController {
    private GameActionHandler gameActionHandler;

    /**
     * Constructeur
     * @param gameActionHandler GameActionHandler
     */
    public SaveLoadController(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    /**
     * Sauvegarde la partie
     */
    public void saveGame() {
        try {
            String fileName = SaveLoad.saveGame(this.gameActionHandler.getHistoryController().getHistory(), this.gameActionHandler.getPlayerController().getPlayer1(), this.gameActionHandler.getPlayerController().getPlayer2(), this.gameActionHandler.getPlayerController().getCurrentPlayer());
            Log.addMessage(MessageFormat.format(this.gameActionHandler.getMessages().getString("save.success"), fileName));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Charge une partie
     * @param fileName String
     * @return boolean
     */
    public boolean loadGame(String fileName) {
        try {

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
                this.gameActionHandler.getPlayerController().getPlayer2().getAi().setGameActionHandler(this.gameActionHandler);
            }
            if (this.gameActionHandler.getPlayerController().getPlayer1().isAi() || this.gameActionHandler.getPlayerController().getPlayer2().isAi()) {
                this.gameActionHandler.getAiController().startAi();
            }

            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateBorderBank();
            HexMetrics.resetHexMetricsWidth();
            ViewMetrics.resetViewPosition();
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateButtons();
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateAllLabels();
            this.gameActionHandler.getDisplayGame().repaint();

            Log.addMessage(MessageFormat.format(this.gameActionHandler.getMessages().getString("load.success"), fileName));
            return true;
        } catch (Exception ex) {
            Log.addMessage(this.gameActionHandler.getMessages().getString("load.error") + ex);
            return false;
        }
    }
}
