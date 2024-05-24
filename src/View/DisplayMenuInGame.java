package View;

import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayMenuInGame {
    private static final String DEFAULT = "                       ---";
    private static final String SAVE = "Sauvegarder";
    private static final String RULES = "Règles";
    private static final String NEWGAME = "Recommencer la partie";
    private static final String ABORT = "Abandonner la partie";

    private GameActionHandler gameActionHandler;
    private JPanel panelGame;
    private JPanel optionsPanel;
    private JButton cancelButton;
    private JButton redoButton;
    private JButton changeStateAI;
    private JLabel logLabel;

    private boolean optionVisible;

    private PageActionHandler pageActionHandler;

    public DisplayMenuInGame(JPanel panelGame, GridBagConstraints gbc, GameActionHandler gameActionHandler, PageActionHandler pageActionHandler) {
        this.panelGame = panelGame;
        this.gameActionHandler = gameActionHandler;
        this.pageActionHandler = pageActionHandler;

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
        GridBagConstraints gbcButtonInGame  = new GridBagConstraints();
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
        gbc.anchor = GridBagConstraints.EAST;
        panelGame.add(this.optionsPanel, gbc);

        this.logLabel = new JLabel();
        logLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logLabel.setFont(new Font("Arial", Font.BOLD, 30));
        logLabel.setForeground(Color.WHITE); // Set the text color to red for visibility
        logLabel.setOpaque(true); // Enable opacity to set background color
        logLabel.setBackground(Color.BLACK); // Set background color to blue

        //TODO : mettre les boutons bien au centre

        // Add the message label to the JFrame

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        panelGame.add(logLabel, gbc);

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

    private void showTemporaryMessage(String message, int duration) {
        // Set the message on the label
        JLabel jmessage = this.logLabel;
                jmessage.setText(message);

        // Create a Timer to clear the message after the specified duration
        Timer timer = new Timer(duration, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jmessage.setText("");
            }
        });

        // Ensure the timer only runs once
        timer.setRepeats(false);
        timer.start();
    }

    private JPanel createOptionPanel(){
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
        optionPanel.add(createButton(NEWGAME), optionGbc);
        optionGbc.gridy = 3;
        optionPanel.add(createButton(ABORT), optionGbc);
        return optionPanel;
    }

    private JButton createButton(String name){
        JButton button = new JButton(name);
        button.addActionListener(e -> {
            switch (name) {
                case SAVE:
                    this.gameActionHandler.saveGame();
                    showTemporaryMessage("Sauvegarde en cour !!!", 3000);
                    break;
                case RULES:
                    this.pageActionHandler.gameAndRules();
                    break;
                case NEWGAME:
                    this.pageActionHandler.gameAndRestart();
                    break;
                case ABORT:
                    this.gameActionHandler.stopAi();
                    this.pageActionHandler.gameAndAbort();
                    break;
                case DEFAULT:
                    break;
                default:
                    Log.addMessage("Erreur dans les options du menu du jeu");
            }

        });
        return button;
    }

    private JButton createButtonOption(){
        JButton button = new JButton(DisplayMain.loadIcon("Menu.png"));
        button.addActionListener(e -> {
            if(this.optionVisible){
                this.optionsPanel.setVisible(false);
                this.optionVisible = false;
            }
            else {
                this.optionsPanel.setVisible(true);
                this.optionVisible = true;
            }
        });
        return button;
    }

    private JButton createButtonCancel() {
        JButton button = new JButton(DisplayMain.loadIcon("Undo.png"));
        button.setEnabled(this.gameActionHandler.getHistory().canCancel() && !this.gameActionHandler.getCurrentPlayer().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.cancelMove();
        });
        return button;
    }

    private JButton createButtonRedo() {
        JButton button = new JButton(DisplayMain.loadIcon("Redo.png"));
        button.setEnabled(this.gameActionHandler.getHistory().canRedo() && !this.gameActionHandler.getCurrentPlayer().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.redoMove();
        });
        return button;
    }

    private JButton createButtonChangeStateAI() {
        JButton button = new JButton("Stopper les IA");
        button.setEnabled(this.gameActionHandler.getPlayer1().isAi() && this.gameActionHandler.getPlayer2().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.changeStateAi();
            if (this.gameActionHandler.isAiRunning()) {
                button.setText("Stopper les IA");
            } else {
                button.setText("Relancer les IA");
            }
        });
        return button;
    }

    public void updateButtons() {
        this.cancelButton.setEnabled(this.gameActionHandler.getHistory().canCancel() && !this.gameActionHandler.getCurrentPlayer().isAi());
        this.redoButton.setEnabled(this.gameActionHandler.getHistory().canRedo() && !this.gameActionHandler.getCurrentPlayer().isAi());
        this.changeStateAI.setEnabled(this.gameActionHandler.getPlayer1().isAi() && this.gameActionHandler.getPlayer2().isAi());
    }

}