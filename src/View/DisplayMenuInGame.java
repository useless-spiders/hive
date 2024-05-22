package View;

import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayMenuInGame {
    private static final String DEFAULT = "                       ---";
    private static final String SAVE = "Sauvegarder";
    private static final String RULES = "Règles";
    private static final String NEWGAME = "Recommencer la partie";
    private static final String ABORT = "Abandonner la partie";

    private GameActionHandler gameActionHandler;
    private JPanel panelGame;
    private JButton cancelButton;
    private JButton redoButton;
    private JButton changeStateAI;

    private PageActionHandler pageActionHandler;

    public DisplayMenuInGame(JPanel panelGame, GridBagConstraints gbc, GameActionHandler gameActionHandler, PageActionHandler pageActionHandler) {
        this.panelGame = panelGame;
        this.gameActionHandler = gameActionHandler;
        this.pageActionHandler = pageActionHandler;

        // Création du JPanel pour contenir le menu et les boutons
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setOpaque(false);

        // Création des boutons annuler et refaire
        this.cancelButton = createButtonCancel();
        this.redoButton = createButtonRedo();
        this.changeStateAI = createButtonChangeStateAI();

        // Création du menu
        JComboBox<String> menu = createMenu();
        menu.setFocusable(false);

        // Ajout du menu au JPanel avec les contraintes pour le positionner dans le coin nord-est
        GridBagConstraints cancelButtonConstraints = new GridBagConstraints();
        cancelButtonConstraints.gridx = 0;
        cancelButtonConstraints.gridy = 0;
        cancelButtonConstraints.anchor = GridBagConstraints.NORTHEAST;
        cancelButtonConstraints.insets = new Insets(10, 10, 10, 10);
        menuPanel.add(this.cancelButton, cancelButtonConstraints);


        // Ajout du bouton annuler au JPanel avec les contraintes pour le positionner à gauche du menu
        GridBagConstraints redoButtonConstraints = new GridBagConstraints();
        redoButtonConstraints.gridx = 1; // à droite du menu
        redoButtonConstraints.gridy = 0;
        redoButtonConstraints.anchor = GridBagConstraints.NORTHEAST;
        redoButtonConstraints.insets = new Insets(10, 10, 10, 10);
        menuPanel.add(this.redoButton, redoButtonConstraints);

        // Ajout du bouton change state
        GridBagConstraints changeStateAiConstraints = new GridBagConstraints();
        changeStateAiConstraints.gridx = 0; // à droite du menu
        changeStateAiConstraints.gridy = 1;
        changeStateAiConstraints.anchor = GridBagConstraints.NORTHEAST;
        changeStateAiConstraints.insets = new Insets(10, 10, 10, 10);
        menuPanel.add(this.changeStateAI, changeStateAiConstraints);

        // Ajout du bouton refaire au JPanel avec les contraintes pour le positionner à droite du menu
        GridBagConstraints menuConstraints = new GridBagConstraints();
        menuConstraints.gridx = 2; // à droite du bouton annuler
        menuConstraints.gridy = 0;
        menuConstraints.anchor = GridBagConstraints.NORTHEAST;
        menuConstraints.insets = new Insets(10, 10, 10, 10);
        menuPanel.add(menu, menuConstraints);

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
                        this.gameActionHandler.saveGame();
                        break;
                    case RULES:
                        this.pageActionHandler.gameAndRule();
                        break; // A REMPLIR PLUS TARD
                    case NEWGAME:
                        this.gameActionHandler.restartGameWithSamePlayers();
                        break;
                    case ABORT:
                        this.gameActionHandler.stopAi();
                        this.pageActionHandler.gameToMenu();
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

    private JButton createButtonCancel() {
        JButton button = new JButton(DisplayMain.loadIcon("Undo.png"));
        button.setEnabled(this.gameActionHandler.getHistory().canCancel() && !this.gameActionHandler.getCurrentPlayer().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.cancelMove();
            updateButtonsState();
        });
        return button;
    }

    private JButton createButtonRedo() {
        JButton button = new JButton(DisplayMain.loadIcon("Redo.png"));
        button.setEnabled(this.gameActionHandler.getHistory().canRedo() && !this.gameActionHandler.getCurrentPlayer().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.redoMove();
            updateButtonsState();
        });
        return button;
    }

    private JButton createButtonChangeStateAI() {
        JButton button = new JButton("Stopper / Relancer IA");
        // setEnabled ne marche pas pour l'instant a cause de l'incohérence des états
        button.setEnabled(this.gameActionHandler.getPlayer1().isAi() && this.gameActionHandler.getPlayer2().isAi());
        button.addActionListener(e -> this.gameActionHandler.changeStateAi());
        return button;
    }

    public void updateButtonsState() {
        this.cancelButton.setEnabled(this.gameActionHandler.getHistory().canCancel());
        this.redoButton.setEnabled(this.gameActionHandler.getHistory().canRedo());
    }

    public void updateGameActionHandler() {
        this.cancelButton.setEnabled(this.gameActionHandler.getHistory().canCancel() && !this.gameActionHandler.getCurrentPlayer().isAi());
        this.redoButton.setEnabled(this.gameActionHandler.getHistory().canRedo() && !this.gameActionHandler.getCurrentPlayer().isAi());
        this.changeStateAI.setEnabled(this.gameActionHandler.getPlayer1().isAi() && this.gameActionHandler.getPlayer2().isAi());
    }
}