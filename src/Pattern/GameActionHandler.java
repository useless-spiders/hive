package Pattern;

import Model.Insect.Insect;
import Model.Player;
import Structure.HexCoordinate;

import java.util.ArrayList;

public interface GameActionHandler {
    void clicInsectButton(Class<? extends Insect> insectClass, Player player);

    Player getCurrentPlayer();

    ArrayList<HexCoordinate> getPlayableCoordinates();

    Player getPlayer1();

    Player getPlayer2();

    void cancelMove();

    void redoMove();


}
