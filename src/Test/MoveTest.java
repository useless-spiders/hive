package Test;

import Model.Move;
import Model.Insect.Ant;
import Model.Insect.Bee;
import Model.Player;
import Structure.HexCoordinate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Classe de test pour les actions
 */
public class MoveTest {

    /**
     * Teste la création d'actions et la récupération des coordonnées.
     */
    @Test
    public void testAction() {
        Player player = new Player("Inspecteur blanco");
        player.setColor(Player.WHITE);

        // Première action
        Move move1 = new Move(new Bee(player), new HexCoordinate(0, 0), new HexCoordinate(0, 1));

        // Deuxième action
        Move move2 = new Move(new Ant(player), new HexCoordinate(0, 2), new HexCoordinate(1, 1));

        // Vérification des coordonnées de la première action
        assertEquals(move1.getPreviousCoor(), new HexCoordinate(0, 0));
        assertEquals(move1.getNewCoor(), new HexCoordinate(0, 1));

        // Vérification des coordonnées de la deuxième action
        assertEquals(move2.getPreviousCoor(), new HexCoordinate(0, 2));
        assertEquals(move2.getNewCoor(), new HexCoordinate(1, 1));
    }
}
