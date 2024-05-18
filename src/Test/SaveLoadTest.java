package Test;

import Model.History;
import Model.Player;
import Model.SaveLoad;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

public class SaveLoadTest {
    private History history = new History();
    private Player player1 = new Player("white", "Player 1");
    private Player player2 = new Player("black", "Player 2");
    private Player currentPlayer = this.player1;

    @Test
    public void testSaveGame() throws Exception {
        String fileName = SaveLoad.saveGame(this.history, this.player1, this.player2, this.currentPlayer);
        File file = new File(fileName);
        assertTrue(file.exists());
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testLoadGame() throws Exception {
        String fileName = SaveLoad.saveGame(this.history, this.player1, this.player2, this.currentPlayer);
        SaveLoad saveLoad = SaveLoad.loadGame(fileName);
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        assertEquals(this.history, saveLoad.getHistory());
        assertEquals(this.player1, saveLoad.getPlayer1());
        assertEquals(this.player2, saveLoad.getPlayer2());
        assertEquals(this.currentPlayer, saveLoad.getCurrentPlayer());
    }
}