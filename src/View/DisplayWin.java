package View;

import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayWin {

    JFrame frameWin;
    private static final String REPLAY = "New Game";
    private static final String MENU = "Menu";
    private PageActionHandler controller;

    public DisplayWin(JFrame frameWin, String player, PageActionHandler controller){
        this.controller = controller;
        this.frameWin = frameWin;
        JPanel column1 = createColumn();;
        JLabel Wintext = new JLabel("Victoire de" + player);
        frameWin.setSize(800, 600);
        frameWin.add(column1,BorderLayout.CENTER);
        //TODO : ajouter l'image de l'abeille de la bonne couleur
        frameWin.add(Wintext,BorderLayout.NORTH);
    }

    private JPanel createColumn() {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.add(createButton(REPLAY));
        column.add(createButton(MENU));
        return column;
    }
    //TODO: Faire les boutons tel Replay et Menu tel qu il renvoie au bon endroit
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
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
