package Test;


import Modele.HexCell;
import Modele.HexGrid;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HexTest {
    private HexGrid grid;
    private HexCell cell;

    @Before
    public void setUp() {
        grid = new HexGrid();
        cell = new HexCell(HexCell.TYPE_SPIDER);
    }

    @Test
    public void testSetCell() {
        grid.setCell(0, 0, cell);
        assertEquals(cell, grid.getCell(0, 0));
    }

    @Test
    public void testGetCell() {
        grid.setCell(0, 0, cell);
        HexCell retrievedCell = grid.getCell(0, 0);
        assertEquals(cell, retrievedCell);
    }

    @Test
    public void testGetType(){
        assertEquals(HexCell.TYPE_SPIDER, cell.getType());
    }

    @Test
    public void testGetEmpty(){
        HexCell retrievedCell = grid.getCell(0, 0);
        assertNull(retrievedCell);
    }
}