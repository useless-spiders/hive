package Vue;

import Modele.HexGrid;

import javax.swing.*;
import java.awt.*;

public class DisplayHexGrid extends JComponent {
    //Image imageAraignerBlanche = Display.charge("Araigner_blanche.png");
    private HexGrid hexGrid;
    private int taille;

    public DisplayHexGrid(HexGrid hexGrid) {
        this.hexGrid = hexGrid;
    }

    public void paintHexGrid(Graphics g) {
        //g.drawImage(imageAraignerBlanche, getSize().width/2, getSize().height/2, 50, 50, null);
}   }
