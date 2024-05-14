package Vue;

import Structures.HexCoordinate;

import javax.swing.*;
import java.awt.*;

public class DisplayOpening extends JPanel{
    JFrame frame;
    private Image opening;

    public DisplayOpening(JFrame frame) {
        this.frame = frame;
        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new BorderLayout()); // Définir le layout du JPanel
        frame.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frame.pack(); // Redimensionne la JFrame pour adapter le JPanel
    }

    @Override
    public void paintComponent(Graphics g) {
        //Affichage du background
        this.opening = Display.loadBackground("Opening.png");
        Dimension frameSize = getFrameSize();
        g.drawImage(opening, 0, 0, frameSize.width, frameSize.height, this);

        //Affichage du message A MODIFIER
        Font font = new Font("Times New Roman", Font.BOLD, 30);
        g.setFont(font); // Appliquer la police définie
        String text = "Appuyer sur une touche";
        int x = frameSize.width/3; // Position x
        int y = frameSize.height/2; // Position y
        g.drawString(text, x, y);
    }

    //Récupérer la taille de la fenêtre, notamment pour l'affichage du background
    //Si réutiliséee dans d'autres classes, à mettre ailleurs
    public Dimension getFrameSize() {
        return frame.getSize();
    }
}


