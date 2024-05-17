package View;

import Controller.PageManager;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayWinFrame {

    JFrame winFrame;
    private static final String REPLAY = "New Game";
    private static final String MENU = "Menu";
    private PageActionHandler controller;

    public DisplayWinFrame(JFrame winFrame, String player,PageActionHandler controller){
        this.controller = controller;
        this.winFrame = winFrame;
        JPanel column1 = createColumn();;
        JLabel Wintext = new JLabel("Victoire de" + player);
        winFrame.setSize(800, 600);
        winFrame.add(column1,BorderLayout.CENTER);
        //TODO : ajouter l'image de l'abeille de la bonne couleur
        winFrame.add(Wintext,BorderLayout.NORTH);
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
                button.addActionListener(e -> controller.winFrameToMenu());
                break;
            default:
                Log.addMessage("Erreur dans les boutons de la WinFrame");
        }
        return button;
    }
}
