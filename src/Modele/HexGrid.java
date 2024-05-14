package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import Modele.Insect.Bee;
import Modele.Insect.Insect;
import Structures.HexCoordinate;

public class HexGrid implements Cloneable {
    private Map<HexCoordinate, HexCell> grid;
    private int insectsCount;

    public HexGrid() {
        this.grid = new HashMap<>();
        this.insectsCount = 0;
    }

    public Map<HexCoordinate, HexCell> getGrid() {
        return grid;
    }

    public HexCell getCell(HexCoordinate coord) {
        return this.grid.get(coord);
    }

    public void addCell(HexCoordinate coord, Insect insect) {
        HexCell cell = new HexCell();
        cell.addInsect(insect);
        this.grid.put(coord, cell);
    }

    public void removeCell(HexCoordinate coord) {
        this.grid.remove(coord);
    }

    public HexCell getAdj(HexCoordinate coord, String dir) {
        int x = coord.getX();
        int y = coord.getY();
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
        return this.grid.get(new HexCoordinate(x, y));
    }

    public HexGrid clone() {
        HexGrid newGrid = new HexGrid();
        newGrid.insectsCount = this.insectsCount;

        for (Map.Entry<HexCoordinate, HexCell> entry : this.grid.entrySet()) {
            HexCoordinate coordinate = entry.getKey();
            HexCell cell = entry.getValue();

            HexCell newCell = new HexCell();
            for (Insect insect : cell.getInsects()) {
                newCell.addInsect(insect.clone()); // Assumes Insect class has a clone method
            }

            newGrid.grid.put(coordinate, newCell);
        }

        return newGrid;
    }

    public HashMap<HexCoordinate, String> getNeighbors(HexCoordinate cell) {
        HashMap<HexCoordinate, String> neighbors = new HashMap<>();
        String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
        int[] dx = {0, 1, 1, 0, -1, -1};
        int[] dy = {-1, -1, 0, 1, 1, 0};

        for (int i = 0; i < directions.length; i++) {
            HexCoordinate next = new HexCoordinate(cell.getX() + dx[i], cell.getY() + dy[i]);
            if (this.getAdj(cell, directions[i]) != null) {
                neighbors.put(next, directions[i]);
            }
        }

        return neighbors;
    }

    public boolean isHiveConnectedAfterMove(HexCoordinate from, HexCoordinate to) {
        // Create a copy of the current HexGrid
        HexGrid tempGrid = this.clone();

        // Make the move on the temporary grid
        Insect insect = tempGrid.getCell(from).getTopInsect();

        if (tempGrid.getCell(from).getInsects().size() <= 1) {
            tempGrid.removeCell(from);
        } else {
            tempGrid.getCell(from).removeTopInsect();
        }

        boolean isConnected1 = tempGrid.isHiveConnected();

        if(tempGrid.getCell(to) == null){
            tempGrid.addCell(to, insect);
        } else {
            tempGrid.getCell(to).addInsect(insect);
        }

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
            if (!visited.contains(next) && this.getAdj(current, directions[i]) != null) {
                dfs(next, visited);
            }
        }
    }

    public boolean checkLoser(Player player) {
        for (HexCoordinate h : this.getGrid().keySet()) {
            ArrayList<Insect> insects = this.getCell(h).getInsects();
            for (Insect i : insects){
                if (i instanceof Bee && i.getPlayer() == player) {
                    return this.getNeighbors(h).size() == 6;
                }
            }
        }
        return false;
    }

}
