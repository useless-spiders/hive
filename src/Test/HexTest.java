package Test;


import Modele.HexCell;
import Modele.HexGrid;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

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
        assertEquals(cell, grid.getCell(0, 0));
    }

    @Test
    public void testGetType() {
        assertEquals(Optional.of(HexCell.TYPE_SPIDER), Optional.ofNullable(cell.getType()));
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
        grid.setCell(0, -1, cell1);
        grid.setCell(1, -1, cell2);
        grid.setCell(1, 0, cell3);
        grid.setCell(0, 1, cell4);
        grid.setCell(-1, 1, cell5);
        grid.setCell(-1, 0, cell6);
        assertEquals(cell1, grid.getAdj(0, 0, "NO"));
        assertEquals(cell2, grid.getAdj(0, 0, "NE"));
        assertEquals(cell3, grid.getAdj(0, 0, "E"));
        assertEquals(cell4, grid.getAdj(0, 0, "SE"));
        assertEquals(cell5, grid.getAdj(0, 0, "SO"));
        assertEquals(cell6, grid.getAdj(0, 0, "O"));
    }
    
}