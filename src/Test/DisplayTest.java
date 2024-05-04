package Test;

import Modele.Insect.Spider;
import Modele.Player;
import Vue.Display;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotNull;

public class DisplayTest {
    private Player player = new Player("white");
    @Test
    public void testLoadImage() {
        Image image = Display.loadImage(new Spider(player).getImageName());
        assertNotNull(image);
    }
}
