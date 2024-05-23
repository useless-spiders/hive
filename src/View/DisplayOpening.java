package View;

import Pattern.PageActionHandler;
import Structure.FrameMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayOpening extends JPanel {
    JFrame frameOpening;
    private Image background  = DisplayMain.loadBackground("Opening.png");
    private PageActionHandler pageActionHandler;

    public DisplayOpening(JFrame frameOpening, PageActionHandler pageActionHandler) {
        this.frameOpening = frameOpening;
        this.pageActionHandler = pageActionHandler;

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0); // Espacement entre le bouton et le haut de la fenêtre
        gbc.gridwidth = 2; // Nombre de colonnes occupées par le bouton
        gbc.gridheight = 1; // Nombre de lignes occupées par le bouton
        add(createButton("Jouer"), gbc);

        frameOpening.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frameOpening.pack(); // Redimensionne la JFrame pour adapter le JPanel
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(e -> this.pageActionHandler.openingToMenu());
        return button;
    }

    @Override
    public void paintComponent(Graphics g) {
        //Affichage du background
        g.drawImage(this.background, 0, 0, this.frameOpening.getWidth(), this.frameOpening.getHeight(), this);
    }
}
