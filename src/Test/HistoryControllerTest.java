package Test;

import Controller.HistoryController;
import Model.History;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test pour le controleur d'historique
 */
public class HistoryControllerTest {
    private HistoryController historyController;

    @Before
    public void setup() {
        this.historyController = new HistoryController(null);
    }

    /**
     * Teste la récupération de l'historique
     */
    @Test
    public void testGetHistory() {
        History history = this.historyController.getHistory();
        assertNotNull(history);
    }

    /**
     * Teste la définition de l'historique
     */
    @Test
    public void testSetHistory() {
        History newHistory = new History();
        this.historyController.setHistory(newHistory);
        assertEquals(newHistory, this.historyController.getHistory());
    }

}