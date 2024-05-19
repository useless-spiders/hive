package View;

import Model.SaveLoad;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class DisplayConfigParty extends JPanel {
    private static final String HUMAN = "human";
    private static final String IA_EASY = "AiRandom";
    private static final String IA_HARD = "Ai1";
    private static final String JOUER = "jouer";
    private static final String LOAD = "charger partie";

    private JComboBox<String> column1;
    private JComboBox<String> column2;

    private PageActionHandler pageActionHandler;
    private GameActionHandler gameActionHandler;

    public DisplayConfigParty(JFrame frame, PageActionHandler controllerPage, GameActionHandler controllerGame) {
        this.pageActionHandler = controllerPage;
        this.gameActionHandler = controllerGame;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.column1 = createDropDownMenu();
        this.column2 = createDropDownMenu();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(column1, gbc);

        gbc.gridx = 1;
        add(column2, gbc);

        JButton playButton = createButton(JOUER);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(playButton, gbc);

        JButton loadButton = createFileSelectionButton();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(loadButton, gbc);

        frame.setContentPane(this);
        frame.pack();
    }

    private JComboBox<String> createDropDownMenu() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem(HUMAN);
        comboBox.addItem(IA_EASY);
        comboBox.addItem(IA_HARD);
        return comboBox;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        switch (text) {
            case JOUER:
                button.addActionListener(e -> {
                    gameActionHandler.startGame();
                    if (column1.getSelectedItem() != HUMAN) {
                        gameActionHandler.setPlayer(1, (String) column1.getSelectedItem());
                    }
                    if (column2.getSelectedItem() != HUMAN) {
                        gameActionHandler.setPlayer(2, (String) column2.getSelectedItem());
                    }
                    gameActionHandler.startAi();
                    pageActionHandler.menuToGame();
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
                if(gameActionHandler.loadGame(selectedFile.getAbsolutePath())){
                    pageActionHandler.menuToGame();
                }
            }
        });
        return button;
    }
}