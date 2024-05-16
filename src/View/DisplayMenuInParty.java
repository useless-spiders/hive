package View;

import Controller.PageManager;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayMenuInParty {
    private static final String SAVE = "Sauvegarder";
    private static final String RULES = "Règles";
    private static final String NEWGAME = "Recommencer la partie";
    private static final String MENU = "Menu principal";

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
        String[] options = {SAVE, RULES, NEWGAME, MENU};
        JComboBox<String> menu = new JComboBox<>(options);
        menu.setEditable(true); // Rendre la JComboBox éditable
        menu.setSelectedItem("                       - - -"); // Définir le texte initial
        menu.addActionListener(e -> {
            String selectedItem = (String) menu.getSelectedItem();
            if (selectedItem != null && !selectedItem.equals("MENU")) {
                switch (selectedItem) {
                    case SAVE:
                        break; // A REMPLIR PLUS TARD
                    case RULES:
                        break; // A REMPLIR PLUS TARD
                    case NEWGAME:
                        break; // A REMPLIR PLUS TARD
                    case MENU:
                        controllerPage.gameToMenu();
                    default:
                        Log.addMessage("Erreur dans les options du menu du jeu");
                }
                // Réinitialiser la sélection à "MENU" après l'action
                menu.setSelectedItem("                       - - -");
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