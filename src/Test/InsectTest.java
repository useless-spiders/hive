package Test;


import Modele.HexCell;
import Modele.HexGrid;

import Modele.Insect.Ant;
import Modele.Insect.Insect;
import Modele.Insect.Spider;
import org.junit.Test;

import static org.junit.Assert.*;

public class InsectTest {
    private HexGrid grid = new HexGrid();

    @Test
    public void testAntMaxCount() {
        HexCell cell = new HexCell();
        Insect insect = new Ant("white");
        for (int i = 0; i < insect.getMax(); i++) {
            cell.addInsect(new Ant("white"));
        }
        assertThrows(IllegalArgumentException.class, () -> {
            cell.addInsect(new Ant("white"));
        });
    }

    @Test
    public void testSpiderMaxCount() {
        HexCell cell = new HexCell();
        Insect insect = new Spider("white");
        for (int i = 0; i < insect.getMax(); i++) {
            cell.addInsect(new Spider("white"));
        }
        assertThrows(IllegalArgumentException.class, () -> {
            cell.addInsect(new Spider("white"));
        });
    }

    @Test
    public void testBee() {
        
    }

    @Test
    public void testBeetle() {
        
    }

    @Test
    public void testGrasshopper() {
        
    }

    @Test
    public void testSpider() {
        
    }

    @Test
    public void testAnt() {
        
    }

    
    
}