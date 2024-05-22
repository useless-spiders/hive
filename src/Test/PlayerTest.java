package Test;

import Model.Insect.Ant;
import Model.Player;
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
        assertTrue(this.player.canAddInsect(Ant.class));

        // Teste l'ajout d'une fourmi alors que le nombre maximum est atteint
        for (int i = 0; i < 3; i++) {
            this.player.playInsect(Ant.class);
        }

        assertFalse(this.player.canAddInsect(Ant.class));
        assertEquals(this.player.getInsectCount(Ant.class), 0);
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

    @Test
    public void testClone(){
        this.player = new Player("white", "Inspecteur blanco");
        this.player.playInsect(Ant.class);
        Player player2 = this.player.clone();
        assertEquals(this.player, player2);
        assertEquals(this.player.getStock().size(), player2.getStock().size());
    }

    @Test
    public void testEquals(){
        this.player = new Player("white", "Inspecteur blanco");
        Player player2 = new Player("white", "Inspecteur blanco");
        assertEquals(this.player, player2);
    }
}
