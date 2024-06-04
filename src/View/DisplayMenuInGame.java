package View;

import Pattern.GameActionHandler;
import Structure.Log;
import Structure.RessourceLoader;

import javax.swing.*;
import java.awt.*;

public class DisplayMenuInGame extends JFrame {
    private final String SAVE;
    private final String RULES;
    private final String RESTART;
    private final String ABORT;
    private final String RETURN;

    private final GameActionHandler gameActionHandler;
    private final RessourceLoader ressourceLoader;
    private final JPanel panelGame;
    private final JPanel optionsPanel;
    private final JButton cancelButton;
    private final JButton redoButton;
    private final JButton changeStateAIButton;
    private JButton saveButton;
    private JButton rulesButton;
    private JButton restartButton;
    private JButton abortButton;
    private JButton returnButton;

    private boolean optionVisible;

    public DisplayMenuInGame(JPanel panelGame, GridBagConstraints gbc, GameActionHandler gameActionHandler) {
        this.panelGame = panelGame;
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);

        this.SAVE = this.gameActionHandler.getLang().getString("display.menu.save");
        this.RULES = this.gameActionHandler.getLang().getString("display.menu.rules");
        this.RESTART = this.gameActionHandler.getLang().getString("display.menu.restart");
        this.ABORT = this.gameActionHandler.getLang().getString("display.menu.abort");
        this.RETURN = this.gameActionHandler.getLang().getString("display.menu.return");

        this.optionVisible = false;
        // Création du JPanel pour contenir le menu et les boutons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        this.optionsPanel = this.createOptionPanel();

        // Création des boutons annuler et refaire
        this.cancelButton = this.createButtonCancel();
        this.redoButton = this.createButtonRedo();

        this.changeStateAIButton = this.createButtonChangeStateAI();
        JButton optionButton = this.createButtonOption();

        // Ajout du menu au JPanel avec les contraintes pour le positionner dans le coin nord-est
        GridBagConstraints gbcButtonInGame = new GridBagConstraints();
        gbcButtonInGame.gridx = 0; // à droite du menu
        gbcButtonInGame.gridy = 0;
        gbcButtonInGame.anchor = GridBagConstraints.NORTHEAST;
        gbcButtonInGame.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(this.changeStateAIButton, gbcButtonInGame);

        gbcButtonInGame.gridx = 1;
        gbcButtonInGame.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(this.cancelButton, gbcButtonInGame);

        // Ajout du bouton annuler au JPanel avec les contraintes pour le positionner à gauche du menu
        gbcButtonInGame.gridx = 2; // à droite du menu
        gbcButtonInGame.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(this.redoButton, gbcButtonInGame);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        panelGame.add(this.optionsPanel, gbc);


        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;

        // Ajout du JPanel contenant le menu et les boutons à panelGame avec les contraintes pour le positionner dans le coin nord-est
        gbc.gridy = 3; // Row (starts from 0)
        gbc.insets = new Insets(10, 10, 0, 10); // Add some padding
        panelGame.add(buttonPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(10, 10, 0, 10); // Add some padding
        panelGame.add(optionButton, gbc);

    }

    private JPanel createOptionPanel() {
        JPanel optionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints optionGbc = new GridBagConstraints();
        optionPanel.setVisible(false);
        optionPanel.setBackground(new Color(88, 41, 0, 75));
        optionGbc.gridx = 0;
        optionGbc.gridy = 0;
        optionGbc.insets = new Insets(10, 10, 10, 10);
        optionGbc.anchor = GridBagConstraints.CENTER;
        this.saveButton = this.createButton(this.SAVE);
        optionPanel.add(this.saveButton, optionGbc);
        optionGbc.gridy = 1;
        this.rulesButton = this.createButton(this.RULES);
        optionPanel.add(this.rulesButton, optionGbc);
        optionGbc.gridy = 2;
        this.restartButton = this.createButton(this.RESTART);
        optionPanel.add(this.restartButton, optionGbc);
        optionGbc.gridy = 3;
        this.abortButton = this.createButton(this.ABORT);
        optionPanel.add(this.abortButton, optionGbc);
        optionGbc.gridy = 4;
        this.returnButton = this.createButton(this.RETURN);
        optionPanel.add(this.returnButton, optionGbc);

        // Définir une taille plus grande pour optionsPanel
        optionPanel.setPreferredSize(new Dimension(300, 300));

        return optionPanel;
    }

