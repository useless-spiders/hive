package View;

import Global.Configuration;
import Model.Insect.*;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.Log;
import Structure.RessourceLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Locale;

public class DisplayConfigGame extends JPanel {
    private String HUMAN = "Humain";
    private String IA_EASY = "AiRandom";
    private String IA_HARD = "Ai1";
    private String IA_HARD2 = "Ai2";
    private String IA_HARD3 = "Ai3";
    private String PLAY;
    private String RETURN;
    private String LOAD;
    private String SKIN;
    private String NAME_TEXT;
    private boolean isSkinSelectorAdded = false;
    private Image background;
    private JPanel eastPanel;
    private JPanel westPanel;
    private JPanel exampleSkinPanelWhite;
    private JPanel exampleSkinPanelBlack;
    private GridBagConstraints westGbc;
    private JComboBox<String> skinSelector;

    private JComboBox<String> column1;
    private JComboBox<String> column2;

    private JTextField player1NameField;
    private JTextField player2NameField;

    private GameActionHandler gameActionHandler;
    private RessourceLoader ressourceLoader;

    private JButton playButton;
    private JButton loadButton;
    private JButton skinButton;

    public DisplayConfigGame(JFrame frame, GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);

        this.background = this.ressourceLoader.loadBackground("Opening_param.png");

        this.PLAY = this.gameActionHandler.getLang().getString("display.config.play");
        this.RETURN = this.gameActionHandler.getLang().getString("display.config.back");
        this.LOAD = this.gameActionHandler.getLang().getString("display.config.load");
        this.SKIN = this.gameActionHandler.getLang().getString("display.config.skin");
        this.NAME_TEXT = this.gameActionHandler.getLang().getString("display.config.name");

        setLayout(new GridLayout(1, 2));
        this.westPanel = new JPanel(new GridBagLayout());
        this.westPanel.setVisible(false);

        initEastPanel();

        add(this.westPanel);
        add(this.eastPanel);

