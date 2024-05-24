package Test;

import Model.Insect.Ant;
import Model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test pour les joueurs
 */
public class PlayerTest {

    /**
     * Teste l'ajout d'insectes
     */
    @Test
    public void testMaxInsects() {
        Player player = new Player("Inspecteur blanco");
        player.setColor(Player.WHITE);

        // Teste l'ajout d'une fourmi
        assertTrue(player.canAddInsect(Ant.class));

        // Teste l'ajout d'une fourmi alors que le nombre maximum est atteint
        for (int i = 0; i < 3; i++) {
            player.playInsect(Ant.class);
        }

        assertFalse(player.canAddInsect(Ant.class));
        assertEquals(player.getInsectCount(Ant.class), 0);
    }

    /**
     * Teste le nom du joueur
     */
    @Test
    public void testName(){
        Player player = new Player("Inspecteur blanco");
        player.setColor(Player.WHITE);
        // Teste le nom du joueur
        assertEquals("Inspecteur blanco", player.getName());
    }

    /**
     * Teste le tour du joueur
     */
    @Test
    public void testTurn(){
        Player player = new Player("Inspecteur blanco");
        player.setColor(Player.WHITE);

        // Teste l'incrementation du tour
        assertEquals(1, player.getTurn());
        player.incrementTurn();
        assertEquals(2, player.getTurn());

        // Test la décrémentation du tour
        player.decrementTurn();
        assertEquals(1, player.getTurn());
    }

    /**
     * Teste si l'abeille a été placé
     */
    @Test
    public void testBeePlaced(){
        Player player = new Player("Inspecteur blanco");
        player.setColor(Player.WHITE);
        // Teste si l'abeille a été placé
        assertFalse(player.isBeePlaced());
        player.setBeePlaced(true);
        assertTrue(player.isBeePlaced());
    }

    @Test
    public void testClone(){
        Player player = new Player("Inspecteur blanco");
        player.setColor(Player.WHITE);
        player.playInsect(Ant.class);
        Player player2 = player.clone();
        assertEquals(player, player2);
        assertEquals(player.getStock().size(), player2.getStock().size());
    }

    @Test
    public void testEquals(){
        Player player = new Player("Inspecteur blanco");
        player.setColor(Player.WHITE);
        Player player2 = new Player("Inspecteur blanco");
        player2.setColor(Player.WHITE);
        assertEquals(player, player2);
    }
}
