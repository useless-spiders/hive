package Test;


import Modele.HexCell;
import Modele.HexGrid;

import Modele.Insect.Ant;
import Modele.Insect.Spider;
import org.junit.Test;

import static org.junit.Assert.*;

public class HexTest {
    private HexGrid grid = new HexGrid();
    private Ant ant = new Ant();
    private Spider spider = new Spider();

    @Test
    public void testGridEmpty() {
        assert grid.getGrid().isEmpty();
    }

    @Test
    public void testSetCell() {
        grid.setCell(0, 0, ant);
        assert !grid.getGrid().isEmpty();
    }

    @Test
    public void testCell() {
        HexCell cell = new HexCell(spider);
        grid.setCell(0, 0, spider);
        assertEquals(cell, grid.getCell(0, 0));
    }

    @Test
    public void testCell2() {
        HexCell cell = new HexCell(spider);
        grid.setCell(0, 0, ant);
        assertNotEquals(cell, grid.getCell(0, 0));
    }

    @Test
    public void testGetType() {
        HexCell cell = new HexCell(spider);
        assertEquals(spider, cell.getType());
    }

    @Test
    public void testGetEmpty() {
        HexCell retrievedCell = grid.getCell(0, 0);
        assertNull(retrievedCell);
    }

    @Test
    public void testClearCell() {
        grid.clearCell(0, 0);
        assert grid.getGrid().isEmpty();
    }

    @Test
    public void testGetAdj() {
        HexCell cell1 = new HexCell(ant);
        HexCell cell2 = new HexCell(ant);
        HexCell cell3 = new HexCell(ant);
        HexCell cell4 = new HexCell(ant);
        HexCell cell5 = new HexCell(ant);
        HexCell cell6 = new HexCell(ant);
        grid.setCell(0, -1, ant);
        grid.setCell(1, -1, ant);
        grid.setCell(1, 0, ant);
        grid.setCell(0, 1, ant);
        grid.setCell(-1, 1, ant);
        grid.setCell(-1, 0, ant);
        assertEquals(cell1, grid.getAdj(0, 0, "NO"));
        assertEquals(cell2, grid.getAdj(0, 0, "NE"));
        assertEquals(cell3, grid.getAdj(0, 0, "E"));
        assertEquals(cell4, grid.getAdj(0, 0, "SE"));
        assertEquals(cell5, grid.getAdj(0, 0, "SO"));
        assertEquals(cell6, grid.getAdj(0, 0, "O"));
    }

}