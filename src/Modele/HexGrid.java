package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Modele.Insect.Insect;
import Structures.HexCoordinate;

public class HexGrid  implements Cloneable {
    private Map<HexCoordinate, HexCell> grid;
    private int insectsCount;

    public HexGrid() {
        this.grid = new HashMap<>();
        this.insectsCount=0;
    }

    public HexGrid(HexGrid h) {
        this.grid = new HashMap<>(h.grid);
        this.insectsCount=h.insectsCount;
    }

    public Map<HexCoordinate, HexCell> getGrid() {
        return grid;
    }

    public HexCell getCell(int x, int y) {
        return this.grid.get(new HexCoordinate(x, y));
    }

    public int getInsectsCount()
    {
        return this.insectsCount;
    }

    public void addCell(int x, int y, Insect insect) {
        HexCell cell = new HexCell();
        cell.addInsect(insect);
        this.grid.put(new HexCoordinate(x, y), cell);
    }

    public void removeCell(int x, int y){
        this.grid.remove(new HexCoordinate(x, y));
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

    public int neighboursCount(int x, int y)
    {
        int counter=0;
        HexCell tmp = this.getAdj(x, y, "NO");
        if (tmp != null && tmp.getInsects().size()>0) counter++;
        tmp = this.getAdj(x, y, "NE");
        if (tmp != null && tmp.getInsects().size()>0) counter++;
        tmp = this.getAdj(x, y, "E");
        if (tmp != null && tmp.getInsects().size()>0) counter++;
        tmp = this.getAdj(x, y, "SE");
        if (tmp != null && tmp.getInsects().size()>0) counter++;
        tmp = this.getAdj(x, y, "SO");
        if (tmp != null && tmp.getInsects().size()>0) counter++;
        tmp = this.getAdj(x, y, "O");
        if (tmp != null && tmp.getInsects().size()>0) counter++;
        return counter;
    }

    public ArrayList<HexCoordinate> getNeighbours(int x, int y)
    {
        ArrayList<HexCoordinate> neighbours = new ArrayList<>();
        HexCell tmp = this.getAdj(x, y, "NO");
        if (tmp != null && tmp.getInsects().size()>0) neighbours.add(new HexCoordinate(x, y-1));
        tmp = this.getAdj(x, y, "NE");
        if (tmp != null && tmp.getInsects().size()>0) neighbours.add(new HexCoordinate(x+1, y-1));
        tmp = this.getAdj(x, y, "E");
        if (tmp != null && tmp.getInsects().size()>0) neighbours.add(new HexCoordinate(x+1, y));
        tmp = this.getAdj(x, y, "SE");
        if (tmp != null && tmp.getInsects().size()>0) neighbours.add(new HexCoordinate(x, y+1));
        tmp = this.getAdj(x, y, "SO");
        if (tmp != null && tmp.getInsects().size()>0) neighbours.add(new HexCoordinate(x-1, y+1));
        tmp = this.getAdj(x, y, "O");
        if (tmp != null && tmp.getInsects().size()>0) neighbours.add(new HexCoordinate(x-1, y));
        return neighbours;
    }

    public HexGrid clone()
    {
        return new HexGrid(this);
    }

}
