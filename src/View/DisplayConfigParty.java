package View;

import Pattern.GameActionHandler;
import Pattern.PageActionHandler;

import javax.swing.*;
import java.awt.*;

public class DisplayConfigParty extends JPanel {
    private static final String HUMAN = "human";
    private static final String IA_EASY = "ia facile";
    private static final String IA_HARD = "ia difficile";
    private static final String JOUER = "jouer";

    private JComboBox<String> column1;
    private JComboBox<String> column2;

    private PageActionHandler pageActionHandler;
    private GameActionHandler gameActionHandler;

    public DisplayConfigParty(JFrame frame, PageActionHandler controllerPage, GameActionHandler controllerGame){
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
                    gameActionHandler.setPlayer(1, (String) column1.getSelectedItem());
                    gameActionHandler.setPlayer(2, (String) column2.getSelectedItem());
                    pageActionHandler.menuToGame();
                });
                break;
        }
        return button;
    }
}