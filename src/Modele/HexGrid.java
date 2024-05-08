package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import Modele.Insect.Bee;
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

    public HexCell getCell(HexCoordinate h) {
        return this.grid.get(h);
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

    public HexGrid clone()
    {
        return new HexGrid(this);
    }

    public HashMap<HexCoordinate, String> getNeighbors(HexCoordinate cell) {
        HashMap<HexCoordinate, String> neighbors = new HashMap<>();
        String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
        int[] dx = {0, 1, 1, 0, -1, -1};
        int[] dy = {-1, -1, 0, 1, 1, 0};

        for (int i = 0; i < directions.length; i++) {
            HexCoordinate next = new HexCoordinate(cell.getX() + dx[i], cell.getY() + dy[i]);
            if (this.getAdj(cell.getX(), cell.getY(), directions[i]) != null) {
                neighbors.put(next, directions[i]);
            }
        }

        return neighbors;
    }

    public boolean isHiveConnectedAfterMove(HexCoordinate from, HexCoordinate to) {
        // Create a copy of the current HexGrid
        HexGrid tempGrid = this.clone();

        // Make the move on the temporary grid
        Insect insect = tempGrid.getCell(from.getX(), from.getY()).getTopInsect();
        tempGrid.removeCell(from.getX(), from.getY());

        boolean isConnected1 = tempGrid.isHiveConnected();

        tempGrid.addCell(to.getX(), to.getY(), insect);

        boolean isConnected2 = tempGrid.isHiveConnected();

        return isConnected1 && isConnected2;
    }

    public boolean isHiveConnected() {
        HexCoordinate start = this.getGrid().keySet().iterator().next();
        HashSet<HexCoordinate> visited = new HashSet<>();

        if (this.getGrid().isEmpty()) {
            return true;
        }

        dfs(start, visited);

        // If all insects were visited, the hive is connected
        return visited.size() == this.getGrid().size();
    }

    private void dfs(HexCoordinate current, HashSet<HexCoordinate> visited) {
        String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
        int[] dx = {0, 1, 1, 0, -1, -1};
        int[] dy = {-1, -1, 0, 1, 1, 0};

        visited.add(current);

        for (int i = 0; i < directions.length; i++) {
            HexCoordinate next = new HexCoordinate(current.getX() + dx[i], current.getY() + dy[i]);
            if (!visited.contains(next) && this.getAdj(current.getX(), current.getY(), directions[i]) != null) {
                dfs(next, visited);
            }
        }
    }

    public boolean checkLoser(Player player) {
        for (HexCoordinate h : this.getGrid().keySet()) {
            Insect insect = this.getCell(h).getTopInsect();
            if (insect instanceof Bee && insect.getPlayer() == player) {
                return this.getNeighbors(h).size() == 6;
            }
        }
        return false;
    }

}
