package Test;

import Model.History;
import Model.Player;
import Model.SaveLoad;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

public class SaveLoadTest {
    private History history = new History();
    private Player player1 = new Player("Player 1");
    private Player player2 = new Player("Player 2");
    private Player currentPlayer;
    private SaveLoad saveLoad = new SaveLoad(null);

    @Before
    public void setUp() {
        this.player1.setColor(0);
        this.player2.setColor(1);
        this.currentPlayer = this.player1;
    }

    @Test
    public void testSaveGame() throws Exception {
        String fileName = this.saveLoad.saveGame(this.history, this.player1, this.player2, this.currentPlayer);
        File file = new File(fileName);
        assertTrue(file.exists());
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testLoadGame() throws Exception {
        String fileName = this.saveLoad.saveGame(this.history, this.player1, this.player2, this.currentPlayer);
        this.saveLoad.loadGame(fileName);
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        assertEquals(this.history, this.saveLoad.getHistory());
        assertEquals(this.player1, this.saveLoad.getPlayer1());
        assertEquals(this.player2, this.saveLoad.getPlayer2());
        assertEquals(this.currentPlayer, this.saveLoad.getCurrentPlayer());
    }
}