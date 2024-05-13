package Test;


import Modele.HexCell;
import Modele.HexGrid;

import Modele.Insect.*;
import Modele.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test pour la grille hexagonale
 */
public class HexGridTest {
    private HexGrid grid = new HexGrid();
    private Player player = new Player("white", "Inspecteur blanco");
    private Insect ant = new Ant(player);
    private Insect spider = new Spider(player);
    private Insect bee = new Bee(player);
    private Insect beetle = new Beetle(player);

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
        grid.addCell(0, 0, ant);
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
        HexCell retrievedCell = grid.getCell(0, 0);
        assertNull(retrievedCell);
    }

    /**
     * Teste la récupération d'une cellule dans la grille
     */
    @Test
    public void testClearCell() {
        grid.addCell(0, 0, ant);
        grid.removeCell(0, 0);
        assert grid.getGrid().isEmpty();
    }

    /**
     * Teste la récupération d'une cellule adjacente
     */
    @Test
    public void testGetAdj() {
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
        grid.addCell(0, -1, ant);
        grid.addCell(1, -1, ant);
        grid.addCell(1, 0, bee);
        grid.addCell(0, 1, beetle);
        grid.addCell(-1, 1, beetle);
        grid.addCell(-1, 0, spider);
        assertEquals(cell1, grid.getAdj(0, 0, "NO"));
        assertEquals(cell2, grid.getAdj(0, 0, "NE"));
        assertEquals(cell3, grid.getAdj(0, 0, "E"));
        assertEquals(cell4, grid.getAdj(0, 0, "SE"));
        assertEquals(cell5, grid.getAdj(0, 0, "SO"));
        assertEquals(cell6, grid.getAdj(0, 0, "O"));
    }

}