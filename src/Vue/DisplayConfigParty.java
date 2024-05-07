package Vue;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DisplayConfigParty {
    private static final String HUMAN = "human";
    private static final String IA_EASY = "ia facile";
    private static final String IA_HARD = "ia difficile";

    public DisplayConfigParty(JFrame frame){
        //TODO : à modifier pour afficher proprement les boutons au centre. Il faudra supprimer les setBorder à therme mais c'est utile pour voie les contenaires.
        JPanel positionnementPanel = new JPanel();
        positionnementPanel.setLayout(new GridLayout(4, 4));
        positionnementPanel.setBorder(new LineBorder(Color.BLUE, 2));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBorder(new LineBorder(Color.BLACK, 2));

        JPanel column1 = createColumn("j1");
        JPanel column2 = createColumn("j2");
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(column1);
        mainPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        mainPanel.add(column2);
        mainPanel.add(Box.createHorizontalGlue());

        positionnementPanel.add(createLabel(""));
        positionnementPanel.add(createLabel(""));
        positionnementPanel.add(createLabel(""));
        positionnementPanel.add(createLabel(""));
        positionnementPanel.add(mainPanel);

        frame.getContentPane().add(positionnementPanel, BorderLayout.NORTH);
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