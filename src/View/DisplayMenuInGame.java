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
    private JButton cancelButton;
    private JButton redoButton;
    private JButton changeStateAI;
    private JLabel logLabel;

    private PageActionHandler pageActionHandler;

    public DisplayMenuInGame(JPanel panelGame, GridBagConstraints gbc, GameActionHandler gameActionHandler, PageActionHandler pageActionHandler) {
        this.panelGame = panelGame;
        this.gameActionHandler = gameActionHandler;
        this.pageActionHandler = pageActionHandler;

        // Création du JPanel pour contenir le menu et les boutons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        // Création des boutons annuler et refaire
        this.cancelButton = createButtonCancel();
        this.redoButton = createButtonRedo();
        this.changeStateAI = createButtonChangeStateAI();

        // Création du menu
        JComboBox<String> menu = createMenu();
        menu.setFocusable(false);

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



        // Ajout du bouton refaire au JPanel avec les contraintes pour le positionner à droite du menu
        GridBagConstraints menuConstraints = new GridBagConstraints();
        menuConstraints.gridx = 3; // à droite du bouton annuler
        menuConstraints.gridy = 0;
        menuConstraints.anchor = GridBagConstraints.NORTHEAST;
        menuConstraints.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(menu, menuConstraints);


        this.logLabel = new JLabel();
        logLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logLabel.setFont(new Font("Arial", Font.BOLD, 30));
        logLabel.setForeground(Color.WHITE); // Set the text color to red for visibility
        logLabel.setOpaque(true); // Enable opacity to set background color
        logLabel.setBackground(Color.BLACK); // Set background color to blue

        // Add the message label to the JFrame
        gbc.gridx = 1;
        gbc.gridy = 2;
        //gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.weightx = 1.0;
        //gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        panelGame.add(logLabel, gbc);

        // Ajout du JPanel contenant le menu et les boutons à panelGame avec les contraintes pour le positionner dans le coin nord-est
        gbc.gridy = 3; // Row (starts from 0)
        gbc.insets = new Insets(10, 10, 0, 10); // Add some padding
        panelGame.add(buttonPanel, gbc);
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

    private JComboBox<String> createMenu() {
        String[] options = {DEFAULT, SAVE, RULES, NEWGAME, ABORT};
        JComboBox<String> menu = new JComboBox<>(options);
        menu.addActionListener(e -> {
            String selectedItem = (String) menu.getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
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