    private JButton createButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(e -> {
            if (name.equals(this.SAVE)) {
                this.gameActionHandler.getSaveLoadController().saveGame();
                DisplayText.addTextPopUp(this.gameActionHandler.getLang().getString("display.menu.save.game"), this.gameActionHandler.getDisplayGame().getFrameGame());
            } else if (name.equals(this.RULES)) {
                this.gameActionHandler.getPageController().gameAndRules();
            } else if (name.equals(this.RESTART)) {
                this.gameActionHandler.getPageController().gameAndRestart();
                this.optionsPanel.setVisible(false);
                this.optionVisible = false;
            } else if (name.equals(this.ABORT)) {
                this.gameActionHandler.getPageController().gameAndAbort();
                this.optionsPanel.setVisible(false);
                this.optionVisible = false;
            } else if (name.equals(this.RETURN)) {
                this.optionsPanel.setVisible(false);
                this.optionVisible = false;
                this.gameActionHandler.getAiController().startAi();
            } else {
                Log.addMessage(this.gameActionHandler.getLang().getString("display.menu.error"));
            }
        });
        button.setFocusable(false);
        return button;
    }

    private JButton createButtonOption() {
        JButton button = new JButton(this.ressourceLoader.loadIcon("Menu.png"));
        button.addActionListener(e -> {
            if (this.optionVisible) {
                this.optionsPanel.setVisible(false);
                this.optionVisible = false;
                this.gameActionHandler.getAiController().startAi();
            } else {
                this.optionsPanel.setVisible(true);
                this.optionVisible = true;
                this.gameActionHandler.getAiController().stopAi();
            }
            if (this.gameActionHandler.getAiController().isAiRunning()) {
                this.changeStateAIButton.setText(this.gameActionHandler.getLang().getString("display.menu.ai.stop"));
            } else {
                this.changeStateAIButton.setText(this.gameActionHandler.getLang().getString("display.menu.ai.restart"));
            }
        });
        button.setFocusable(false);
        return button;
    }

    private JButton createButtonCancel() {
        JButton button = new JButton(this.ressourceLoader.loadIcon("Undo.png"));
        button.setEnabled(this.gameActionHandler.getHistoryController().getHistory().canCancel() && !this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.getHistoryController().cancelMove();
        });
        button.setFocusable(false);
        return button;
    }

    private JButton createButtonRedo() {
        JButton button = new JButton(this.ressourceLoader.loadIcon("Redo.png"));
        button.setEnabled(this.gameActionHandler.getHistoryController().getHistory().canRedo() && !this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.getHistoryController().redoMove();
        });
        button.setFocusable(false);
        return button;
    }

    private JButton createButtonChangeStateAI() {
        JButton button = new JButton(this.gameActionHandler.getLang().getString("display.menu.ai.stop"));
        button.setEnabled(this.gameActionHandler.getPlayerController().getPlayer1().isAi() && this.gameActionHandler.getPlayerController().getPlayer2().isAi());
        button.addActionListener(e -> {
            if (this.gameActionHandler.getAiController().isAiRunning()) {
                this.gameActionHandler.getAiController().stopAi();
                button.setText(this.gameActionHandler.getLang().getString("display.menu.ai.restart"));
            } else {
                this.gameActionHandler.getAiController().startAi();
                button.setText(this.gameActionHandler.getLang().getString("display.menu.ai.stop"));
            }
        });
        button.setFocusable(false);
        return button;
    }

    public void updateButtons() {
        if (this.gameActionHandler.getAiController().isAiRunning()) {
            this.changeStateAIButton.setText(this.gameActionHandler.getLang().getString("display.menu.ai.stop"));
        } else {
            this.changeStateAIButton.setText(this.gameActionHandler.getLang().getString("display.menu.ai.restart"));
        }

        this.saveButton.setText(this.gameActionHandler.getLang().getString("display.menu.save"));
        this.rulesButton.setText(this.gameActionHandler.getLang().getString("display.menu.rules"));
        this.restartButton.setText(this.gameActionHandler.getLang().getString("display.menu.restart"));
        this.abortButton.setText(this.gameActionHandler.getLang().getString("display.menu.abort"));
        this.returnButton.setText(this.gameActionHandler.getLang().getString("display.menu.return"));

        this.cancelButton.setEnabled(this.gameActionHandler.getHistoryController().getHistory().canCancel() && !this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi());
        this.redoButton.setEnabled(this.gameActionHandler.getHistoryController().getHistory().canRedo() && !this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi());
        this.changeStateAIButton.setEnabled(this.gameActionHandler.getPlayerController().getPlayer1().isAi() && this.gameActionHandler.getPlayerController().getPlayer2().isAi());
    }

}