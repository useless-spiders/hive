package View;

import Model.SaveLoad;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.io.File;

public class DisplayConfigParty extends JPanel {
    private static final String HUMAN = "human";
    private static final String IA_EASY = "AiRandom";
    private static final String IA_HARD = "Ai1";
    private static final String IA_HARD2 = "Ai2";
    private static final String JOUER = "jouer";
    private static final String LOAD = "charger partie";

    private JComboBox<String> column1;
    private JComboBox<String> column2;

    private JTextField player1NameField;
    private JTextField player2NameField;

    private PageActionHandler pageActionHandler;
    private GameActionHandler gameActionHandler;

    public DisplayConfigParty(JFrame frame, PageActionHandler controllerPage, GameActionHandler controllerGame) {
        this.pageActionHandler = controllerPage;
        this.gameActionHandler = controllerGame;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

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

        JButton playButton = createButton(JOUER);
        gbc.gridx = 0;
        gbc.gridy = 2; // Move down another row
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(playButton, gbc);

        JButton loadButton = createFileSelectionButton();
        gbc.gridx = 0;
        gbc.gridy = 3; // Move down another row
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(loadButton, gbc);

        frame.setContentPane(this);
        frame.pack();
    }

    private JComboBox<String> createDropDownMenu(JTextField textField) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem(HUMAN);
        comboBox.addItem(IA_EASY);
        comboBox.addItem(IA_HARD);
        comboBox.addItem(IA_HARD2);

        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                textField.setEnabled(comboBox.getSelectedItem() == HUMAN);
            }
        });

        return comboBox;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        String defaultText = "Nom du joueur";
        textField.setText(defaultText);
        textField.setPreferredSize(new Dimension(200, 24)); // Set your preferred size

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(defaultText)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(defaultText);
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
                button.addActionListener(e -> {
                    this.gameActionHandler.startGame();
                    if (this.column1.getSelectedItem() != HUMAN) {
                        this.gameActionHandler.setPlayer(1, (String) this.column1.getSelectedItem());
                    } else {
                        this.gameActionHandler.getPlayer1().setName(this.player1NameField.getText());
                    }
                    if (this.column2.getSelectedItem() != HUMAN) {
                        this.gameActionHandler.setPlayer(2, (String) this.column2.getSelectedItem());
                    } else {
                        this.gameActionHandler.getPlayer2().setName(this.player2NameField.getText());
                    }
                    this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateAllLabels();
                    this.gameActionHandler.startAi();
                    this.pageActionHandler.menuToGame();
                });
                break;
        }
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
                if(this.gameActionHandler.loadGame(selectedFile.getAbsolutePath())){
                    this.pageActionHandler.menuToGame();
                }
            }
        });
        return button;
    }
}