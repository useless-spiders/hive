package View;

import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayMenuInGame extends JFrame{
    private static final String SAVE = "Sauvegarder";
    private static final String RULES = "Règles";
    private static final String RESTART = "Recommencer la partie";
    private static final String ABORT = "Abandonner la partie";
    private static final String RETURN = "Retour au jeu";

    private GameActionHandler gameActionHandler;
    private JPanel panelGame;
    private JPanel optionsPanel;
    private JButton cancelButton;
    private JButton redoButton;
    private JButton changeStateAI;
    private static JLabel messageLabel;

    private boolean optionVisible;

    public DisplayMenuInGame(JPanel panelGame, GridBagConstraints gbc, GameActionHandler gameActionHandler) {
        this.panelGame = panelGame;
        this.gameActionHandler = gameActionHandler;

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

        this.messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        messageLabel.setForeground(Color.WHITE); // Set the text color to red for visibility
        messageLabel.setOpaque(true); // Enable opacity to set background color
        messageLabel.setBackground(Color.BLACK); // Set background color to blue
        gbc.gridx = 1; // Placer le JLabel au centre horizontalement
        gbc.gridy = 1; // Placer le JLabel sous le JPanel des options
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelGame.add(messageLabel, gbc);

        //TODO : mettre les boutons bien au centre

        // Add the message label to the JFrame

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        panelGame.add(messageLabel, gbc);

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

    private void showTemporaryMessage(String message) {
        this.messageLabel.setText(message);
        this.messageLabel.setVisible(true);

        // Cacher le message après 3 secondes
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayMenuInGame.messageLabel.setText("");
                DisplayMenuInGame.messageLabel.setVisible(false);
            }
        });
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
        optionPanel.add(createButton(RESTART), optionGbc);
        optionGbc.gridy = 3;
        optionPanel.add(createButton(ABORT), optionGbc);
        optionGbc.gridy = 4;
        optionPanel.add(createButton(RETURN), optionGbc);

        // Définir une taille plus grande pour optionsPanel
        optionPanel.setPreferredSize(new Dimension(300, 300));

        return optionPanel;
    }

    private JButton createButton(String name){
        JButton button = new JButton(name);
        button.addActionListener(e -> {
            switch (name) {
                case SAVE:
                    this.gameActionHandler.getSaveLoadController().saveGame();
                    showTemporaryMessage("Sauvegarde en cours");
                    break;
                case RULES:
                    this.gameActionHandler.getPageActionHandler().gameAndRules();
                    break;
                case RESTART:
                    this.gameActionHandler.getPageActionHandler().gameAndRestart();
                    this.optionsPanel.setVisible(false);
                    this.optionVisible = false;
                    break;
                case ABORT:
                    this.gameActionHandler.getAiController().stopAi();
                    this.gameActionHandler.getPageActionHandler().gameAndAbort();
                    this.optionsPanel.setVisible(false);
                    this.optionVisible = false;
                    break;
                case RETURN:
                    this.optionsPanel.setVisible(false);
                    this.optionVisible = false;
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
        //Augmenter la taille du bouton des options
        button.setPreferredSize(new Dimension(150, 50));
        return button;
    }

    private JButton createButtonCancel() {
        JButton button = new JButton(DisplayMain.loadIcon("Undo.png"));
        button.setEnabled(this.gameActionHandler.getHistoryController().getHistory().canCancel() && !this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.getHistoryController().cancelMove();
        });
        return button;
    }

    private JButton createButtonRedo() {
        JButton button = new JButton(DisplayMain.loadIcon("Redo.png"));
        button.setEnabled(this.gameActionHandler.getHistoryController().getHistory().canRedo() && !this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.getHistoryController().redoMove();
        });
        return button;
    }

    private JButton createButtonChangeStateAI() {
        JButton button = new JButton("Stopper les IA");
        button.setEnabled(this.gameActionHandler.getPlayerController().getPlayer1().isAi() && this.gameActionHandler.getPlayerController().getPlayer2().isAi());
        button.addActionListener(e -> {
            this.gameActionHandler.getAiController().changeStateAi();
            if (this.gameActionHandler.getAiController().isAiRunning()) {
                button.setText("Stopper les IA");
            } else {
                button.setText("Relancer les IA");
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