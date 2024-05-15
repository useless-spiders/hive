package Modele;

import java.util.*;

import Modele.Insect.Bee;
import Modele.Insect.Insect;
import Structures.HexCoordinate;
import Structures.Log;

public class HexGrid implements Cloneable {
    private Map<HexCoordinate, HexCell> grid;
    private int insectsCount;

    public static final String[] DIRECTIONS = {"NO", "NE", "E", "SE", "SO", "O"};
    public static final int[] DX = {0, 1, 1, 0, -1, -1};
    public static final int[] DY = {-1, -1, 0, 1, 1, 0};

    public HexGrid() {
        this.grid = new HashMap<>();
        this.insectsCount = 0;
    }

    public Map<HexCoordinate, HexCell> getGrid() {
        return this.grid;
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

    public HexCoordinate getNeighbor(HexCoordinate coord, String dir) {
        int x = coord.getX();
        int y = coord.getY();
        int index = Arrays.asList(DIRECTIONS).indexOf(dir);
        if (index != -1) {
            x += DX[index];
            y += DY[index];
        } else {
            Log.addMessage("Direction not found");
        }
        return new HexCoordinate(x, y);
    }

    public HashMap<HexCoordinate, String> getNeighbors(HexCoordinate coord) {
        return getNeighbors(coord, true);
    }

    public HashMap<HexCoordinate, String> getNeighbors(HexCoordinate coord, boolean verifyNull) {
        HashMap<HexCoordinate, String> neighbors = new HashMap<>();

        for (int i = 0; i < DIRECTIONS.length; i++) {
            HexCoordinate next = new HexCoordinate(coord.getX() + DX[i], coord.getY() + DY[i]);
            if (!verifyNull || this.getCell(this.getNeighbor(coord, DIRECTIONS[i])) != null) {
                neighbors.put(next, DIRECTIONS[i]);
            }
        }

        return neighbors;
    }

    public void applyMove(Move move,Player player){ //Appelé uniquement si le move est valide
        HexCell newCell = this.getCell(move.getNewCoor());
        Insect insect = move.getInsect();

        if(insect instanceof Bee){
            player.setBeePlaced(true);
        }
        if(newCell == null){ //cellule d'arrivé vide
            this.addCell(move.getNewCoor(), insect);
        }
        else{ //cellule d'arrivée deja remplis (scarabée)
            newCell.addInsect(insect);
        }

        if(move.getPreviousCoor() != null){ //cas deplacement insecte
            if(this.getCell(move.getPreviousCoor()).getInsects().size() == 1){ //cellule de depart a suppr
                this.removeCell(move.getPreviousCoor());
            }
            else{ //cellule de depart a garder
                this.getCell(move.getPreviousCoor()).removeTopInsect();
            }
        }
        else{ //cas placement insecte
            player.playInsect(insect.getClass());
        }
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

        if (tempGrid.getCell(to) == null) {
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

        this.dfs(start, visited);

        return visited.size() == this.getGrid().size();
    }

    private void dfs(HexCoordinate current, HashSet<HexCoordinate> visited) {
        visited.add(current);

        for (HexCoordinate next : getNeighbors(current).keySet()) {
            if (!visited.contains(next)) {
                this.dfs(next, visited);
            }
        }
    }

    public boolean checkLoser(Player player) {
        for (HexCoordinate h : this.getGrid().keySet()) {
            ArrayList<Insect> insects = this.getCell(h).getInsects();
            for (Insect i : insects) {
                if (i instanceof Bee && i.getPlayer() == player) {
                    return this.getNeighbors(h).size() == 6;
                }
            }
        }
        return false;
    }

    public String getClockwiseDirection(String direction) {
        int index = Arrays.asList(DIRECTIONS).indexOf(direction);
        int clockwiseIndex = (index + 1) % DIRECTIONS.length;
        return DIRECTIONS[clockwiseIndex];
    }

    public String getCounterClockwiseDirection(String direction) {
        int index = Arrays.asList(DIRECTIONS).indexOf(direction);
        int counterClockwiseIndex = (index - 1 + DIRECTIONS.length) % DIRECTIONS.length;
        return DIRECTIONS[counterClockwiseIndex];
    }

    @Override
    public HexGrid clone() {
        HexGrid newGrid = new HexGrid();
        newGrid.insectsCount = this.insectsCount;

        for (Map.Entry<HexCoordinate, HexCell> entry : this.grid.entrySet()) {
            HexCoordinate coordinate = entry.getKey();
            HexCell cell = entry.getValue();

            HexCell newCell = new HexCell();
            for (Insect insect : cell.getInsects()) {
                newCell.addInsect(insect.clone());
            }

            newGrid.grid.put(coordinate, newCell);
        }

        return newGrid;

    }
}
