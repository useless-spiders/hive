package Controller;

import Listener.GameActionListener;
import Model.*;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.HexMetrics;
import Structure.ViewMetrics;
import View.DisplayGame;

import java.util.ArrayList;

public class Game implements GameActionHandler {
    private HexGrid hexGrid;
    private PageActionHandler pageActionHandler;
    private boolean isFirstStart = true;
    private PlayerController playerController;
    private AiController aiController;
    private MoveController moveController;
    private HistoryController historyController;
    private GameActionListener gameActionListener;
    private SaveLoadController saveLoadController;
    private DisplayGame displayGame;

    public Game() {
        this.hexGrid = new HexGrid();
        this.playerController = new PlayerController(this);
        this.playerController.initPlayers();

        this.aiController = new AiController(this);
        this.aiController.startAi();

        this.moveController = new MoveController(this);
        this.historyController = new HistoryController(this);
        this.pageActionHandler = new PageController(this);
        this.gameActionListener = new GameActionListener(this);
        this.saveLoadController = new SaveLoadController(this);

        this.displayGame.getDisplayBankInsects().updateBorderBank();
        HexMetrics.resetHexMetricsWidth();
        ViewMetrics.resetViewPosition();
        this.displayGame.getDisplayBankInsects().updateAllLabels();
        this.displayGame.repaint();
    }

    @Override
    public PageActionHandler getPageActionHandler() {
        return this.pageActionHandler;
    }

    @Override
    public PlayerController getPlayerController() {
        return this.playerController;
    }

    @Override
    public AiController getAiController() {
        return this.aiController;
    }

    @Override
    public MoveController getMoveController() {
        return this.moveController;
    }

    @Override
    public HistoryController getHistoryController() {
        return this.historyController;
    }

    @Override
    public GameActionListener getGameActionListener() {
        return this.gameActionListener;
    }

    @Override
    public SaveLoadController getSaveLoadController() {
        return this.saveLoadController;
    }

    @Override
    public boolean getIsFirstStart() {
        return this.isFirstStart;
    }

    @Override
    public void setIsFirstStart(boolean isFirstStart) {
        this.isFirstStart = isFirstStart;
    }

    @Override
    public HexGrid getGrid() {
        return this.hexGrid;
    }

    @Override
    public void setGrid(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
    }

    @Override
    public void setDisplayGame(DisplayGame displayGame) {
        this.displayGame = displayGame;
    }

    @Override
    public DisplayGame getDisplayGame() {
        return this.displayGame;
    }

    @Override
    public void restartGameWithSamePlayers() {
        this.aiController.stopAi();
        this.gameActionListener.setIsInsectButtonClicked(false);
        this.gameActionListener.setIsInsectCellClicked(false);
        this.gameActionListener.setHexClicked(null);
        this.hexGrid = new HexGrid();
        this.historyController.setHistory(new History());
        this.getPlayerController().resetPlayers();
        if (this.getPlayerController().getPlayer1().getColor() == Player.WHITE) {
            this.getPlayerController().setCurrentPlayer(this.getPlayerController().getPlayer1());
        } else {
            this.getPlayerController().setCurrentPlayer(this.getPlayerController().getPlayer2());
        }
        this.displayGame.getDisplayBankInsects().updateBorderBank();
        HexMetrics.resetHexMetricsWidth();
        ViewMetrics.resetViewPosition();
        this.aiController.startAi();
        this.displayGame.getDisplayBankInsects().updateAllLabels();
        this.displayGame.repaint();
    }

    @Override
    public void resetGame() {
        this.hexGrid = new HexGrid();
        this.getPlayerController().initPlayers();
        this.gameActionListener.setIsInsectButtonClicked(false);
        this.gameActionListener.setIsInsectCellClicked(false);
        this.gameActionListener.setHexClicked(null);
        this.gameActionListener.setPlayableCoordinates(new ArrayList<>());
        this.historyController.setHistory(new History());
        this.aiController.startAi();
        HexMetrics.resetHexMetricsWidth();
        ViewMetrics.resetViewPosition();
        this.displayGame.getDisplayBankInsects().updateButtons();
        this.displayGame.getDisplayBankInsects().updateBorderBank();
        this.displayGame.getDisplayBankInsects().updateAllLabels();
        this.displayGame.repaint();
    }
}