package Test;


import Modele.HexCell;
import Modele.HexGrid;

import org.junit.Test;

import static org.junit.Assert.*;

public class HexTest {
    private HexGrid grid = new HexGrid();

    @Test
    public void testGridEmpty() {
        assert grid.getGrid().isEmpty();
    }

    @Test
    public void testSetCell() {
        grid.setCell(0, 0, HexCell.TYPE_SPIDER);
        assert !grid.getGrid().isEmpty();
    }

    @Test
    public void testCell() {
        HexCell cell = new HexCell(HexCell.TYPE_SPIDER);
        grid.setCell(0, 0, HexCell.TYPE_SPIDER);
        assertEquals(cell, grid.getCell(0, 0));
    }

    @Test
    public void testGetType() {
        HexCell cell = new HexCell(HexCell.TYPE_SPIDER);
        assertEquals(HexCell.TYPE_SPIDER, cell.getType());
    }

    @Test
    public void testGetEmpty() {
        HexCell retrievedCell = grid.getCell(0, 0);
        assertNull(retrievedCell);
    }

    @Test
    public void testGetAdj() {
        HexCell cell1 = new HexCell(HexCell.TYPE_ANT);
        HexCell cell2 = new HexCell(HexCell.TYPE_BEETLE);
        HexCell cell3 = new HexCell(HexCell.TYPE_BEE);
        HexCell cell4 = new HexCell(HexCell.TYPE_GRASSHOPPER);
        HexCell cell5 = new HexCell(HexCell.TYPE_SPIDER);
        HexCell cell6 = new HexCell(HexCell.TYPE_ANT);
        grid.setCell(0, -1, HexCell.TYPE_ANT);
        grid.setCell(1, -1, HexCell.TYPE_BEETLE);
        grid.setCell(1, 0, HexCell.TYPE_BEE);
        grid.setCell(0, 1, HexCell.TYPE_GRASSHOPPER);
        grid.setCell(-1, 1, HexCell.TYPE_SPIDER);
        grid.setCell(-1, 0, HexCell.TYPE_ANT);
        assertEquals(cell1, grid.getAdj(0, 0, "NO"));
        assertEquals(cell2, grid.getAdj(0, 0, "NE"));
        assertEquals(cell3, grid.getAdj(0, 0, "E"));
        assertEquals(cell4, grid.getAdj(0, 0, "SE"));
        assertEquals(cell5, grid.getAdj(0, 0, "SO"));
        assertEquals(cell6, grid.getAdj(0, 0, "O"));
    }
    
}