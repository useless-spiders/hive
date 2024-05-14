package Test;

import org.junit.Test;
import Structures.HexCoordinate;

import static org.junit.Assert.*;

/**
 * Classe de test pour les coordonnées hexagonales
 */
public class HexCoordinateTest {

    /**
     * Teste la création d'une coordonnée hexagonale
     */
    @Test
    public void testGetXandGetY() {
        HexCoordinate coord = new HexCoordinate(5, 10);

        // Vérification des coordonnées
        assertEquals(5, coord.getX());
        assertEquals(10, coord.getY());
    }

    /**
     * Teste la comparaison de coordonnées hexagonales
     */
    @Test
    public void testEquals() {
        HexCoordinate coord1 = new HexCoordinate(5, 10);
        HexCoordinate coord2 = new HexCoordinate(5, 10);
        HexCoordinate coord3 = new HexCoordinate(10, 5);

        // Teste l'égalité entre deux coordonnées
        assertEquals(coord1, coord2);
        assertNotEquals(coord1, coord3);
    }

    /**
     * Teste le hashcode d'une coordonnée hexagonale
     */
    @Test
    public void testHashCode() {
        HexCoordinate coord1 = new HexCoordinate(5, 10);
        HexCoordinate coord2 = new HexCoordinate(5, 10);

        // Teste l'égalité des hashcodes
        assertEquals(coord1.hashCode(), coord2.hashCode());
    }
}