package Test;

import Modele.HexCell;
import Modele.Insect.Ant;
import Modele.Insect.Bee;
import Modele.Insect.Insect;
import Modele.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test pour les cellules hexagonales
 */
public class HexCellTest {
    // Déclaration d'un joueur pour les tests
    private Player player = new Player("white", "Inspecteur blanco");

    /**
     * Teste l'ajout et la suppression d'insectes dans une cellule
     */
    @Test
    public void testAddAndRemoveTopInsect() {
        HexCell cell = new HexCell();
        Insect ant = new Ant(player);
        Insect bee = new Bee(player);

        // Teste l'ajout d'insectes
        cell.addInsect(ant);
        assertTrue(cell.getInsects().contains(ant));
        cell.addInsect(bee);
        assertTrue(cell.getInsects().contains(bee));

        // Teste la suppression d'un insecte
        cell.removeTopInsect();
        assertFalse(cell.getInsects().contains(bee));

        // Vérifie que la cellule n'est pas vide
        assertFalse(cell.getInsects().isEmpty());
    }

    /**
     * Teste la vérification de la présence d'un insecte dans une cellule
     */
    @Test
    public void testEquals() {
        HexCell cell1 = new HexCell();
        HexCell cell2 = new HexCell();
        Insect ant = new Ant(player);
        Insect bee = new Bee(player);
        Insect spider = new Bee(player);

        // Teste l'égalité entre deux cellules
        cell1.addInsect(ant);
        cell2.addInsect(ant);
        assertEquals(cell1, cell2);

        cell1.addInsect(bee);
        cell2.addInsect(spider);
        assertNotEquals(cell1, cell2);

        // Teste l'égalité entre deux cellules après suppression d'un insecte
        cell2.removeTopInsect();
        assertNotEquals(cell1, cell2);
    }
}