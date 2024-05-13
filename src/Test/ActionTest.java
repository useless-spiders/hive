package Test;

import Modele.Action;
import Modele.Insect.Ant;
import Modele.Insect.Bee;
import Modele.Player;
import Structures.HexCoordinate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Classe de test pour les actions
 */
public class ActionTest {

    /**
     * Teste la création d'actions et la récupération des coordonnées.
     */
    @Test
    public void testAction() {
        Player player = new Player("white", "Inspecteur blanco");

        // Première action
        Action action1 = new Action(new Bee(player), new HexCoordinate(0, 0), new HexCoordinate(0, 1));

        // Deuxième action
        Action action2 = new Action(new Ant(player), new HexCoordinate(0, 2), new HexCoordinate(1, 1));

        // Vérification des coordonnées de la première action
        assertEquals(action1.getPreviousCoor(), new HexCoordinate(0, 0));
        assertEquals(action1.getNewCoor(), new HexCoordinate(0, 1));

        // Vérification des coordonnées de la deuxième action
        assertEquals(action2.getPreviousCoor(), new HexCoordinate(0, 2));
        assertEquals(action2.getNewCoor(), new HexCoordinate(1, 1));
    }
}
