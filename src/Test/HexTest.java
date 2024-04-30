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
    public void testGridEmpty() {
        assertNull(grid);
    }

    @Test
    public void testGrid(){
        grid.setCell(0, 0, cell);
        assertNotNull(grid);
    }

    @Test
    public void testCell() {
        grid.setCell(0, 0, cell);
        System.out.println(grid.getCell(0, 0));
        assertEquals(cell, grid.getCell(0, 0));
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