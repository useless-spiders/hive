package Test;

import Modele.Move;
import Modele.History;
import Modele.Insect.Ant;
import Modele.Insect.Bee;
import Modele.Player;
import Structures.HexCoordinate;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test pour l'historique des actions
 */
public class HistoryTest {
    private History history;
    private Player player = new Player("white", "Inspecteur blanco");
    private Move move1;
    private Move move2;

    /**
     * Teste l'ajout d'une action dans l'historique
     */
    @Test
    public void testAddAction() {
        history = new History();
        // Création des actions
        move1 = new Move(new Bee(player), new HexCoordinate(0, 0), new HexCoordinate(0, 1));
        move2 = new Move(new Ant(player), new HexCoordinate(0, 2), new HexCoordinate(1, 1));

        // Ajout des actions
        history.addAction(move1);
        history.addAction(move2);

        // Vérification de l'ajout des actions
        assertFalse(history.getHistory().isEmpty());
        assertEquals(move2, history.getHistory().peek());
    }

    /**
     * Teste l'annulation d'une action
     */
    @Test
    public void testCancelAction() {
        history = new History();
        // Création des actions
        move1 = new Move(new Bee(player), new HexCoordinate(0, 0), new HexCoordinate(0, 1));
        move2 = new Move(new Ant(player), new HexCoordinate(0, 2), new HexCoordinate(1, 1));

        // Ajout des actions
        history.addAction(move1);
        history.addAction(move2);

        // Vérification de l'annulation des actions
        Move cancelledMove2 = history.cancelAction();

        assertFalse(history.getHistory().isEmpty());

        Move cancelledMove1 = history.cancelAction();

        assertTrue(history.getHistory().isEmpty());

        assertFalse(history.canCancel());

        assertEquals(move1, cancelledMove1);
        assertEquals(move2, cancelledMove2);

    }

    /**
     * Teste la possibilité de refaire une action
     */
    @Test
    public void testRedoAction() {
        history = new History();
        // Création des actions
        move1 = new Move(new Bee(player), new HexCoordinate(0, 0), new HexCoordinate(0, 1));
        move2 = new Move(new Ant(player), new HexCoordinate(0, 2), new HexCoordinate(1, 1));

        // Vérification de la possibilité de refaire une action
        assertFalse(history.canRedo());

        // Ajout des actions
        history.addAction(move1);
        history.addAction(move2);

        // Annulation d'une action
        history.cancelAction();
        assertTrue(history.canRedo());

        // Vérification pour refaire une action
        Move redoMove2 = history.redoAction();

        assertFalse(history.getHistory().isEmpty());
        assertEquals(move2, redoMove2);
    }
}