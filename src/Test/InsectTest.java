package Test;

import Model.Insect.Ant;
import Model.Insect.Bee;
import Model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Classe de test pour les insectes
 */
public class InsectTest {

    private Player player = new Player("white", "Inspecteur blanco");
    private Player player2 = new Player("black", "Barbe noire");

    private Ant ant = new Ant(player);
    private Bee bee = new Bee(player);
    private Ant ant2 = new Ant(player2);
    private Bee bee2 = new Bee(player2);
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
        Ant ant1b = new Ant(player);
        assertEquals(ant, ant1b);

        assertNotEquals(ant, ant2);

        assertNotEquals(ant, bee);
    }


}