package Vue;

import Pattern.GameActionHandler;
import Structures.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayWinFrame {
    private static final String REPLAY = "New Game";
    private static final String MENU = "Menu";


    private GameActionHandler controller;

    public DisplayWinFrame(String player,GameActionHandler controller){
        this.controller = controller;
        JPanel column1 = createColumn();
        JFrame WinFrame = new JFrame("Victoire");
        JLabel Wintext = new JLabel("Victoire de" + player);
        WinFrame.setSize(800, 600);
        WinFrame.add(column1,BorderLayout.CENTER);
        //TODO : ajouter l'image de l'abeille de la bonne couleur
        WinFrame.add(Wintext,BorderLayout.NORTH);
    }

    private JPanel createColumn() {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.add(createButton(REPLAY));
        column.add(createButton(MENU));
        return column;
    }
    //TODO: Faire les boutons tel Replay et Menu tel qu il renvoie au bonne endroit
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        switch (text) {
            case REPLAY:
                break;
            case MENU:
                break;
            default:
                Log.addMessage("Erreur dans les boutons de la WinFrame");
        }
        return button;
    }
}
