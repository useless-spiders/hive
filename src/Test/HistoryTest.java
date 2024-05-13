package Test;

import Modele.Action;
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
    private Action action1;
    private Action action2;

    /**
     * Teste l'ajout d'une action dans l'historique
     */
    @Test
    public void testAddAction() {
        history = new History();
        // Création des actions
        action1 = new Action(new Bee(player), new HexCoordinate(0, 0), new HexCoordinate(0, 1));
        action2 = new Action(new Ant(player), new HexCoordinate(0, 2), new HexCoordinate(1, 1));

        // Ajout des actions
        history.addAction(action1);
        history.addAction(action2);

        // Vérification de l'ajout des actions
        assertFalse(history.getHistory().isEmpty());
        assertEquals(action2, history.getHistory().peek());
    }

    /**
     * Teste l'annulation d'une action
     */
    @Test
    public void testCancelAction() {
        history = new History();
        // Création des actions
        action1 = new Action(new Bee(player), new HexCoordinate(0, 0), new HexCoordinate(0, 1));
        action2 = new Action(new Ant(player), new HexCoordinate(0, 2), new HexCoordinate(1, 1));

        // Ajout des actions
        history.addAction(action1);
        history.addAction(action2);

        // Vérification de l'annulation des actions
        Action cancelledAction2 = history.cancelAction();

        assertFalse(history.getHistory().isEmpty());

        Action cancelledAction1 = history.cancelAction();

        assertTrue(history.getHistory().isEmpty());

        assertFalse(history.canCancel());

        assertEquals(action1, cancelledAction1);
        assertEquals(action2, cancelledAction2);

    }

    /**
     * Teste la possibilité de refaire une action
     */
    @Test
    public void testRedoAction() {
        history = new History();
        // Création des actions
        action1 = new Action(new Bee(player), new HexCoordinate(0, 0), new HexCoordinate(0, 1));
        action2 = new Action(new Ant(player), new HexCoordinate(0, 2), new HexCoordinate(1, 1));

        // Vérification de la possibilité de refaire une action
        assertFalse(history.canRedo());

        // Ajout des actions
        history.addAction(action1);
        history.addAction(action2);

        // Annulation d'une action
        history.cancelAction();
        assertTrue(history.canRedo());

        // Vérification pour refaire une action
        Action redoAction2 = history.redoAction();

        assertFalse(history.getHistory().isEmpty());
        assertEquals(action2, redoAction2);
    }
}