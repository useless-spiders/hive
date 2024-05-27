package Test;

import Model.Insect.Spider;
import Model.Player;
import View.DisplayMain;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotNull;

/**
 * Classe de test pour l'affichage
 */
public class DisplayTest {
    // Déclaration d'un joueur pour les tests
    private Player player = new Player("Inspecteur blanco");

    @Before
    public void setUp() {
        this.player.setColor(0);
    }

    /**
     * Teste le chargement d'une image avec une araignée
     */
    @Test
    public void testLoadImage() {
        Image image = DisplayMain.loadImageInsects(DisplayMain.getImageInsectName(Spider.class, this.player));
        assertNotNull(image);

        image = DisplayMain.loadImageHexagons("Full.png");
        assertNotNull(image);
    }
}
