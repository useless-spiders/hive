package View;

import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayWin extends JPanel{

    JFrame frameWin;
    private static final String REPLAY = "New Game";
    private static final String MENU = "Menu";
    private PageActionHandler controller;

    public DisplayWin(JFrame frameWin, PageActionHandler controller){
        this.controller = controller;
        this.frameWin = frameWin;
        JPanel column1 = createColumn();

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0); // Espacement entre le bouton et le haut de la fenêtre
        gbc.gridwidth = 2; // Nombre de colonnes occupées par le bouton
        gbc.gridheight = 1; // Nombre de lignes occupées par le bouton
        add(column1, gbc);


        //TODO : ajouter l'image de l'abeille de la bonne couleur
        frameWin.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frameWin.pack(); // Redimensionne la JFrame pour adapter le JPanel

    }

    private JPanel createColumn() {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        JLabel Wintext = new JLabel("Victoire de");
        Wintext.setAlignmentX(Component.CENTER_ALIGNMENT);
        column.add(Wintext);
        column.add(createButton(REPLAY));
        column.add(createButton(MENU));
        return column;
    }
    //TODO: Faire les boutons tel Replay et Menu tel qu il renvoie au bon endroit
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        switch (text) {
            case REPLAY:
                break;
            case MENU:
                button.addActionListener(e -> controller.winToMenu());
                break;
            default:
                Log.addMessage("Erreur dans les boutons de la frameWin");
        }
        return button;
    }
}
