package View;

import Model.SaveLoad;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.io.File;

public class DisplayConfigGame extends JPanel {
    private static final String HUMAN = "Humain";
    private static final String IA_EASY = "IARandom";
    private static final String IA_HARD = "Ai1";
    private static final String IA_HARD2 = "Ai2";
    private static final String IA_HARD3 = "Ai3";
    private static final String IA_HARD4 = "Ai4";
    private static final String JOUER = "Jouer";
    private static final String LOAD = "Charger partie";
    private static final String SKIN = "Choix du skin";
    private static final String NAME_TEXT = "Nom du joueur";


    private JComboBox<String> column1;
    private JComboBox<String> column2;

    private JTextField player1NameField;
    private JTextField player2NameField;

    private PageActionHandler pageActionHandler;
    private GameActionHandler gameActionHandler;

    public DisplayConfigGame(JFrame frame, PageActionHandler pageActionHandler, GameActionHandler gameActionHandler) {
        this.pageActionHandler = pageActionHandler;
        this.gameActionHandler = gameActionHandler;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Les deux menus déroulants
        this.player1NameField = createTextField();
        this.player2NameField = createTextField();

        this.column1 = createDropDownMenu(this.player1NameField);
        this.column2 = createDropDownMenu(this.player2NameField);

        gbc.gridx = 0;
        gbc.gridy = 0; // Start at the top
        gbc.insets = new Insets(10, 10, 10, 10);
        add(this.column1, gbc);

        gbc.gridx = 1;
        add(this.column2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1; // Move down one row
        add(this.player1NameField, gbc);

        gbc.gridx = 1;
        add(this.player2NameField, gbc);

        //Bouton "Jouer"
        JButton playButton = createButton(JOUER);
        gbc.gridx = 0;
        gbc.gridy = 2; // Move down another row
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(playButton, gbc);

        //Bouton "Charger partie"
        JButton loadButton = createButton(LOAD);
        gbc.gridx = 0;
        gbc.gridy = 3; // Move down another row
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(loadButton, gbc);

        //Bouton "Choix du skin"
        JButton skinButton = createButton(SKIN);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(skinButton, gbc);

        frame.setContentPane(this);
        frame.pack();
    }

    private JComboBox<String> createDropDownMenu(JTextField textField) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem(HUMAN);
        comboBox.addItem(IA_EASY);
        comboBox.addItem(IA_HARD);
        comboBox.addItem(IA_HARD2);
        comboBox.addItem(IA_HARD3);
        comboBox.addItem(IA_HARD4);

        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                textField.setEnabled(comboBox.getSelectedItem() == HUMAN);
            }
        });

        return comboBox;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setText(NAME_TEXT);
        textField.setPreferredSize(new Dimension(200, 24)); // Set your preferred size

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(NAME_TEXT)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(NAME_TEXT);
                }
            }
        });

        return textField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        switch (text) {
            case JOUER:
                button = this.createPlayersSelectionButton(button);
                break;

            case LOAD:
                button = this.createFileSelectionButton();
                break;

            case SKIN:
                button = this.createSkinSelectionButton(button);
                break;
        }
        return button;
    }

    private JButton createPlayersSelectionButton(JButton button) {
        button.addActionListener(e -> {
            if(this.gameActionHandler.getIsFirstStart()){
                this.gameActionHandler.setIsFirstStart(false);
            } else {
                this.gameActionHandler.resetGame();
            }
            if (this.column1.getSelectedItem() != HUMAN) {
                this.gameActionHandler.getPlayerController().setPlayer(1, (String) this.column1.getSelectedItem());
            } else {
                if(!this.player1NameField.getText().equals(NAME_TEXT)){
                    this.gameActionHandler.getPlayerController().getPlayer1().setName(this.player1NameField.getText());
                }
            }
            if (this.column2.getSelectedItem() != HUMAN) {
                this.gameActionHandler.getPlayerController().setPlayer(2, (String) this.column2.getSelectedItem());
            } else {
                if(!this.player2NameField.getText().equals(NAME_TEXT)){
                    this.gameActionHandler.getPlayerController().getPlayer2().setName(this.player2NameField.getText());
                }
            }
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateAllLabels();
            this.gameActionHandler.getAiController().startAi();
            this.pageActionHandler.menuToGame();
        });
        return button;
    }

    private JButton createFileSelectionButton() {
        JButton button = new JButton(LOAD);
        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            // Définir le répertoire de départ
            fileChooser.setCurrentDirectory(new File(SaveLoad.SAVE_PATH));

            // Créer un filtre pour les fichiers .save
            FileNameExtensionFilter filter = new FileNameExtensionFilter("HIVE SAVE FILES", SaveLoad.SAVE_EXTENSION);
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if(this.gameActionHandler.getSaveLoadController().loadGame(selectedFile.getAbsolutePath())){
                    this.pageActionHandler.menuToGame();
                }
            }
        });
        return button;
    }

    private JButton createSkinSelectionButton(JButton button) {
        button.addActionListener(e -> {
            // Afficher le popup de sélection des skins
            String[] skinOptions = {"Skin par défaut", "Skin noir et blanc", "Skin Among Us"};
            String selectedSkin = (String) JOptionPane.showInputDialog(
                    null,
                    "Choisissez un skin pour les insectes :",
                    "Sélection du skin",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    skinOptions,
                    skinOptions[0]);

            // Mettre à jour les skins des hexagones
            if (selectedSkin != null) {
                switch (selectedSkin) {
                    case "Skin par défaut":
                        this.pageActionHandler.getDisplayMain().setHexagonSkin("Default/");
                        break;
                    case "Skin noir et blanc":
                        this.pageActionHandler.getDisplayMain().setHexagonSkin("Black_and_white/");
                        break;
                    case "Skin Among Us":
                        this.pageActionHandler.getDisplayMain().setHexagonSkin("Sus_skin/");
                        break;
                    default:
                        Log.addMessage("Skin inconnu");
                }
            }
        });
        return button;
    }
}