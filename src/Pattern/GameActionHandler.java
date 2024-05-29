package Pattern;

import Controller.*;
import Listener.GameActionListener;
import Model.HexGrid;
import View.DisplayGame;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface pour les actions du jeu
 */
public interface GameActionHandler {

    ResourceBundle getLang();

    HexGrid getGrid();

    void setGrid(HexGrid hexGrid);

    void setDisplayGame(DisplayGame displayGame);

    DisplayGame getDisplayGame();

    void restartGameWithSamePlayers();

    void resetGame();

    void setIsFirstStart(boolean isFirstStart);

    PlayerController getPlayerController();

    AiController getAiController();

    MoveController getMoveController();

    HistoryController getHistoryController();

    GameActionListener getGameActionListener();

    SaveLoadController getSaveLoadController();

    PageController getPageController();

    boolean getIsFirstStart();

    void setLang(Locale language);

    Locale getCurrentLocale();

}
