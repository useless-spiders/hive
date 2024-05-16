package View;

import Controller.PageManager;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayMenuInParty {
    private static final String RETOUR = "retour";
    private static final String ANNULER = "annuler";
    private static final String REFAIRE = "refaire";
    private static final String REPLAY = "refaire partie";
    private static final String SELECTLVL = "choix du niveau";
    private static final String RULES = "regle";
    private static final String QUIT = "quitter";



    private GameActionHandler controller;
    private JPanel panelGame;

    private PageManager pageManager;
    private PageActionHandler controllerPage;

    public DisplayMenuInParty(JPanel panelGame, GridBagConstraints gbc, GameActionHandler controller, PageManager pageManager, PageActionHandler controllerPage) {
        this.panelGame = panelGame;
        this.controller = controller;
        this.controllerPage = controllerPage;
        this.pageManager = pageManager;
        JPanel column1 = createColumn();

        gbc.gridx = GridBagConstraints.RELATIVE; // Pour placer la colonne à droite du composant précédent
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST; // Pour placer la colonne dans le coin en haut à droite
        gbc.insets = new Insets(10, 10, 10, 10); // Ajouter des marges pour un meilleur espacement
        panelGame.add(column1, gbc);
    }

    private JPanel createColumn() {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.add(createButton(RETOUR));
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
            case RETOUR:
                button.addActionListener(e -> controllerPage.gameToMenu(pageManager));
                break;
            case ANNULER:
                button.addActionListener(e -> controller.cancelMove());
                break;
            case REFAIRE:
                button.addActionListener(e -> controller.redoMove());
                break;
            case REPLAY:
                break;
            case SELECTLVL:
                //button.addActionListener(e -> controllerPage.)
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
