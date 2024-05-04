package Modele.Insect;

import Structures.HexCoordinate;
import java.util.ArrayList;
import Modele.HexGrid;

public class Grasshopper extends Insect{

    private static final int MAX = 6;
    private String color;

    public Grasshopper(String color){
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
    public ArrayList<HexCoordinate> playableCells(int x, int y, HexGrid g){ //Fait
        ArrayList<HexCoordinate> jouable = new ArrayList<>();
        int nx = x,ny = y;

        while(g.getAdj(x, y, "NO") != null){
            ny+=1;
        }
        jouable.add(new HexCoordinate(x, y-ny));

        while(g.getAdj(x, y, "NE") != null){
            ny+=1;
            nx+=1;
        }
        jouable.add(new HexCoordinate(x+nx, y-ny));

        while(g.getAdj(x, y, "E") != null){
            nx+=1;
        }
        jouable.add(new HexCoordinate(x+nx, y));

        while(g.getAdj(x, y, "SE") != null){
            ny+=1;
        }
        jouable.add(new HexCoordinate(x, y+ny));

        while(g.getAdj(x, y, "SO") != null){
            ny+=1;
            nx +=1;
        }
        jouable.add(new HexCoordinate(x-nx, y+ny));

        while(g.getAdj(x, y, "O") != null){
            nx+=1;
        }
        jouable.add(new HexCoordinate(x-nx, y));

        return jouable;
    }
}