package Modele.Insect;

import Modele.Player;
import Structures.HexCoordinate;
import java.util.ArrayList;

import Modele.HexGrid;

public class Ant extends Insect{

    private static final int MAX = 6;

    public Ant(Player player) {
        super(player);
    }

    @Override
    public int getMax(){
        return MAX;
    }

    @Override
    public ArrayList<HexCoordinate> playableCells(int x, int y, HexGrid g){ //A faire
        ArrayList<HexCoordinate> jouable = new ArrayList<>();
        if(g.getAdj(x, y, "NO") == null)jouable.add(new HexCoordinate(x, y-1));
        if(g.getAdj(x, y, "NE") == null)jouable.add(new HexCoordinate(x+1, y-1));
        if(g.getAdj(x, y, "E") == null)jouable.add(new HexCoordinate(x+1, y));
        if(g.getAdj(x, y, "SE") == null)jouable.add(new HexCoordinate(x, y+1));
        if(g.getAdj(x, y, "SO") == null)jouable.add(new HexCoordinate(x-1, y+1));
        if(g.getAdj(x, y, "O") == null)jouable.add(new HexCoordinate(x-1, y));

        return jouable;
    }
}