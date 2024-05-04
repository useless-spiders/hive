package Test;


import Modele.HexCell;
import Modele.HexGrid;

import Modele.Insect.Ant;
import Modele.Insect.Insect;
import Modele.Insect.Spider;
import Modele.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class InsectTest {
    private HexGrid grid = new HexGrid();
    private Player player = new Player("white");

    @Test
    public void testAntMaxCount() {
        HexCell cell = new HexCell();
        Insect insect = new Ant(player);
        for (int i = 0; i < insect.getMax(); i++) {
            cell.addInsect(new Ant(player));
        }
        assertThrows(IllegalArgumentException.class, () -> {
            cell.addInsect(new Ant(player));
        });
    }

    @Test
    public void testSpiderMaxCount() {
        HexCell cell = new HexCell();
        Insect insect = new Spider(player);
        for (int i = 0; i < insect.getMax(); i++) {
            cell.addInsect(new Spider(player));
        }
        assertThrows(IllegalArgumentException.class, () -> {
            cell.addInsect(new Spider(player));
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