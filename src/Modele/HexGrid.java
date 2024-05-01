package Modele;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HexGrid {
    private Map<HexCoordinate, HexCell> grid;

    public HexGrid() {
        this.grid = new HashMap<>();
        initializeGrid();
    }

    private void initializeGrid() {
        // Ajouter des données initiales à la grille
        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                setCell(i, j, new HexCell(null));
            }
        }
    }

    public Map<HexCoordinate, HexCell> getGrid() {
        return grid;
    }

    public HexCell getCell(int x, int y) {
        return this.grid.get(new HexCoordinate(x, y));
    }

    public void setCell(int x, int y, HexCell cell) {
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

    public class HexCoordinate {
        private int x;
        private int y;

        public HexCoordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HexCoordinate other = (HexCoordinate) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

    }


}
