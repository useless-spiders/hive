package Test;

import Vue.Affichage;
import org.junit.Test;
import java.awt.Image;
import static org.junit.Assert.*;

public class AffichageTest {

    @Test
    public void testCharge() {
        Image image = Affichage.charge("res/Images/gauffre.png");
        assertNotNull("L'image ne doit pas être null", image);
    }

}