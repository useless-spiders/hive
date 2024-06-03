package Test;

import Controller.AiController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Classe de test pour le controleur d'IA
 */
public class AiControllerTest {
    private AiController aiController;

    @Before
    public void setup() {
        aiController = new AiController(null);
    }

    /**
     * Teste le démarrage de l'IA
     */
    @Test
    public void testStartAi() {
        aiController.startAi();
        assertTrue(aiController.isAiRunning());
    }

    /**
     * Teste l'arrêt de l'IA
     */
    @Test
    public void testStopAi() {
        aiController.startAi();
        aiController.stopAi();
        assertFalse(aiController.isAiRunning());
    }

}