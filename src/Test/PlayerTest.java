package Test;

import Modele.Insect.Ant;
import Modele.Insect.Insect;
import Modele.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Test
    public void testMaxInsects() {
        this.player = new Player("white", "Inspecteur blanco");
        Insect ant = new Ant(this.player);
        for (int i = 0; i < ant.getMax(); i++) {
            assertTrue(this.player.canAddInsect(ant));
        }
        assertFalse(this.player.canAddInsect(ant));
    }

    @Test
    public void testName(){
        this.player = new Player("white", "Inspecteur blanco");
        assertEquals("Inspecteur blanco", this.player.getName());
    }

    @Test
    public void testTurn(){
        this.player = new Player("white", "Inspecteur blanco");
        assertEquals(1, this.player.getTurn());
        this.player.incrementTurn();
        assertEquals(2, this.player.getTurn());
    }

    @Test
    public void testBeePlaced(){
        this.player = new Player("white", "Inspecteur blanco");
        assertFalse(this.player.isBeePlaced());
        this.player.setBeePlaced(true);
        assertTrue(this.player.isBeePlaced());
    }
}
