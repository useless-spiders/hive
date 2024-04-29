package Test;

import Modele.Case;

import org.junit.Test;

import static org.junit.Assert.*;

public class CaseTest {
    @Test
    public void testXY() {
        Case c = new Case(0, 0);
        assert c.getX() == 0 && c.getY() == 0;
    }

    @Test
    public void testEtat() {
        Case c = new Case(0, 0);
        assertFalse("La case ne doit pas être mangée", c.getEstMange());
        assertFalse("La case ne doit pas être empoisonnée", c.getEstPoisson());
        c.empoisonnerCase();
        assertTrue("La case doit être empoisonnée", c.getEstPoisson());
        c.mangerCase();
        assertTrue("La case doit être mangée", c.getEstMange());
    }
}
