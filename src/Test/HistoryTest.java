package Test;

import Modele.Action;
import Modele.History;
import org.junit.Test;

import static org.junit.Assert.*;

public class HistoryTest {
    private History history;
    private Action action1;
    private Action action2;

    @Test
    public void testAddAction() {
        history = new History();
        action1 = new Action();
        action2 = new Action();

        history.addAction(action1);
        history.addAction(action2);

        assertFalse(history.getHistory().isEmpty());
        assertEquals(action2, history.getHistory().peek());
    }

    @Test
    public void testCancelAction() {
        history = new History();
        action1 = new Action();
        action2 = new Action();

        history.addAction(action1);
        history.addAction(action2);

        Action cancelledAction2 = history.cancelAction();

        assertFalse(history.getHistory().isEmpty());

        Action cancelledAction1 = history.cancelAction();

        assertTrue(history.getHistory().isEmpty());

        assertFalse(history.canCancel());

        assertEquals(action1, cancelledAction1);
        assertEquals(action2, cancelledAction2);

    }

    @Test
    public void testRedoAction() {
        history = new History();
        action1 = new Action();
        action2 = new Action();

        history.addAction(action1);
        history.addAction(action2);

        history.cancelAction();
        assertTrue(history.canRedo());

        Action redoAction2 = history.redoAction();

        assertFalse(history.getHistory().isEmpty());
        assertEquals(action2, redoAction2);
    }
}