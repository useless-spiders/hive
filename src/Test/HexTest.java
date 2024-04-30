package Test;


import Modele.HexCell;
import Modele.HexGrid;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HexTest {
    private HexGrid grid = new HexGrid();
    private HexCell cell = new HexCell(HexCell.TYPE_SPIDER);

    @Test
    public void testGridEmpty() {
        assert grid.getGrid().isEmpty();
    }

    @Test
    public void testSetCell() {
        grid.setCell(0, 0, cell);
        assert !grid.getGrid().isEmpty();
    }

    @Test
    public void testCell() {
        grid.setCell(0, 0, cell);
        System.out.println(grid.getCell(0, 0));
        assertEquals(cell, grid.getCell(0, 0));
    }

    @Test
    public void testGetType() {
        assertEquals(HexCell.TYPE_SPIDER, cell.getType());
    }

    @Test
    public void testGetEmpty() {
        HexCell retrievedCell = grid.getCell(0, 0);
        assertNull(retrievedCell);
    }
}