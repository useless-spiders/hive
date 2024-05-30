package Model;

import Model.Insect.Bee;
import Model.Insect.Insect;
import Structure.HexCoordinate;

import java.util.*;

/**
 * Classe pour la grille hexagonale
 */
public class HexGrid implements Cloneable {
    private Map<HexCoordinate, HexCell> grid;

    public static final String[] DIRECTIONS = {"NO", "NE", "E", "SE", "SO", "O"};
    public static final int[] DX = {0, 1, 1, 0, -1, -1};
    public static final int[] DY = {-1, -1, 0, 1, 1, 0};

    // utilisation d'une hashmap pour être en temps constant entre la direction et l'index
    private static final Map<String, Integer> DIRECTIONS_MAP = new HashMap<>();

    static {
        for (int i = 0; i < DIRECTIONS.length; i++) {
            DIRECTIONS_MAP.put(DIRECTIONS[i], i);
        }
    }

    /**
     * Constructeur
     */
    public HexGrid() {
        this.grid = new HashMap<>();
    }

    /**
     * Renvoie la grille
     *
     * @return Map<HexCoordinate, HexCell>
     */
    public Map<HexCoordinate, HexCell> getGrid() {
        return this.grid;
    }

    /**
     * Renvoie la cellule à partir des coordonnées
     *
     * @param coord Coordonnées
     * @return HexCell
     */
    public HexCell getCell(HexCoordinate coord) {
        return this.grid.get(coord);
    }

    /**
     * Ajoute une cellule
     *
     * @param coord  Coordonnées
     * @param insect Insecte
     */
    public void addCell(HexCoordinate coord, Insect insect) {
        HexCell cell = new HexCell();
        cell.addInsect(insect);
        this.grid.put(coord, cell);
    }

    /**
     * Supprime une cellule
     *
     * @param coord Coordonnées
     */
    public void removeCell(HexCoordinate coord) {
        this.grid.remove(coord);
    }

    /**
     * Renvoie les coordonnées voisines
     *
     * @param coord Coordonnées
     * @param dir   Direction
     * @return HexCoordinate
     */
    public HexCoordinate getNeighborCoordinates(HexCoordinate coord, String dir) {
        int x = coord.getX();
        int y = coord.getY();
        Integer index = DIRECTIONS_MAP.get(dir);
        if (index != null) {
            x += DX[index];
            y += DY[index];
            return new HexCoordinate(x, y);
        } else {
            return null;
        }
    }

    /**
     * Renvoie les coordonnées voisines
     *
     * @param coord Coordonnées
     * @return HashMap<HexCoordinate, String>
     */
    public HashMap<HexCoordinate, String> getNeighborsCoordinates(HexCoordinate coord) {
        return getNeighborsCoordinates(coord, true);
    }

    /**
     * Renvoie les coordonnées voisines
     *
     * @param coord      Coordonnées
     * @param verifyNull Vérifie si la cellule est nulle
     * @return HashMap<HexCoordinate, String>
     */
    public HashMap<HexCoordinate, String> getNeighborsCoordinates(HexCoordinate coord, boolean verifyNull) {
        HashMap<HexCoordinate, String> neighbors = new HashMap<>();

        for (int i = 0; i < DIRECTIONS.length; i++) {
            HexCoordinate next = new HexCoordinate(coord.getX() + DX[i], coord.getY() + DY[i]);
            if (!verifyNull || this.getCell(this.getNeighborCoordinates(coord, DIRECTIONS[i])) != null) {
                neighbors.put(next, DIRECTIONS[i]);
            }
        }

        return neighbors;
    }

    /**
     * Applique un mouvement
     *
     * @param move   Move
     * @param player Player
     */
    public void applyMove(Move move, Player player) { //Appelé uniquement si le move est valide
        HexCell newCell = this.getCell(move.getNewCoor());
        Insect insect = move.getInsect();

        if (insect instanceof Bee) {
            player.setBeePlaced(true);
        }
        if (newCell == null) { //cellule d'arrivé vide
            this.addCell(move.getNewCoor(), insect);
        } else { //cellule d'arrivée deja remplis (scarabée)
            newCell.addInsect(insect);
        }

        if (move.getPreviousCoor() != null) { //cas deplacement insecte
            if (this.getCell(move.getPreviousCoor()).getInsects().size() == 1) { //cellule de depart a suppr
                this.removeCell(move.getPreviousCoor());
            } else { //cellule de depart a garder
                this.getCell(move.getPreviousCoor()).removeTopInsect();
            }
        } else { //cas placement insecte
            player.playInsect(insect.getClass());
        }
    }

