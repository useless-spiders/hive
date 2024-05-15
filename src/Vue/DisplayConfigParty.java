package Vue;

import Controleur.PageManager;
import Pattern.PageActionHandler;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DisplayConfigParty extends JPanel {
    private static final String HUMAN = "human";
    private static final String IA_EASY = "ia facile";
    private static final String IA_HARD = "ia difficile";
    private static final String JOUER = "jouer";

    private PageManager pageManager;
    private PageActionHandler pageActionHandler;

    public DisplayConfigParty(JFrame frame, PageManager pageManager, PageActionHandler controllerPage){
        this.pageManager = pageManager;
        this.pageActionHandler = controllerPage;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel column1 = createColumn("j1");
        JPanel column2 = createColumn("j2");

        gbc.gridx = 0; // Placer la première colonne à la première colonne
        gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER; // Ajuster la hauteur de la cellule pour remplir la hauteur de la grille
        gbc.gridwidth = 1; // Une cellule de large pour la première colonne
        gbc.anchor = GridBagConstraints.CENTER; // Centrer la première colonne dans la cellule
        gbc.insets = new Insets(10, 10, 10, 10); // Ajouter des marges pour un meilleur espacement
        add(column1, gbc);

        gbc.gridx = 1; // Placer la deuxième colonne à la deuxième colonne
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Ajuster la largeur de la cellule pour remplir la grille
        add(column2, gbc);

        gbc.gridy = GridBagConstraints.RELATIVE; // Pour que le bouton soit positionné en dessous de la dernière composante ajoutée
        gbc.gridwidth = 2; // Span sur deux colonnes
        gbc.anchor = GridBagConstraints.PAGE_END; // Aligner en bas
        add(createButton(JOUER), gbc);

        frame.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frame.pack(); // Redimensionne la JFrame pour adapter le JPanel
    }

    private JPanel createColumn(String label) {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.add(createLabel(label)); // nom de la colonne
        column.add(createButton(HUMAN));
        column.add(createButton(IA_EASY));
        column.add(createButton(IA_HARD));
        return column;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        switch (text){
            case JOUER:
                button.addActionListener(e -> pageActionHandler.menuToGame(pageManager));
        }
        // il faudra ajouter un listener
        return button;
    }
}