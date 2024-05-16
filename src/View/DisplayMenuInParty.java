package View;

import Controller.PageManager;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayMenuInParty {
    private static final String SAUVEGARDER = "Sauvegarder";
    private static final String REGLES = "Règles";
    private static final String NEW = "Recommencer la partie";
    private static final String MENU = "Menu principal";

    private static final String ANNULER = "Annuler";
    private static final String REFAIRE = "Refaire";


    private GameActionHandler controller;
    private JPanel panelGame;

    private PageManager pageManager;
    private PageActionHandler controllerPage;

    public DisplayMenuInParty(JPanel panelGame, GridBagConstraints gbc, GameActionHandler controller, PageManager pageManager, PageActionHandler controllerPage) {
        this.panelGame = panelGame;
        this.controller = controller;
        this.controllerPage = controllerPage;
        this.pageManager = pageManager;
        JComboBox<String> menu = createMenu();
        JButton annulerButton = createButton(ANNULER);
        JButton refaireButton = createButton(REFAIRE);

        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelGame.add(menu, gbc);
        panelGame.add(annulerButton, gbc);
        panelGame.add(refaireButton, gbc);
    }

    private JComboBox<String> createMenu() {
        String[] options = {SAUVEGARDER, REGLES, NEW, MENU};
        JComboBox<String> menu = new JComboBox<>(options);
        menu.setEditable(true); // Rendre la JComboBox éditable
        menu.setSelectedItem("                       - - -"); // Définir le texte initial
        menu.addActionListener(e -> {
            String selectedItem = (String) menu.getSelectedItem();
            if (selectedItem != null && !selectedItem.equals("MENU")) {
                switch (selectedItem) {
                    case SAUVEGARDER:
                        break; // A REMPLIR PLUS TARD
                    case REGLES:
                        break; // A REMPLIR PLUS TARD
                    case NEW:
                        break; // A REMPLIR PLUS TARD
                    case MENU:
                        controllerPage.gameToMenu(pageManager);
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
                case ANNULER:
                    controller.cancelMove();
                    break;
                case REFAIRE:
                    controller.redoMove();
                    break;
                default:
                    Log.addMessage("Erreur dans les boutons du jeu");
            }
        });
        return button;
    }
}

