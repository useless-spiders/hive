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
}
