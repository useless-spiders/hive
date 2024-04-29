package Vue;

import Modele.Grille;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;


public class Affichage extends JComponent {
    private ZoneGauffre zoneGauffre;
    private ZoneBouton zoneBouton;
    private JFrame frame;

    public static Image charge(String nom) {
        try {
            return ImageIO.read(new FileInputStream(nom));
        } catch (Exception e) {
            System.err.println("Impossible de charger l'image " + nom);
            System.exit(1);
            return null; //tatatata
        }
    }

    public Affichage(Grille grille, JFrame frame) {
        this.frame = frame;
        this.zoneGauffre = new ZoneGauffre(grille);
        this.zoneBouton = new ZoneBouton(frame);


    }

    @Override
    protected void paintComponent(Graphics g) {
        this.zoneGauffre.setTaille(getSize().width - getSize().width / 3, getSize().height);
        this.zoneGauffre.paintGauffre(g);
    }

    public ZoneGauffre getZoneGauffre() {
        return zoneGauffre;
    }
}
