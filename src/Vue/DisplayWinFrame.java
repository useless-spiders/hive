package Vue;

import Controleur.PageManager;
import Pattern.PageActionHandler;
import Structures.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayWinFrame {

    JFrame WinFrame;
    private static final String REPLAY = "New Game";
    private static final String MENU = "Menu";
    private PageManager pageManager;
    private PageActionHandler controller;

    public DisplayWinFrame(JFrame WinFrame, String player,PageManager pageManager,PageActionHandler controller){
        this.controller = controller;
        this.pageManager = pageManager;
        this.WinFrame = WinFrame;
        JPanel column1 = createColumn();;
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
                button.addActionListener(e -> controller.WinFrameToMenu(pageManager));
                break;
            default:
                Log.addMessage("Erreur dans les boutons de la WinFrame");
        }
        return button;
    }
}
