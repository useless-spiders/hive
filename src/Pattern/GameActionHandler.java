package Pattern;

import Modele.Insect.Insect;
import Modele.Player;
import Structures.HexCoordinate;

import javax.swing.*;
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
