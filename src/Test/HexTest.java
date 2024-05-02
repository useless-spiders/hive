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
    public void testAddCell() {
        grid.addCell(0, 0, ant);
        assert !grid.getGrid().isEmpty();
    }

    @Test
    public void testGetType() {
        HexCell cell = new HexCell();
        cell.addInsect(spider);
        for(int i = 0; i < cell.getInsects().size(); i++) {
            assertEquals(spider, cell.getInsects().get(i));
        }
    }

    @Test
    public void testGetEmpty() {
        HexCell retrievedCell = grid.getCell(0, 0);
        assertNull(retrievedCell);
    }

    @Test
    public void testClearCell() {
        grid.removeCell(0, 0);
        assert grid.getGrid().isEmpty();
    }

    @Test
    public void testGetAdj() {
        HexCell cell1 = new HexCell();
        cell1.addInsect(ant);
        HexCell cell2 = new HexCell();
        cell2.addInsect(ant);
        HexCell cell3 = new HexCell();
        cell3.addInsect(ant);
        HexCell cell4 = new HexCell();
        cell4.addInsect(ant);
        HexCell cell5 = new HexCell();
        cell5.addInsect(ant);
        HexCell cell6 = new HexCell();
        cell6.addInsect(ant);
        grid.addCell(0, -1, ant);
        grid.addCell(1, -1, ant);
        grid.addCell(1, 0, ant);
        grid.addCell(0, 1, ant);
        grid.addCell(-1, 1, ant);
        grid.addCell(-1, 0, ant);
        assertEquals(cell1, grid.getAdj(0, 0, "NO"));
        assertEquals(cell2, grid.getAdj(0, 0, "NE"));
        assertEquals(cell3, grid.getAdj(0, 0, "E"));
        assertEquals(cell4, grid.getAdj(0, 0, "SE"));
        assertEquals(cell5, grid.getAdj(0, 0, "SO"));
        assertEquals(cell6, grid.getAdj(0, 0, "O"));
    }

}