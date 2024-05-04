package Test;

import Modele.HexCell;
import Modele.Insect.Ant;
import Modele.Insect.Bee;
import Modele.Insect.Insect;
import org.junit.Test;

import static org.junit.Assert.*;

public class HexCellTest {

    @Test
    public void testAddAndRemoveInsect() {
        HexCell cell = new HexCell();
        Insect ant = new Ant("white");
        Insect bee = new Bee("white");

        // Test adding insects
        cell.addInsect(ant);
        assertTrue(cell.getInsects().contains(ant));
        cell.addInsect(bee);
        assertTrue(cell.getInsects().contains(bee));

        // Test removing insects
        cell.removeInsect(ant);
        assertFalse(cell.getInsects().contains(ant));
    }

    @Test
    public void testEquals() {
        HexCell cell1 = new HexCell();
        HexCell cell2 = new HexCell();
        Insect ant = new Ant("white");

        // Test equality with same insects
        cell1.addInsect(ant);
        cell2.addInsect(ant);
        assertEquals(cell1, cell2);

        // Test inequality with different insects
        cell2.removeInsect(ant);
        assertNotEquals(cell1, cell2);
    }
}