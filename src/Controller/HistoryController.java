package Controller;

import Model.History;
import Model.Move;
import Pattern.GameActionHandler;
import Structure.Log;

import java.util.ArrayList;

public class HistoryController {
    private GameActionHandler gameActionHandler;
    private History history;

    public HistoryController(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.history = new History();
    }

    public History getHistory() {
        return this.history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public void cancelMove() {
        if (this.history.canCancel()) {
            this.gameActionHandler.getGameActionListener().setPlayableCoordinates(new ArrayList<>());
            Move move = this.history.cancelMove();
            this.gameActionHandler.getPlayerController().getCurrentPlayer().decrementTurn();
            this.gameActionHandler.getPlayerController().switchPlayer();
            this.gameActionHandler.getPlayerController().getCurrentPlayer().decrementTurn();
            this.gameActionHandler.getGrid().unapplyMove(move, this.gameActionHandler.getPlayerController().getCurrentPlayer());
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateBorderBank();
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateAllLabels();
            this.gameActionHandler.getDisplayGame().getDisplayHexGrid().updateInsectClickState(false, this.gameActionHandler.getGameActionListener().getHexClicked());
            this.gameActionHandler.getDisplayGame().getDisplayStack().updateStackClickState(this.gameActionHandler.getGameActionListener().getIsInsectCellClicked(), this.gameActionHandler.getGameActionListener().getHexClicked());
            this.gameActionHandler.getDisplayGame().repaint();
            if (this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi()) {
                this.cancelMove();
            }
        } else {
            Log.addMessage("no move to cancel");
        }
    }

    public void redoMove() {
        if (this.history.canRedo()) {
            this.gameActionHandler.getGameActionListener().setPlayableCoordinates(new ArrayList<>());
            Move move = this.history.redoMove();
            this.gameActionHandler.getGrid().applyMove(move, this.gameActionHandler.getPlayerController().getCurrentPlayer());
            this.gameActionHandler.getPlayerController().switchPlayer();
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateBorderBank();
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateAllLabels();
            this.gameActionHandler.getDisplayGame().getDisplayHexGrid().updateInsectClickState(false, this.gameActionHandler.getGameActionListener().getHexClicked());
            this.gameActionHandler.getDisplayGame().repaint();
            if (this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi()) {
                this.redoMove();
            }
        } else {
            Log.addMessage("no move to redo");
        }
    }

    public void addMove(Move move) {
        this.history.addMove(move);
    }
}
