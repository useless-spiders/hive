package Modele;

import java.util.HashMap;
import java.util.Map;
import Structures.HexCoordinate;

public class HexGrid {
    private Map<HexCoordinate, HexCell> grid;

    public HexGrid() {
        this.grid = new HashMap<>();
    }

    public Map<HexCoordinate, HexCell> getGrid() {
        return grid;
    }

    public HexCell getCell(int x, int y) {
        return this.grid.get(new HexCoordinate(x, y));
    }

    public void setCell(int x, int y, int type) {
        HexCell cell = new HexCell(type);
        this.grid.put(new HexCoordinate(x, y), cell);
    }

    public HexCell getAdj(int x, int y, String dir) {
        switch (dir) {
            case "NO":
                y -= 1;
                break;
            case "NE":
                x += 1;
                y -= 1;
                break;
            case "E":
                x += 1;
                break;
            case "SE":
                y += 1;
                break;
            case "SO":
                x -= 1;
                y += 1;
                break;
            case "O":
                x -= 1;
                break;
            default:
                x = y = 0; //cas pas possible en theorie
                break;
        }
        return getCell(x, y);
    }

}
