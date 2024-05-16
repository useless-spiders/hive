package Test;

import Model.Insect.Spider;
import Model.Player;
import View.Display;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotNull;

/**
 * Classe de test pour l'affichage
 */
public class DisplayTest {
    // Déclaration d'un joueur pour les tests
    private Player player = new Player("white", "Inspecteur blanco");

    /**
     * Teste le chargement d'une image avec une araignée
     */
    @Test
    public void testLoadImage() {
        Image image = Display.loadImage(Display.getImageName(Spider.class, player));
        assertNotNull(image);
    }
}
