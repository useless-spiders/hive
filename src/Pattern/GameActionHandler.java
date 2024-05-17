package Pattern;

import Model.HexGrid;
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

    void mouseMoved(int x, int y);

    void mousePressed(int x, int y);

    void mouseDragged(int x, int y);

}
