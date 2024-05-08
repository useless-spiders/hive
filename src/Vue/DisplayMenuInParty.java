package Vue;

import Pattern.GameActionHandler;
import Structures.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayMenuInParty {
    private static final String ANNULER = "annuler";
    private static final String REFAIRE = "refaire";
    private static final String REPLAY = "refaire partie";
    private static final String SELECTLVL = "choix du niveau";
    private static final String RULES = "regle";
    private static final String QUIT = "quitter";
    private GameActionHandler controller;

    public DisplayMenuInParty(JFrame frame, GameActionHandler controller) {
        JPanel mainPanel = new JPanel();
        this.controller = controller;
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel column1 = createColumn();
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(column1);
        //mainPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        mainPanel.add(Box.createHorizontalGlue());

        frame.getContentPane().add(mainPanel, BorderLayout.EAST);
    }

    private JPanel createColumn() {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.add(createButton(ANNULER));
        column.add(createButton(REFAIRE));
        column.add(createButton(REPLAY));
        column.add(createButton(SELECTLVL));
        column.add(createButton(RULES));
        column.add(createButton(QUIT));
        return column;
    }


    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        switch (text){
            case ANNULER:
                button.addActionListener(e -> controller.cancelAction());
                break;
            case REFAIRE:
                button.addActionListener(e -> controller.redoAction());
                break;
            case REPLAY:
                break;
            case SELECTLVL:
                break;
            case RULES:
                break;
            case QUIT:
                break;
            default:
                Log.addMessage("Erreur dans les boutons du menu en haut à droite du jeu");
        }
        // il faudra ajouter un listener
        return button;
    }

}
