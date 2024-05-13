package Test;

import Modele.Insect.Ant;
import Modele.Insect.Insect;
import Modele.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test pour les joueurs
 */
public class PlayerTest {
    private Player player;

    /**
     * Teste l'ajout d'insectes
     */
    @Test
    public void testMaxInsects() {
        this.player = new Player("white", "Inspecteur blanco");

        // Teste l'ajout d'une fourmi
        Insect ant = new Ant(this.player);
        assertTrue(this.player.canAddInsect(ant));

        // Teste l'ajout d'une fourmi alors que le nombre maximum est atteint
        for (int i = 0; i < ant.getMax(); i++) {
            this.player.addInsect(ant);
        }

        assertFalse(this.player.canAddInsect(ant));
        assertEquals(this.player.getInsectCount(ant.getClass()), ant.getMax());
    }

    /**
     * Teste le nom du joueur
     */
    @Test
    public void testName(){
        this.player = new Player("white", "Inspecteur blanco");
        // Teste le nom du joueur
        assertEquals("Inspecteur blanco", this.player.getName());
    }

    /**
     * Teste le tour du joueur
     */
    @Test
    public void testTurn(){
        this.player = new Player("white", "Inspecteur blanco");
        // Teste l'incrementation du tour
        assertEquals(1, this.player.getTurn());
        this.player.incrementTurn();
        assertEquals(2, this.player.getTurn());

        // Test la décrémentation du tour
        this.player.decrementTurn();
        assertEquals(1, this.player.getTurn());
    }

    /**
     * Teste si l'abeille a été placé
     */
    @Test
    public void testBeePlaced(){
        this.player = new Player("white", "Inspecteur blanco");
        // Teste si l'abeille a été placé
        assertFalse(this.player.isBeePlaced());
        this.player.setBeePlaced(true);
        assertTrue(this.player.isBeePlaced());
    }
}
