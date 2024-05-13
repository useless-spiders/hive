package Test;

import Modele.Insect.*;
import Modele.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Classe de test pour les insectes
 */
public class InsectTest {

    private Player player = new Player("white", "Inspecteur blanco");

    /**
     * Teste la création d'une abeille
     */
    @Test
    public void testBee() {
        Insect bee = new Bee(player);
        assertEquals(1, bee.getMax());
        assertEquals("Bee_white.png", bee.getImageName());
    }

    /**
     * Teste la création d'un scarabée
     */
    @Test
    public void testBeetle() {
        Insect beetle = new Beetle(player);
        assertEquals(2, beetle.getMax());
        assertEquals("Beetle_white.png", beetle.getImageName());
    }

    /**
     * Teste la création d'une sauterelle
     */
    @Test
    public void testGrasshopper() {
        Insect grasshopper = new Grasshopper(player);
        assertEquals(3, grasshopper.getMax());
        assertEquals("Grasshopper_white.png", grasshopper.getImageName());
    }

    /**
     * Teste la création d'une araignée
     */
    @Test
    public void testSpider() {
        Insect spider = new Spider(player);
        assertEquals(2, spider.getMax());
        assertEquals("Spider_white.png", spider.getImageName());
    }

    /**
     * Teste la création d'une fourmi
     */
    @Test
    public void testAnt() {
        Insect ant = new Ant(player);
        assertEquals(3, ant.getMax());
        assertEquals("Ant_white.png", ant.getImageName());
    }


}