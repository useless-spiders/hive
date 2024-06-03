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
        this.aiController = new AiController(null);
    }

    /**
     * Teste le démarrage de l'IA
     */
    @Test
    public void testStartAi() {
        this.aiController.startAi();
        assertTrue(this.aiController.isAiRunning());
    }

    /**
     * Teste l'arrêt de l'IA
     */
    @Test
    public void testStopAi() {
        this.aiController.startAi();
        this.aiController.stopAi();
        assertFalse(this.aiController.isAiRunning());
    }

}