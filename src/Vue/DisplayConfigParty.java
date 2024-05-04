package Vue;

import javax.swing.*;
import java.awt.*;

public class DisplayConfigParty {
    private static final String HUMAN = "human";
    private static final String IA_EASY = "ia facile";
    private static final String IA_HARD = "ia difficile";

    public DisplayConfigParty(JFrame frame){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel column1 = createColumn("J1");
        JPanel column2 = createColumn("J2");

        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(column1);
        mainPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        mainPanel.add(column2);
        mainPanel.add(Box.createHorizontalGlue());

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createColumn(String label) {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.add(createLabel(label)); // nom de la colonne
        column.add(createButton(HUMAN));
        column.add(createButton(IA_EASY));
        column.add(createButton(IA_HARD));
        return column;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        // il faudra ajouter un listener
        return button;
    }
}