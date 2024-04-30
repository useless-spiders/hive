package Modele;

import java.util.HashMap;
import java.util.Map;

public class HexGrid {
    private Map<HexCoordinate, HexCell> grid;

    public HexGrid() {
        this.grid = new HashMap<>();
    }

    public HexCell getCell(HexCoordinate coord) {
        return this.grid.get(coord);
    }

    public void setCell(HexCoordinate coord, HexCell cell) {
        this.grid.put(coord, cell);
    }

    public HexCell getAdj(HexCoordinate coord,String Dir){
        int x,y;
        switch(Dir){
            case "NE":
                x = coord.getX();
                y = coord.getY() - 1;
                break;
            case "NO":
                x = coord.getX() + 1;
                y = coord.getY() -1;
                break;
            case "O":
                x = coord.getX();
                y = coord.getY() -1;
                break;
            case "SO":
                x = coord.getX() + 1;
                y = coord.getY();
                break;
            case "SE":
                x = coord.getX() - 1;
                y = coord.getY() + 1;
                break;
            case "E":
                x = coord.getX() - 1;
                y = coord.getY();
                break;
        }
        return getCell(HexCoordinate(x,y));
    }

    

}