        frame.setContentPane(this);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width - frame.getWidth(), 100);
    }

    /**
     * initialisation du panel contenant les elements permettant la configuration d'une partie
     */
    private void initEastPanel(){
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
        this.playButton = createButton(this.PLAY);
        gbc.gridx = 0;
        gbc.gridy = 2; // Move down another row
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        this.eastPanel.add(this.playButton, gbc);

        //Bouton "Charger partie"
        this.loadButton = createButton(this.LOAD);
        gbc.gridx = 0;
        gbc.gridy = 3; // Move down another row
        this.eastPanel.add(this.loadButton, gbc);

        //Bouton "Choix du skin"
        this.skinButton = createButton(this.SKIN);
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.eastPanel.add(this.skinButton, gbc);

        //Bouton "Choix de la langue"
        JComboBox<String> languageSelector = createLanguageSelector();
        gbc.gridx = 0;
        gbc.gridy = 5; // Move down another row
        this.eastPanel.add(languageSelector, gbc);
    }

    /**
     * renvoie une JComboBox<String> contenant les joueurs possibles (humain ou ia)
     * @param textField JTextField
     * @return comboBox
     */
    private JComboBox<String> createDropDownMenu(JTextField textField) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem(HUMAN);
        comboBox.addItem(IA_EASY);
        comboBox.addItem(IA_HARD);
        comboBox.addItem(IA_HARD2);
        comboBox.addItem(IA_HARD3);

        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                textField.setEnabled(comboBox.getSelectedItem() == HUMAN);
            }
        });

        return comboBox;
    }

    /**
     * Renvoie le JTextField de récuperation du nom du joueurs
     * @return textField
     */
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
    /**
     * renvoie une JComboBox<String> contenant les langues possibles ("English" ou "français")
     * @return languageSelector
     */
    private JComboBox<String> createLanguageSelector() {
        String[] languages = {"English", "Français"};
        JComboBox<String> languageSelector = new JComboBox<>(languages);
        Locale currentLocale = this.gameActionHandler.getCurrentLocale();
        String currentLanguage;
        switch (currentLocale.getLanguage()) {
            case "en":
                currentLanguage = "English";
                break;
            case "fr":
                currentLanguage = "Français";
                break;
            default:
                currentLanguage = "English"; // Default to English if the current locale is not supported
        }
        languageSelector.setSelectedItem(currentLanguage);

        languageSelector.addActionListener(e -> {
            String selectedLanguage = (String) languageSelector.getSelectedItem();
            Locale locale;
            switch (selectedLanguage) {
                case "English":
                    locale = Locale.ENGLISH;
                    break;
                case "Français":
                    locale = Locale.FRENCH;
                    break;
                default:
                    locale = Locale.getDefault();
            }
            this.gameActionHandler.setLang(locale);
            this.gameActionHandler.getDisplayGame().getDisplayMenuInGame().updateButtons();
            this.updateButtons();
        });

        return languageSelector;
    }

    /**
     * renvoie un bouton (PLAY, LOAD, SKIN, RETURN)
     * @param text String
     * @return button
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (text.equals(this.PLAY)) {
            button = this.createPlayersSelectionButton(button);
        } else if (text.equals(this.LOAD)) {
            button = this.createFileSelectionButton(button);
        } else if (text.equals(this.SKIN)) {
            button = this.createbuttonSkinSelection(button);
        } else if (text.equals(this.RETURN)) {
            button.addActionListener(e -> {
                this.background = this.ressourceLoader.loadBackground("Opening_param.png");
                eastPanel.setVisible(true);
                westPanel.setVisible(false);
            });
        }
        return button;
    }

    /**
    * Renvoie un le bouton PLAY avec son ActionListener
     * @param button JButton
     * @return button
     */
    private JButton createPlayersSelectionButton(JButton button) {
        button.addActionListener(e -> {
            if (this.gameActionHandler.getIsFirstStart()) {
                this.gameActionHandler.setIsFirstStart(false);
            } else {
                this.gameActionHandler.resetGame();
            }
            if (this.column1.getSelectedItem() != HUMAN) {
                this.gameActionHandler.getPlayerController().setAiPlayer(1, (String) this.column1.getSelectedItem());
            } else {
                if (!this.player1NameField.getText().equals(NAME_TEXT)) {
                    this.gameActionHandler.getPlayerController().getPlayer1().setName(this.player1NameField.getText());
                }
            }
            if (this.column2.getSelectedItem() != HUMAN) {
                this.gameActionHandler.getPlayerController().setAiPlayer(2, (String) this.column2.getSelectedItem());
            } else {
                if (!this.player2NameField.getText().equals(NAME_TEXT)) {
                    this.gameActionHandler.getPlayerController().getPlayer2().setName(this.player2NameField.getText());
                }
            }
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateAllLabels();
            this.gameActionHandler.getAiController().startAi();
            this.gameActionHandler.getPageController().menuToGame();
        });
        return button;
    }

    /**
     * Renvoie un le bouton LOAD avec son ActionListener
     * @param button JButton
     * @return button
     */
    private JButton createFileSelectionButton(JButton button) {
        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            // Définir le répertoire de départ
            File directory = new File(Configuration.SAVE_PATH);
            if (directory.exists()) {
                fileChooser.setCurrentDirectory(directory);

                // Créer un filtre pour les fichiers .save
                FileNameExtensionFilter filter = new FileNameExtensionFilter("HIVE SAVE FILES", Configuration.SAVE_EXTENSION);
                fileChooser.setFileFilter(filter);

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (this.gameActionHandler.getSaveLoadController().loadGame(selectedFile.getAbsolutePath())) {
                        this.gameActionHandler.getPageController().menuToGame();
                    }
                }
            } else {
                Log.addMessage(this.gameActionHandler.getLang().getString("save.not.found"));
            }
        });
        return button;
    }
    /**
     * Renvoie un le bouton SKIN avec son ActionListener
     * @param button JButton
     * @return button
     */
    private JButton createbuttonSkinSelection(JButton button) {
        button.addActionListener(e -> {
            if (!isSkinSelectorAdded) {
                changeBackgroundAndAddButtons();
                isSkinSelectorAdded = true;
            }
            this.background = this.ressourceLoader.loadBackground("Opening_skin.png");
            repaint();
            this.eastPanel.setVisible(false);
            this.westPanel.setVisible(true);
            this.westPanel.setBackground(new Color(0, 0, 0, 0));
            //TODO: rendre invisible et defocus les boutons de la page de configuration


        });
        return button;
    }

    /**
     * renvoie une JcomboBox<String> contenant les differents skin possible avec les action associé à la selection d'un skin
     * @return skinSelector
     */
    private JComboBox<String> createComboBoxSkinSelector() {
        String[] skins = {
                this.gameActionHandler.getLang().getString("display.config.skin.default"),
                this.gameActionHandler.getLang().getString("display.config.skin.black_white"),
                this.gameActionHandler.getLang().getString("display.config.skin.among_us"),
                this.gameActionHandler.getLang().getString("display.config.skin.hard")
        };
        JComboBox<String> skinSelector = new JComboBox<>(skins);
        skinSelector.addActionListener(e -> {
            String selectedSkin = (String) skinSelector.getSelectedItem();
            if (selectedSkin.equals(skins[0])) {
                Configuration.DEFAULT_SKINS = "Default/";
            } else if (selectedSkin.equals(skins[1])) {
                Configuration.DEFAULT_SKINS = "Black_and_white/";
            } else if (selectedSkin.equals(skins[2])) {
                Configuration.DEFAULT_SKINS = "Sus_skin/";
            } else if (selectedSkin.equals(skins[3])) {
                Configuration.DEFAULT_SKINS = "Skin_very_hard/";
            } else {
                Log.addMessage("Skin inconnu");
            }
            this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateButtons();
            updateExampleSkin();
        });
        return skinSelector;
    }

    /**
     * Transition de la configuration de la partie au choix du skin
     */
    private void changeBackgroundAndAddButtons() {
        this.westGbc = new GridBagConstraints();
        // Changer le fond d'écran
        this.skinSelector = createComboBoxSkinSelector();
        // Ajouter le JComboBox et le JLabel au JPanel

        westGbc.insets = new Insets(10, 10, 10, 10); // Add padding around the container
        westGbc.gridx = 0;
        westGbc.gridy = 0;
        westGbc.anchor = GridBagConstraints.CENTER;
        this.westPanel.add(this.skinSelector, westGbc);
        this.exampleSkinPanelWhite = createExampleSkinPanel(Configuration.PLAYER_WHITE);
        this.exampleSkinPanelBlack = createExampleSkinPanel(Configuration.PLAYER_BLACK);
        westGbc.gridx = 1;
        this.westPanel.add(exampleSkinPanelWhite, westGbc);
        westGbc.gridy = 1;
        this.westPanel.add(exampleSkinPanelBlack, westGbc);
        westGbc.gridwidth = 1;
        westGbc.gridy = 2;
        JButton returnButton = createButton(RETURN);
        this.westPanel.add(returnButton, westGbc);
    }

    /**
     * Renvoie un Jpanel contenant un exemple d'icon pour chaque insecte dans la couleur "color"
     * @param color int
     * @return panel
     */
    private JPanel createExampleSkinPanel(int color) {
        Player player;
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);
        if (this.gameActionHandler.getPlayerController().getPlayer1().getColor() == color) {
            player = this.gameActionHandler.getPlayerController().getPlayer1();
        } else {
            player = this.gameActionHandler.getPlayerController().getPlayer2();
        }
        for (Class<? extends Insect> insect : player.getTypes()) {
            panel.add(createExampleSkin(insect, player));
        }
        return panel;
    }

    /**
     * renvoie un JLabel contenant l'icon de l'insecte en parametre avec la couleur du player
     * @param insectClass Class<? extends Insect>
     * @param player Player
     * @return imgExampleSkin
     */
    private JLabel createExampleSkin(Class<? extends Insect> insectClass, Player player) {
        ImageIcon imageIcon = this.ressourceLoader.loadIconInsects(this.ressourceLoader.getImageInsectName(insectClass, player, this.gameActionHandler.getPlayerController().getCurrentPlayer()));
        JLabel imgExampleSkin = new JLabel(imageIcon);
        return imgExampleSkin;
    }

    /**
     * actualise le skin des insectes dans les exampleSkinPanel
     */
    private void updateExampleSkin() {
        this.westPanel.remove(exampleSkinPanelWhite);
        this.exampleSkinPanelWhite = createExampleSkinPanel(Configuration.PLAYER_WHITE);
        this.westPanel.remove(exampleSkinPanelBlack);
        this.exampleSkinPanelBlack = createExampleSkinPanel(Configuration.PLAYER_BLACK);
        this.westGbc.gridx = 1;
        this.westGbc.gridy = 0;
        this.westPanel.add(exampleSkinPanelWhite, westGbc);
        this.westGbc.gridy = 1;
        this.westPanel.add(exampleSkinPanelBlack, westGbc);
        this.revalidate();
        this.repaint();
    }

    /**
     * actualise le texte des boutons en fonction de la langue
     */
    public void updateButtons() {
        this.playButton.setText(this.gameActionHandler.getLang().getString("display.config.play"));
        this.loadButton.setText(this.gameActionHandler.getLang().getString("display.config.load"));
        this.skinButton.setText(this.gameActionHandler.getLang().getString("display.config.skin"));
        this.player1NameField.setText(this.gameActionHandler.getLang().getString("display.config.name"));
        this.player2NameField.setText(this.gameActionHandler.getLang().getString("display.config.name"));
    }


    /**
     * affichage du background
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image scaledBackground = background.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(scaledBackground, 0, 0, this);
    }

}