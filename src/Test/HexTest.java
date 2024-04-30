package Test;


import Modele.HexCell;
import Modele.HexCoordinate;
import Modele.HexGrid;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HexTest {
    private HexGrid grid;
    private HexCoordinate coord;
    private HexCell cell;

    @Before
    public void setUp() {
        grid = new HexGrid();
        coord = new HexCoordinate(0, 0);
        cell = new HexCell();
    }

    @Test
    public void testSetCell() {
        grid.setCell(coord, cell);
        assertEquals(cell, grid.getCell(coord));
    }

    @Test
    public void testGetCell() {
        grid.setCell(coord, cell);
        HexCell retrievedCell = grid.getCell(coord);
        assertEquals(cell, retrievedCell);
    }
}