    /**
     * Annule un mouvement
     *
     * @param move   Move
     * @param player Player
     */
    public void unapplyMove(Move move, Player player) {
        HexCoordinate from = move.getPreviousCoor();
        HexCoordinate to = move.getNewCoor();
        Insect insect = move.getInsect();
        if (insect instanceof Bee) {
            player.setBeePlaced(false);
        }

        if (this.getCell(to) != null) {
            this.getCell(to).removeTopInsect();
        }
        if (this.getCell(to) == null || this.getCell(to).getInsects().isEmpty()) {
            this.removeCell(to);
        }

        if (from != null) {
            if (this.getCell(from) != null) {
                this.getCell(from).addInsect(insect);
            } else {
                this.addCell(from, insect);
            }
        }

        if (from == null) {
            player.unplayInsect(insect);
        }
    }

    /**
     * Vérifie si la ruche est toujours connectée après un mouvement
     *
     * @param from Ancienne coordonnée
     * @param to   Nouvelle coordonnée
     * @return boolean
     */
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

    /**
     * Vérifie si la ruche est connectée
     *
     * @return boolean
     */
    public boolean isHiveConnected() {
        if (this.getGrid().isEmpty()) {
            return true;
        }

        HexCoordinate start = this.getGrid().keySet().iterator().next();
        HashSet<HexCoordinate> visited = new HashSet<>();

        this.dfs(start, visited);

        return visited.size() == this.getGrid().size();
    }

    /**
     * Parcours en profondeur
     *
     * @param current Coordonnée actuelle
     * @param visited Coordonnées visitées
     */
    private void dfs(HexCoordinate current, HashSet<HexCoordinate> visited) {
        visited.add(current);

        for (HexCoordinate next : getNeighborsCoordinates(current).keySet()) {
            if (!visited.contains(next)) {
                this.dfs(next, visited);
            }
        }
    }

    /**
     * Vérifie si un joueur a perdu
     *
     * @param player Joueur
     * @return boolean
     */
    public boolean checkLoser(Player player) {
        for (HexCoordinate h : this.getGrid().keySet()) {
            ArrayList<Insect> insects = this.getCell(h).getInsects();
            for (Insect i : insects) {
                if (i instanceof Bee && i.getPlayer().equals(player)) {
                    return this.getNeighborsCoordinates(h).size() == 6;
                }
            }
        }
        return false;
    }

    /**
     * Renvoie la direction dans le sens horaire
     *
     * @param direction Direction
     * @return String
     */
    public String getClockwiseDirection(String direction) {
        int index = Arrays.asList(DIRECTIONS).indexOf(direction);
        int clockwiseIndex = (index + 1) % DIRECTIONS.length;
        return DIRECTIONS[clockwiseIndex];
    }

    /**
     * Renvoie la direction dans le sens anti-horaire
     *
     * @param direction Direction
     * @return String
     */
    public String getCounterClockwiseDirection(String direction) {
        int index = Arrays.asList(DIRECTIONS).indexOf(direction);
        int counterClockwiseIndex = (index - 1 + DIRECTIONS.length) % DIRECTIONS.length;
        return DIRECTIONS[counterClockwiseIndex];
    }

    /**
     * Teste si deux grilles sont égales
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HexGrid hexGrid = (HexGrid) obj;
        // Compare the grids for equality
        return Objects.equals(this.getGrid(), hexGrid.getGrid());
    }

    /**
     * Renvoie le hashcode de la grille
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getGrid());
    }

    /**
     * Clone la grille
     *
     * @return HexGrid
     */
    @Override
    public HexGrid clone() {
        try {
            HexGrid cloned = (HexGrid) super.clone();
            cloned.grid = new HashMap<>();
            for (Map.Entry<HexCoordinate, HexCell> entry : this.grid.entrySet()) {
                cloned.grid.put(entry.getKey().clone(), entry.getValue().clone());
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Ne devrait jamais se produire
        }
    }
}
