package Test;

import Modele.Insect.Spider;
import Modele.Player;
import Vue.DisplayGame;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotNull;

/**
 * Classe de test pour l'affichage
 */
public class DisplayGameTest {
    // Déclaration d'un joueur pour les tests
    private Player player = new Player("white", "Inspecteur blanco");

    /**
     * Teste le chargement d'une image avec une araignée
     */
    @Test
    public void testLoadImage() {
        Image image = DisplayGame.loadImage(DisplayGame.getImageName(Spider.class, player));
        assertNotNull(image);
    }
}
