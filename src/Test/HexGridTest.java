package Test;


import Model.HexCell;
import Model.HexGrid;
import Model.Insect.*;
import Model.Player;
import Structure.HexCoordinate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test pour la grille hexagonale
 */
public class HexGridTest {
    private final Player player = new Player("Inspecteur blanco");
    private final Player player2 = new Player("Barbe noire");
    private HexGrid grid = new HexGrid();
    private Insect ant;
    private Insect spider;
    private Insect bee;
    private Insect beetle;
    private Insect ant2;
    private Insect spider2;
    private Insect bee2;
    private Insect beetle2;

    @Before
    public void setUp() {
        this.player.setColor(0);
        this.player2.setColor(1);
        grid = new HexGrid();
        this.ant = new Ant(player);
        this.spider = new Spider(player);
        this.bee = new Bee(player);
        this.beetle = new Beetle(player);
        this.ant2 = new Ant(player2);
        this.spider2 = new Spider(player2);
        this.bee2 = new Bee(player2);
        this.beetle2 = new Beetle(player2);
    }


    /**
     * Teste la création d'une grille vide
     */
    @Test
    public void testGridEmpty() {
        assert grid.getGrid().isEmpty();
    }

    /**
     * Teste l'ajout d'une cellule dans la grille
     */
    @Test
    public void testAddCell() {
        grid.addCell(new HexCoordinate(0, 0), ant);
        assert !grid.getGrid().isEmpty();
    }

    /**
     * Teste la récupération d'une cellule dans la grille
     */
    @Test
    public void testGetType() {
        HexCell cell = new HexCell();
        cell.addInsect(spider);
        cell.addInsect(ant);
        assertEquals(spider, cell.getInsects().get(0));
        assertEquals(ant, cell.getInsects().get(1));
    }

    /**
     * Teste la récupération d'une cellule dans la grille
     */
    @Test
    public void testGetEmpty() {
        HexCell retrievedCell = grid.getCell(new HexCoordinate(0, 0));
        assertNull(retrievedCell);
    }

    /**
     * Teste la récupération d'une cellule dans la grille
     */
    @Test
    public void testClearCell() {
        HexCoordinate coord = new HexCoordinate(0, 0);
        grid.addCell(coord, ant);
        grid.removeCell(coord);
        assert grid.getGrid().isEmpty();
    }

    /**
     * Teste la récupération d'une cellule adjacente
     */
    @Test
    public void testGetNeighborCoordinates() {
        HexCoordinate coord = new HexCoordinate(0, 0);

        HexCell cell1 = new HexCell();
        cell1.addInsect(ant);
        HexCell cell2 = new HexCell();
        cell2.addInsect(ant);
        HexCell cell3 = new HexCell();
        cell3.addInsect(bee);
        HexCell cell4 = new HexCell();
        cell4.addInsect(beetle);
        HexCell cell5 = new HexCell();
        cell5.addInsect(beetle);
        HexCell cell6 = new HexCell();
        cell6.addInsect(spider);
        grid.addCell(new HexCoordinate(0, -1), ant);
        grid.addCell(new HexCoordinate(1, -1), ant);
        grid.addCell(new HexCoordinate(1, 0), bee);
        grid.addCell(new HexCoordinate(0, 1), beetle);
        grid.addCell(new HexCoordinate(-1, 1), beetle);
        grid.addCell(new HexCoordinate(-1, 0), spider);
        assertEquals(cell1, grid.getCell(grid.getNeighborCoordinates(coord, "NO")));
        assertEquals(cell2, grid.getCell(grid.getNeighborCoordinates(coord, "NE")));
        assertEquals(cell3, grid.getCell(grid.getNeighborCoordinates(coord, "E")));
        assertEquals(cell4, grid.getCell(grid.getNeighborCoordinates(coord, "SE")));
        assertEquals(cell5, grid.getCell(grid.getNeighborCoordinates(coord, "SO")));
        assertEquals(cell6, grid.getCell(grid.getNeighborCoordinates(coord, "O")));
    }

    /**
     * Teste la vérification de la victoire
     */
    @Test
    public void testLoser() {
        assertFalse(grid.checkLoser(player));
        grid.addCell(new HexCoordinate(0, -1), ant); // NO
        grid.addCell(new HexCoordinate(1, -1), ant); // NE
        grid.addCell(new HexCoordinate(0, 0), bee); // Center
        grid.addCell(new HexCoordinate(1, 0), beetle); // E
        grid.addCell(new HexCoordinate(0, 1), beetle); // SE
        grid.addCell(new HexCoordinate(-1, 1), spider); // SO
        grid.addCell(new HexCoordinate(-1, 0), spider); // O
        assertTrue(grid.checkLoser(player));
    }

    @Test
    public void testClone() {
        grid.addCell(new HexCoordinate(0, -1), ant); // NO
        grid.addCell(new HexCoordinate(1, -1), ant); // NE
        grid.addCell(new HexCoordinate(0, 0), bee); // Center
        grid.addCell(new HexCoordinate(1, 0), beetle); // E
        grid.addCell(new HexCoordinate(0, 1), beetle); // SE
        grid.addCell(new HexCoordinate(-1, 1), spider); // SO

        grid.addCell(new HexCoordinate(3, 2), ant2); // NO
        grid.addCell(new HexCoordinate(4, 2), ant2); // NE
        grid.addCell(new HexCoordinate(3, 3), bee2); // Center
        grid.addCell(new HexCoordinate(4, 3), beetle2); // E
        grid.addCell(new HexCoordinate(3, 4), beetle2); // SE
        grid.addCell(new HexCoordinate(2, 4), spider2); // SO
        HexGrid clone = grid.clone();

        Player player_c = player.clone();

        assertEquals(player_c, ant.getPlayer());

        assertEquals(grid.getCell(new HexCoordinate(0, 0)).getTopInsect().getPlayer(), player_c);

        assertEquals(grid.getGrid().size(), clone.getGrid().size());

        assertEquals(grid.getGrid(), clone.getGrid());

        assertEquals(grid, clone);

    }

}