package Test;

import Modele.Insect.Ant;
import Modele.Insect.Insect;
import Modele.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {
    private Player player;

    @Test
    public void testMaxInsects() {
        this.player = new Player("white");
        Insect ant = new Ant(this.player);
        for (int i = 0; i < ant.getMax(); i++) {
            assertTrue(this.player.canAddInsect(ant));
        }
        assertFalse(this.player.canAddInsect(ant));
    }
}
