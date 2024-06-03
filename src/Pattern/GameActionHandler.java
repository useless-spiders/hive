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

    void setLang(Locale language);

    HexGrid getGrid();

    void setGrid(HexGrid hexGrid);

    DisplayGame getDisplayGame();

    void setDisplayGame(DisplayGame displayGame);

    void restartGameWithSamePlayers();

    void resetGame();

    PlayerController getPlayerController();

    AiController getAiController();

    MoveController getMoveController();

    HistoryController getHistoryController();

    GameActionListener getGameActionListener();

    SaveLoadController getSaveLoadController();

    PageController getPageController();

    boolean getIsFirstStart();

    void setIsFirstStart(boolean isFirstStart);

    Locale getCurrentLocale();

}
