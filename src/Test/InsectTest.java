package Test;

import Model.Insect.Ant;
import Model.Insect.Bee;
import Model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Classe de test pour les insectes
 */
public class InsectTest {

    private Player player = new Player("Inspecteur blanco");
    private Player player2 = new Player("Barbe noire");

    private Ant ant;
    private Bee bee;
    private Ant ant2;

    @Before
    public void setUp() {
        this.player.setColor(Player.WHITE);
        this.player2.setColor(Player.BLACK);
        this.ant = new Ant(player);
        this.bee = new Bee(player);
        this.ant2 = new Ant(player2);
    }

    /**
     * Teste la création d'une abeille
     */
    @Test
    public void testBee() {
    }

    /**
     * Teste la création d'un scarabée
     */
    @Test
    public void testBeetle() {
    }

    /**
     * Teste la création d'une sauterelle
     */
    @Test
    public void testGrasshopper() {
    }

    /**
     * Teste la création d'une araignée
     */
    @Test
    public void testSpider() {
    }

    /**
     * Teste la création d'une fourmi
     */
    @Test
    public void testAnt() {
    }

    @Test
    public void testClone(){
        Ant ant2 = (Ant) ant.clone();
        assertEquals(ant, ant2);
    }

    @Test
    public void testEquals(){
        Ant ant1b = new Ant(this.player);
        assertEquals(this.ant, ant1b);

        assertNotEquals(this.ant, this.ant2);

        assertNotEquals(this.ant, this.bee);
    }


}