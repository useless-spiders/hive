package Test;

import Modele.Insect.Spider;
import Vue.Display;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotNull;

public class DisplayTest {
    @Test
    public void testLoadImage() {
        Image image = Display.loadImage(new Spider("black").getImageName());
        assertNotNull(image);
    }
}
