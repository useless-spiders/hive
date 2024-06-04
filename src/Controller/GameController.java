package Controller;

import Global.Configuration;
import Listener.GameActionListener;
import Model.HexGrid;
import Model.History;
import Pattern.GameActionHandler;
import Structure.HexMetrics;
import Structure.ViewMetrics;
import View.DisplayGame;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controleur principal pour le jeu
 */
public class GameController implements GameActionHandler {
    private final PlayerController playerController;
    private final AiController aiController;
    private final MoveController moveController;
    private final HistoryController historyController;
    private final GameActionListener gameActionListener;
    private final SaveLoadController saveLoadController;
    private final PageController pageController;
    private HexGrid hexGrid;
    private boolean isFirstStart = true;
    private DisplayGame displayGame;
    private ResourceBundle lang;
    private Locale currentLocale;

    /**
     * Constructeur
     */
    public GameController() {
        this.lang = ResourceBundle.getBundle(Configuration.LANGUAGE_PATH + Configuration.LANGUAGE_FILENAME, Configuration.DEFAULT_LANGUAGE);
        this.currentLocale = Configuration.DEFAULT_LANGUAGE;

        this.hexGrid = new HexGrid();
        this.playerController = new PlayerController(this);
        this.playerController.initPlayers();

        this.aiController = new AiController(this);
        this.aiController.startAi();

        this.moveController = new MoveController(this);
        this.historyController = new HistoryController(this);
        this.pageController = new PageController(this);
        this.gameActionListener = new GameActionListener(this);
        this.saveLoadController = new SaveLoadController(this);

        this.displayGame.getDisplayBankInsects().updateBorderBank();
        HexMetrics.resetHexMetricsWidth();
        ViewMetrics.resetViewPosition();
        this.displayGame.getDisplayBankInsects().updateAllLabels();
        this.displayGame.repaint();
    }

    /**
     * Renvoie le bundle pour gérer les messages dans plusieurs langues
     *
     * @return ResourceBundle
     */
    public ResourceBundle getLang() {
        return this.lang;
    }

    /**
     * Change la langue
     *
     * @param language String
     */
    public void setLang(Locale language) {
        this.currentLocale = language;
        this.lang = ResourceBundle.getBundle(Configuration.LANGUAGE_PATH + Configuration.LANGUAGE_FILENAME, language);
    }

    /**
     * Renvoie la langue actuelle
     *
     * @return Locale
     */
    public Locale getCurrentLocale() {
        return this.currentLocale;
    }

    /**
     * Renvoie le controleur de page
     *
     * @return PageController
     */
    @Override
    public PageController getPageController() {
        return this.pageController;
    }

    /**
     * Renvoie le controleur de joueur
     *
     * @return PlayerController
     */
    @Override
    public PlayerController getPlayerController() {
        return this.playerController;
    }

    /**
     * Renvoie le controleur de l'IA
     *
     * @return AiController
     */
    @Override
    public AiController getAiController() {
        return this.aiController;
    }

    /**
     * Renvoie le controleur de mouvement
     *
     * @return MoveController
     */
    @Override
    public MoveController getMoveController() {
        return this.moveController;
    }

    /**
     * Renvoie le controleur d'historique
     *
     * @return HistoryController
     */
    @Override
    public HistoryController getHistoryController() {
        return this.historyController;
    }

    /**
     * Renvoie le controleur d'action de jeu
     *
     * @return GameActionListener
     */
    @Override
    public GameActionListener getGameActionListener() {
        return this.gameActionListener;
    }

    /**
     * Renvoie le controleur de sauvegarde
     *
     * @return SaveLoadController
     */
    @Override
    public SaveLoadController getSaveLoadController() {
        return this.saveLoadController;
    }

    /**
     * Renvoie si c'est le premier démarrage
     *
     * @return boolean
     */
    @Override
    public boolean getIsFirstStart() {
        return this.isFirstStart;
    }

    /**
     * Change l'état du premier démarrage
     *
     * @param isFirstStart boolean
     */
    @Override
    public void setIsFirstStart(boolean isFirstStart) {
        this.isFirstStart = isFirstStart;
    }

    /**
     * Renvoie la grille
     *
     * @return HexGrid
     */
    @Override
    public HexGrid getGrid() {
        return this.hexGrid;
    }

    /**
     * Change la grille
     *
     * @param hexGrid HexGrid
     */
    @Override
    public void setGrid(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
    }

    /**
     * Renvoie le display du jeu
     *
     * @return DisplayGame
     */
    @Override
    public DisplayGame getDisplayGame() {
        return this.displayGame;
    }

    /**
     * Change le display du jeu
     *
     * @param displayGame DisplayGame
     */
    @Override
    public void setDisplayGame(DisplayGame displayGame) {
        this.displayGame = displayGame;
    }

    /**
     * Réinitalise le jeu avec les mêmes joueurs
     */
    @Override
    public void restartGameWithSamePlayers() {
        this.aiController.stopAi();
        this.gameActionListener.setIsInsectButtonClicked(false);
        this.gameActionListener.setIsInsectCellClicked(false);
        this.gameActionListener.setHexClicked(null);
        this.hexGrid = new HexGrid();
        this.historyController.setHistory(new History());
        this.getPlayerController().resetPlayers();
        if (this.getPlayerController().getPlayer1().getColor() == Configuration.PLAYER_WHITE) {
            this.getPlayerController().setCurrentPlayer(this.getPlayerController().getPlayer1());
        } else {
            this.getPlayerController().setCurrentPlayer(this.getPlayerController().getPlayer2());
        }
        if(this.getPlayerController().getPlayer1().isAi() || this.getPlayerController().getPlayer2().isAi()){
            this.aiController.startAi();
        }
        HexMetrics.resetHexMetricsWidth();
        ViewMetrics.resetViewPosition();
        this.aiController.startAi();
        this.displayGame.getDisplayBankInsects().updateAllLabels();
        this.displayGame.getDisplayBankInsects().updateBorderBank();
        this.displayGame.repaint();
    }

    /**
     * Réinitialise le jeu
     */
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