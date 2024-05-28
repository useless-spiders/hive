package View;

import Global.Configuration;
import Pattern.GameActionHandler;
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
    private static final String IA_EASY = "AiRandom";
    private static final String IA_HARD = "Ai1";
    private static final String IA_HARD2 = "Ai2";
    private static final String IA_HARD3 = "Ai3";
    private static final String IA_HARD4 = "Ai4";
    private static final String JOUER = "Jouer";
    private static final String RETOUR = "Retour";
    private static final String LOAD = "Charger partie";
    private static final String SKIN = "Choix du skin";
    private static final String NAME_TEXT = "Nom du joueur";
    private boolean isSkinSelectorAdded = false;
    private Image background ;
    private JPanel eastPanel;
    private JPanel westPanel;

    public JPanel getWestPanel() {
        return westPanel;
    }

    private JComboBox<String> column1;
    private JComboBox<String> column2;

    private JTextField player1NameField;
    private JTextField player2NameField;

    private GameActionHandler gameActionHandler;

    public DisplayConfigGame(JFrame frame, GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;

        this.background = DisplayMain.loadBackground("Opening_param.png");

        setLayout(new GridLayout(1, 2));

        //Les deux menus déroulants
        this.player1NameField = createTextField();
        this.player2NameField = createTextField();

        this.column1 = createDropDownMenu(this.player1NameField);
        this.column2 = createDropDownMenu(this.player2NameField);

        this.eastPanel = new JPanel(new GridBagLayout());
        this.eastPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0; // Start at the top
        gbc.insets = new Insets(10, 10, 10, 10);
        this.eastPanel.add(this.column1, gbc);

        gbc.gridx = 1;
        this.eastPanel.add(this.column2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1; // Move down one row
        this.eastPanel.add(this.player1NameField, gbc);

        gbc.gridx = 1;
        this.eastPanel.add(this.player2NameField, gbc);

        //Bouton "Jouer"
        JButton playButton = createButton(JOUER);
        gbc.gridx = 0;
        gbc.gridy = 2; // Move down another row
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        this.eastPanel.add(playButton, gbc);

        //Bouton "Charger partie"
        JButton loadButton = createButton(LOAD);
        gbc.gridx = 0;
        gbc.gridy = 3; // Move down another row

        this.eastPanel.add(loadButton, gbc);

        //Bouton "Choix du skin"
        JButton skinButton = createButton(SKIN);
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.eastPanel.add(skinButton, gbc);


        this.westPanel = new JPanel(new BorderLayout());
        this.westPanel.setVisible(false);

        add(this.westPanel);
        add(this.eastPanel);

        frame.setContentPane(this);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width - frame.getWidth(),100);
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
                button = this.SkinSelection(button);
                break;

            case RETOUR:
                button.addActionListener(e -> {
                    this.background = DisplayMain.loadBackground("Opening_param.png");
                    eastPanel.setVisible(true);
                    westPanel.setVisible(false);
                });
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
                this.gameActionHandler.getPlayerController().setAiPlayer(1, (String) this.column1.getSelectedItem());
            } else {
                if(!this.player1NameField.getText().equals(NAME_TEXT)){
                    this.gameActionHandler.getPlayerController().getPlayer1().setName(this.player1NameField.getText());
                }
            }
            if (this.column2.getSelectedItem() != HUMAN) {
                this.gameActionHandler.getPlayerController().setAiPlayer(2, (String) this.column2.getSelectedItem());
            } else {
                if(!this.player2NameField.getText().equals(NAME_TEXT)){
                    this.gameActionHandler.getPlayerController().getPlayer2().setName(this.player2NameField.getText());
                }
            }
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateAllLabels();
            this.gameActionHandler.getAiController().startAi();
            this.gameActionHandler.getPageController().menuToGame();
        });
        return button;
    }

    private JButton createFileSelectionButton() {
        JButton button = new JButton(LOAD);
        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            // Définir le répertoire de départ
            fileChooser.setCurrentDirectory(new File(Configuration.SAVE_PATH));

            // Créer un filtre pour les fichiers .save
            FileNameExtensionFilter filter = new FileNameExtensionFilter("HIVE SAVE FILES", Configuration.SAVE_EXTENSION);
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if(this.gameActionHandler.getSaveLoadController().loadGame(selectedFile.getAbsolutePath())){
                    this.gameActionHandler.getPageController().menuToGame();
                }
            }
        });
        return button;
    }

    private JButton SkinSelection(JButton button) {
        button.addActionListener(e -> {
            if (!isSkinSelectorAdded) {
                changeBackgroundAndAddButtons();
                isSkinSelectorAdded = true;
            }
            this.background = DisplayMain.loadBackground("Opening_skin.png");
            repaint();
            this.eastPanel.setVisible(false);
            this.westPanel.setVisible(true);
            this.westPanel.setBackground(new Color(0, 0, 0, 0));
            //TODO: rendre invisible et defocus les boutons de la page de configuration


        });
        return button;
    }

    private void changeBackgroundAndAddButtons() {
        // Changer le fond d'écran
        String[] skins = {"Skin par défaut", "Skin noir et blanc", "Skin Among Us","Developper"};
        JComboBox<String> skinSelector = new JComboBox<>(skins);
        String[] insects = {"Ant_white", "Beetle_white", "Bee_white", "Grasshopper_white", "Spider_white"};
        for (String insect : insects) {
            JLabel insectLabel = new JLabel();
            insectLabel.setName(insect);
            this.westPanel.add(insectLabel);
        }

        // Ajouter le JComboBox et le JLabel au JPanel
        this.westPanel.add(skinSelector);
        skinSelector.addActionListener(e -> {
            String selectedSkin = (String) skinSelector.getSelectedItem();
            switch (selectedSkin) {
                case "Skin par défaut":
                    this.gameActionHandler.getPageController().getDisplayMain().setHexagonSkin("Default/");
                    break;
                case "Skin noir et blanc":
                    this.gameActionHandler.getPageController().getDisplayMain().setHexagonSkin("Black_and_white/");
                    break;
                case "Skin Among Us":
                    this.gameActionHandler.getPageController().getDisplayMain().setHexagonSkin("Sus_skin/");
                    break;
                case "Developper":
                    this.gameActionHandler.getPageController().getDisplayMain().setHexagonSkin("Skin_very_hard/");
                    break;
                default:
                    Log.addMessage("Skin inconnu");
            }
            for (Component component : this.westPanel.getComponents()) {
                if (component instanceof JLabel) {
                    JLabel insectLabel = (JLabel) component;
                    ImageIcon insectImage = new ImageIcon("res/Images/Skins/" + selectedSkin + "/" + insectLabel.getName() + ".png");
                    insectLabel.setIcon(insectImage);
                }
            }
        });
        JButton jeux = createButton(RETOUR);
        this.westPanel.add(jeux,BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image scaledBackground = background.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(scaledBackground, 0, 0, this);
    }

}