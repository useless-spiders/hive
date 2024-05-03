package Test;

import Vue.Display;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotNull;

public class DisplayTest {
    @Test
    public void testLoadImage() {
        Image image = Display.loadImage("res/Images/Araignee_blanche.png");
        assertNotNull(image);
    }
}
