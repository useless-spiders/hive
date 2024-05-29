package View;

import Pattern.GameActionHandler;
import Structure.Log;
import Structure.RessourceLoader;

import javax.swing.*;
import java.awt.*;

public class DisplayMenuInGame extends JFrame {
    private static String SAVE;
    private static String RULES;
    private static String RESTART;
    private static String ABORT;
    private static String RETURN;

    private GameActionHandler gameActionHandler;
    private RessourceLoader ressourceLoader;
    private JPanel panelGame;
    private JPanel optionsPanel;
    private JButton cancelButton;
    private JButton redoButton;
    private JButton changeStateAI;


    private boolean optionVisible;

    public DisplayMenuInGame(JPanel panelGame, GridBagConstraints gbc, GameActionHandler gameActionHandler) {
        this.panelGame = panelGame;
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);

        SAVE = this.gameActionHandler.getMessages().getString("display.menu.save");
        RULES = this.gameActionHandler.getMessages().getString("display.menu.rules");
        RESTART = this.gameActionHandler.getMessages().getString("display.menu.restart");
        ABORT = this.gameActionHandler.getMessages().getString("display.menu.abort");
        RETURN = this.gameActionHandler.getMessages().getString("display.menu.return");

        this.optionVisible = false;
        // Création du JPanel pour contenir le menu et les boutons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        this.optionsPanel = createOptionPanel();

        // Création des boutons annuler et refaire
        this.cancelButton = createButtonCancel();
        this.redoButton = createButtonRedo();

        this.changeStateAI = createButtonChangeStateAI();
        JButton optionButton = createButtonOption();

        // Ajout du menu au JPanel avec les contraintes pour le positionner dans le coin nord-est
        GridBagConstraints gbcButtonInGame = new GridBagConstraints();
        gbcButtonInGame.gridx = 0; // à droite du menu
        gbcButtonInGame.gridy = 0;
        gbcButtonInGame.anchor = GridBagConstraints.NORTHEAST;
        gbcButtonInGame.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(this.changeStateAI, gbcButtonInGame);

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
        optionPanel.add(createButton(SAVE), optionGbc);
        optionGbc.gridy = 1;
        optionPanel.add(createButton(RULES), optionGbc);
        optionGbc.gridy = 2;
        optionPanel.add(createButton(RESTART), optionGbc);
        optionGbc.gridy = 3;
        optionPanel.add(createButton(ABORT), optionGbc);
        optionGbc.gridy = 4;
        optionPanel.add(createButton(RETURN), optionGbc);

        // Définir une taille plus grande pour optionsPanel
        optionPanel.setPreferredSize(new Dimension(300, 300));

        return optionPanel;
    }

    private JButton createButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(e -> {
            if (name.equals(SAVE)) {
                this.gameActionHandler.getSaveLoadController().saveGame();
                DisplayText.addTextPopUp(this.gameActionHandler.getMessages().getString("display.menu.save.game"), this.gameActionHandler.getDisplayGame().getFrameGame());
            } else if (name.equals(RULES)) {
                this.gameActionHandler.getPageController().gameAndRules();
            } else if (name.equals(RESTART)) {
                this.gameActionHandler.getPageController().gameAndRestart();
                this.optionsPanel.setVisible(false);
                this.optionVisible = false;
            } else if (name.equals(ABORT)) {
                this.gameActionHandler.getAiController().stopAi();
                this.gameActionHandler.getPageController().gameAndAbort();
                this.optionsPanel.setVisible(false);
                this.optionVisible = false;
            } else if (name.equals(RETURN)) {
                this.optionsPanel.setVisible(false);
                this.optionVisible = false;
            } else {
                Log.addMessage(this.gameActionHandler.getMessages().getString("display.menu.error"));
            }
        });
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
        });
        return button;
    }

    private JButton createButtonCancel() {
        JButton button = new JButton(this.ressourceLoader.loadIcon("Undo.png"));
        button.setEnabled(this.gameActionHandler.getHistoryController().getHistory().canCancel() && !this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.getHistoryController().cancelMove();
        });
        return button;
    }

    private JButton createButtonRedo() {
        JButton button = new JButton(this.ressourceLoader.loadIcon("Redo.png"));
        button.setEnabled(this.gameActionHandler.getHistoryController().getHistory().canRedo() && !this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.getHistoryController().redoMove();
        });
        return button;
    }

    private JButton createButtonChangeStateAI() {
        JButton button = new JButton(this.gameActionHandler.getMessages().getString("display.menu.ai.stop"));
        button.setEnabled(this.gameActionHandler.getPlayerController().getPlayer1().isAi() && this.gameActionHandler.getPlayerController().getPlayer2().isAi());
        button.addActionListener(e -> {
            if (this.gameActionHandler.getAiController().isAiRunning()) {
                this.gameActionHandler.getAiController().stopAi();
                button.setText(this.gameActionHandler.getMessages().getString("display.menu.ai.restart"));
            } else {
                this.gameActionHandler.getAiController().startAi();
                button.setText(this.gameActionHandler.getMessages().getString("display.menu.ai.stop"));
            }
        });
        return button;
    }

    public void updateButtons() {
        this.cancelButton.setEnabled(this.gameActionHandler.getHistoryController().getHistory().canCancel() && !this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi());
        this.redoButton.setEnabled(this.gameActionHandler.getHistoryController().getHistory().canRedo() && !this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi());
        this.changeStateAI.setEnabled(this.gameActionHandler.getPlayerController().getPlayer1().isAi() && this.gameActionHandler.getPlayerController().getPlayer2().isAi());
    }

}