package Test;

import Controller.PlayerController;
import Model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for PlayerController
 */
public class PlayerControllerTest {
    private PlayerController playerController;

    @Before
    public void setup() {
        playerController = new PlayerController(null);
    }

    @Test
    public void testInitPlayers() {
        this.playerController.initPlayers();
        Player player1 = this.playerController.getPlayer1();
        Player player2 = this.playerController.getPlayer2();
        assertNotNull(player1);
        assertNotNull(player2);
        assertNotEquals(player1, player2);
        assertNotNull(this.playerController.getCurrentPlayer());
    }

}