package Pattern;

import Model.HexCell;
import Model.HexGrid;
import Model.History;
import Model.Insect.Insect;
import Model.Player;
import Structure.HexCoordinate;
import View.DisplayGame;
import View.DisplayWin;

import java.util.ArrayList;

public interface GameActionHandler {
    void clicInsectButton(Class<? extends Insect> insectClass, Player player);

    Player getCurrentPlayer();

    ArrayList<HexCoordinate> getPlayableCoordinates();

    Player getPlayer1();

    Player getPlayer2();

    void cancelMove();

    void redoMove();

    HexGrid getGrid();

    void setDisplayGame(DisplayGame displayGame);

    DisplayGame getDisplayGame();

    boolean getIsInsectButtonClicked();

    boolean getIsInsectCellClicked();

    void handleCellClicked(HexCell cell, HexCoordinate hexagon);

    void handleInsectMoved(HexCoordinate hexagon);

    void handleInsectPlaced(HexCoordinate hexagon);

    History getHistory();

    ArrayList<HexCoordinate> generatePlayableCoordinates(Class<? extends Insect> insectClass, Player player);

    void setPlayer(int player, String name);

    void startAi();

    void setPlayer(int player, String name);

}
