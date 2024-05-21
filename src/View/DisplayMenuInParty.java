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

        // Création du JPanel pour contenir le menu et les boutons
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setOpaque(false);

        // Création du menu
        JComboBox<String> menu = createMenu();
        // Ajout du menu au JPanel avec les contraintes pour le positionner dans le coin nord-est
        GridBagConstraints menuConstraints = new GridBagConstraints();
        menuConstraints.gridx = 0;
        menuConstraints.gridy = 0;
        menuConstraints.anchor = GridBagConstraints.NORTHEAST;
        menuConstraints.insets = new Insets(10, 10, 10, 10);
        menuPanel.add(menu, menuConstraints);

        // Création des boutons annuler et refaire
        JButton annulerButton = createButton(CANCEL);
        JButton refaireButton = createButton(REDO);

        // Ajout du bouton annuler au JPanel avec les contraintes pour le positionner à gauche du menu
        GridBagConstraints annulerButtonConstraints = new GridBagConstraints();
        annulerButtonConstraints.gridx = 1; // à droite du menu
        annulerButtonConstraints.gridy = 0;
        annulerButtonConstraints.anchor = GridBagConstraints.NORTHEAST;
        annulerButtonConstraints.insets = new Insets(5, 5, 5, 5);
        menuPanel.add(annulerButton, annulerButtonConstraints);

        // Ajout du bouton refaire au JPanel avec les contraintes pour le positionner à droite du menu
        GridBagConstraints refaireButtonConstraints = new GridBagConstraints();
        refaireButtonConstraints.gridx = 2; // à droite du bouton annuler
        refaireButtonConstraints.gridy = 0;
        refaireButtonConstraints.anchor = GridBagConstraints.NORTHEAST;
        refaireButtonConstraints.insets = new Insets(5, 5, 5, 5);
        menuPanel.add(refaireButton, refaireButtonConstraints);

        // Ajout du JPanel contenant le menu et les boutons à panelGame avec les contraintes pour le positionner dans le coin nord-est
        gbc.gridx = 1; // colonne (commence à 0)
        gbc.gridy = 0; // ligne (commence à 0)
        gbc.anchor = GridBagConstraints.NORTHEAST; // ancre dans le coin nord-est
        gbc.weightx = 1.0; // étendre horizontalement
        gbc.weighty = 1.0; // étendre verticalement
        panelGame.add(menuPanel, gbc);
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
                        break;
                    case RULES:
                        break; // A REMPLIR PLUS TARD
                    case NEWGAME:
                        this.controller.restartGameWithSamePlayers();
                        break;
                    case ABORT:
                        //controllerPage.gameAndAbort();
                        this.controllerPage.gameToMenu();
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
                    this.controller.cancelMove();
                    break;
                case REDO:
                    this.controller.redoMove();
                    break;
                default:
                    Log.addMessage("Erreur dans les boutons du jeu");
            }
        });
        return button;
    }
}