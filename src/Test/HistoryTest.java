package Test;

import Model.Move;
import Model.History;
import Model.Insect.Ant;
import Model.Insect.Bee;
import Model.Player;
import Structure.HexCoordinate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test pour l'historique des moves
 */
public class HistoryTest {
    private Player player = new Player("Inspecteur blanco");
    private Move move1;
    private Move move2;

    @Before
    public void setUp() {
        this.player.setColor(Player.WHITE);
        this.move1 = new Move(new Bee(player), new HexCoordinate(0, 0), new HexCoordinate(0, 1));
        this.move2 = new Move(new Ant(player), new HexCoordinate(0, 2), new HexCoordinate(1, 1));
    }

    /**
     * Teste l'ajout d'une action dans l'historique
     */
    @Test
    public void testAddMove() {
        History history = new History();

        // Ajout des moves
        history.addMove(this.move1);
        history.addMove(this.move2);

        // Vérification de l'ajout des moves
        assertFalse(history.getHistory().isEmpty());
        assertEquals(move2, history.getHistory().peek());
    }

    /**
     * Teste l'annulation d'une move
     */
    @Test
    public void testCancelMove() {
        History history = new History();

        // Ajout des moves
        history.addMove(this.move1);
        history.addMove(this.move2);

        // Vérification de l'annulation des moves
        Move cancelledMove2 = history.cancelMove();

        assertFalse(history.getHistory().isEmpty());

        Move cancelledMove1 = history.cancelMove();

        assertTrue(history.getHistory().isEmpty());

        assertFalse(history.canCancel());

        assertEquals(this.move1, cancelledMove1);
        assertEquals(this.move2, cancelledMove2);

    }

    /**
     * Teste la possibilité de refaire une move
     */
    @Test
    public void testRedoMove() {
        History history = new History();

        // Vérification de la possibilité de refaire une move
        assertFalse(history.canRedo());

        // Ajout des moves
        history.addMove(this.move1);
        history.addMove(this.move2);

        // Annulation d'une move
        history.cancelMove();
        assertTrue(history.canRedo());

        // Vérification pour refaire une move
        Move redoMove2 = history.redoMove();

        assertFalse(history.getHistory().isEmpty());
        assertEquals(this.move2, redoMove2);
    }
}