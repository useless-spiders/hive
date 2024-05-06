package Modele.Insect;

import Modele.Player;
import Structures.HexCoordinate;
import java.util.ArrayList;
import Modele.HexGrid;

public class Beetle extends Insect{

    private static final int MAX = 2;

    public Beetle(Player player){
        super(player);
    }

    @Override
    public int getMax(){
        return MAX;
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCells(int x, int y, HexGrid g) { // A faire
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        if (canMoveInsect(g, this.getPlayer())) {
            String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
            int[] dx = {0, 1, 1, 0, -1, -1};
            int[] dy = {-1, -1, 0, 1, 1, 0};

            for (int i = 0; i < directions.length; i++) {
                if (g.getAdj(x, y, directions[i]) == null) {
                    coordinates.add(new HexCoordinate(x + dx[i], y + dy[i]));
                }
            }
        }
        return coordinates;
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleInsertionCells(HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();

        return coordinates;
    }
}