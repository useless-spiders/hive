package Vue;

import Controleur.PageManager;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structures.FrameMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayOpening extends JPanel{
    JFrame frame;
    private Image opening;
    private GameActionHandler controller;
    private PageActionHandler controllerPage;
    private PageManager pageManager;

    public DisplayOpening(JFrame frame, PageManager pageManager, PageActionHandler controllerPage) {
        this.frame = frame;
        this.controllerPage = controllerPage;
        this.pageManager = pageManager;

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0); // Espacement entre le bouton et le haut de la fenêtre
        gbc.gridwidth = 2; // Nombre de colonnes occupées par le bouton
        gbc.gridheight = 1; // Nombre de lignes occupées par le bouton
        add(createButton("Jouer"), gbc);

        frame.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frame.pack(); // Redimensionne la JFrame pour adapter le JPanel
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(e -> controllerPage.openingToMenu(pageManager));
        return button;
    }

    @Override
    public void paintComponent(Graphics g) {
        //Affichage du background
        this.opening = DisplayGame.loadBackground("Opening.png");
        Dimension frameSize = FrameMetrics.getFrameSize(frame);
        g.drawImage(opening, 0, 0, frameSize.width, frameSize.height, this);

        //Affichage du message A MODIFIER
        Font font = new Font("Times New Roman", Font.BOLD, 30);
        g.setFont(font); // Appliquer la police définie
        String text = "Appuyer sur une touche";
        int x = frameSize.width/3; // Position x
        int y = frameSize.height/2; // Position y
        g.drawString(text, x, y);
    }
}


