package Modele.Insect;

import Structures.HexCoordinate;
import java.util.ArrayList;
import Modele.HexGrid;

public class Beetle extends Insect{

    private static final int MAX = 4;
    private String color;

    public Beetle(String color){
        this.color = color;
    }

    @Override
    public int getMax(){
        return MAX;
    }

    @Override
    public String getImageName(){
        return this.getClass().getSimpleName() + "_" + this.color + ".png";
    }

    @Override
    public ArrayList<HexCoordinate> playableCells(int x, int y, HexGrid g){//A faire
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