package Test;

import org.junit.Test;
import Structures.HexCoordinate;

import static org.junit.Assert.*;

public class HexCoordinateTest {

    @Test
    public void testGetXandGetY() {
        HexCoordinate coord = new HexCoordinate(5, 10);
        assertEquals(5, coord.getX());
        assertEquals(10, coord.getY());
    }

    @Test
    public void testEquals() {
        HexCoordinate coord1 = new HexCoordinate(5, 10);
        HexCoordinate coord2 = new HexCoordinate(5, 10);
        HexCoordinate coord3 = new HexCoordinate(10, 5);

        assertEquals(coord1, coord2);
        assertNotEquals(coord1, coord3);
    }

    @Test
    public void testHashCode() {
        HexCoordinate coord1 = new HexCoordinate(5, 10);
        HexCoordinate coord2 = new HexCoordinate(5, 10);

        assertEquals(coord1.hashCode(), coord2.hashCode());
    }
}