package View;

import Pattern.PageActionHandler;
import Structure.FrameMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayOpening extends JPanel {
    JFrame frame;
    private Image opening;
    private PageActionHandler controllerPage;

    public DisplayOpening(JFrame frame, PageActionHandler controllerPage) {
        this.frame = frame;
        this.controllerPage = controllerPage;

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
        button.addActionListener(e -> this.controllerPage.openingToMenu());
        return button;
    }

    @Override
    public void paintComponent(Graphics g) {
        //Affichage du background
        this.controllerPage.getDisplayBackground().paintBackground(g, frame, "opening.png");
    }
}


