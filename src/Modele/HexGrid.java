package Modele;

import java.util.HashMap;
import java.util.Map;

public class HexGrid {
    private Map<HexCoordinate, HexCell> grid;

    public HexGrid() {
        this.grid = new HashMap<>();
    }

    public HexCell getCell(int x, int y) {
        return this.grid.get(new HexCoordinate(x, y));
    }

    public void setCell(int x, int y, HexCell cell) {
        this.grid.put(new HexCoordinate(x, y), cell);
    }

    public HexCell getAdj(int x,int y, String dir) {
        switch (dir) {
            case "NE":
                y -= 1;
                break;
            case "NO":
                x += 1;
                y -= 1;
                break;
            case "O":
                y -= 1;
                break;
            case "SO":
                x += 1;
                break;
            case "SE":
                x -= 1;
                y += 1;
                break;
            case "E":
                x -= 1;
                break;
            default:
                x = y = 0; //cas pas possible en theorie
                break;
        }
        return getCell(x, y);
    }

    public class HexCoordinate {
        private int x;
        private int y;
    
        public HexCoordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    
        public int getX(){
            return x;
        }
    
        public int getY(){
            return y;
        }
    
    }


}
