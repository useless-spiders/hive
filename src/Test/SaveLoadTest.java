package Test;

import Model.History;
import Model.Player;
import Model.SaveLoad;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

/**
 * Classe de test pour la sauvegarde et le chargement de parties
 */
public class SaveLoadTest {
    private History history = new History();
    private Player player1 = new Player("white", "Player 1");
    private Player player2 = new Player("white", "Player 1");
    private Player currentPlayer = this.player1;
    private SaveLoad saveLoad = new SaveLoad(this.history, this.player1, this.player2, this.currentPlayer);

    /**
     * Teste la sauvegarde d'une partie
     */
    @Test
    public void testSaveGame() throws Exception {
        this.saveLoad.saveGame();
        File file = new File(this.saveLoad.getFileName());
        assertTrue(file.exists());
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Teste le chargement d'une partie
     */
    @Test
    public void testLoadGame() throws Exception {
        this.saveLoad.saveGame();
        File file = new File(this.saveLoad.getFileName());
        this.saveLoad.loadGame(this.saveLoad.getFileName());
        if (file.exists()) {
            file.delete();
        }
        assertEquals(this.history, this.saveLoad.getHistory());
        assertEquals(this.player1, this.saveLoad.getPlayer1());
        assertEquals(this.player2, this.saveLoad.getPlayer2());
        assertEquals(this.currentPlayer, this.saveLoad.getCurrentPlayer());
    }
}
