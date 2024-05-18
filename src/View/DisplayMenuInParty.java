package View;

import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayMenuInParty {
    private static final String DEFAULT = "                       ---";
    private static final String SAVE = "Sauvegarder";
    private static final String RULES = "Règles";
    private static final String NEWGAME = "Recommencer la partie";
    private static final String ABORT = "Abandonner la partie";

    private static final String CANCEL = "Annuler";
    private static final String REDO = "Refaire";


    private GameActionHandler controller;
    private JPanel panelGame;

    private PageActionHandler controllerPage;

    public DisplayMenuInParty(JPanel panelGame, GridBagConstraints gbc, GameActionHandler controller, PageActionHandler controllerPage) {
        this.panelGame = panelGame;
        this.controller = controller;
        this.controllerPage = controllerPage;
        JComboBox<String> menu = createMenu();
        JButton annulerButton = createButton(CANCEL);
        JButton refaireButton = createButton(REDO);

        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelGame.add(menu, gbc);
        panelGame.add(annulerButton, gbc);
        panelGame.add(refaireButton, gbc);
    }

    private JComboBox<String> createMenu() {
        String[] options = {DEFAULT, SAVE, RULES, NEWGAME, ABORT};
        JComboBox<String> menu = new JComboBox<>(options);
        menu.addActionListener(e -> {
            String selectedItem = (String) menu.getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case SAVE:
                        this.controller.saveGame();
                        break; // A REMPLIR PLUS TARD
                    case RULES:
                        break; // A REMPLIR PLUS TARD
                    case NEWGAME:
                        break; // A REMPLIR PLUS TARD
                    case ABORT:
                        controllerPage.gameAndAbort();
                        //controllerPage.gameToMenu();
                        break;
                    case DEFAULT:
                        break;
                    default:
                        Log.addMessage("Erreur dans les options du menu du jeu");
                }
                menu.setSelectedItem(DEFAULT);
            }
        });
        return menu;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(e -> {
            switch (text) {
                case CANCEL:
                    controller.cancelMove();
                    break;
                case REDO:
                    controller.redoMove();
                    break;
                default:
                    Log.addMessage("Erreur dans les boutons du jeu");
            }
        });
        return button;
    }
}