package Pattern;

import Modele.Insect.Insect;
import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;

public interface GameActionHandler {
    void clicInsectButton(Insect insect);
    Player getCurrentPlayer();
    ArrayList<HexCoordinate> getPlayableCells();
}
