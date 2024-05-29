package Test;

import Model.Insect.Spider;
import Model.Player;
import Structure.RessourceLoader;
import View.DisplayMain;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotNull;

/**
 * Classe de test pour l'affichage
 */
public class RessourceLoaderTest {
    // Déclaration d'un joueur pour les tests
    private Player player = new Player("Inspecteur blanco");
    private RessourceLoader ressourceLoader = new RessourceLoader(null);

    @Before
    public void setUp() {
        this.player.setColor(0);
    }

    /**
     * Teste le chargement d'une image avec une araignée
     */
    @Test
    public void testLoadImage() {
        Image image = this.ressourceLoader.loadImageInsects(this.ressourceLoader.getImageInsectName(Spider.class, this.player, this.player));
        assertNotNull(image);

        image = this.ressourceLoader.loadImageHexagons("Full.png");
        assertNotNull(image);
    }
}
