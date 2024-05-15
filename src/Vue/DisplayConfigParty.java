package Vue;

import Controleur.PageManager;
import Pattern.PageActionHandler;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DisplayConfigParty extends JPanel {
    private static final String HUMAN = "human";
    private static final String IA_EASY = "ia facile";
    private static final String IA_HARD = "ia difficile";
    private static final String JOUER = "jouer";

    private PageManager pageManager;
    private PageActionHandler pageActionHandler;

    public DisplayConfigParty(JFrame frame, PageManager pageManager, PageActionHandler controllerPage){
        this.pageManager = pageManager;
        this.pageActionHandler = controllerPage;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JComboBox<String> column1 = createDropDownMenu();
        JComboBox<String> column2 = createDropDownMenu();

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
                button.addActionListener(e -> pageActionHandler.menuToGame(pageManager));
                break;
        }
        return button;
    }
}