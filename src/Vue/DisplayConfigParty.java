package Vue;

import javax.swing.*;
import java.awt.*;

public class DisplayConfigParty {
    public DisplayConfigParty(JFrame frame){
        JLabel labelJ1 = new JLabel("J1");
        JLabel labelJ2 = new JLabel("J2");
        JButton buttonJ1Human = new JButton("human");
        JButton buttonJ2Human = new JButton("human");
        JButton buttonJ1IaEasy = new JButton("ia facile");
        JButton buttonJ2IaEasy = new JButton("ia facile");
        JButton buttonJ1IaHard = new JButton("ia difficile");
        JButton buttonJ2IaHard = new JButton("ia difficile");

        labelJ1.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonJ1Human.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonJ1IaEasy.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonJ1IaHard.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelJ2.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonJ2Human.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonJ2IaEasy.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonJ2IaHard.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel column1 = new JPanel();
        column1.setLayout(new BoxLayout(column1, BoxLayout.Y_AXIS));
        column1.add(labelJ1);
        column1.add(buttonJ1Human);
        column1.add(buttonJ1IaEasy);
        column1.add(buttonJ1IaHard);

        // Créer la deuxième colonne de boutons
        JPanel column2 = new JPanel();
        column2.setLayout(new BoxLayout(column2, BoxLayout.Y_AXIS));
        column2.add(labelJ2);
        column2.add(buttonJ2Human);
        column2.add(buttonJ2IaEasy);
        column2.add(buttonJ2IaHard);

        // Ajouter les deux colonnes au panneau principal
        mainPanel.add(Box.createHorizontalGlue()); // Ajoute un espace flexible
        mainPanel.add(column1);
        mainPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Espace entre les colonnes
        mainPanel.add(column2);
        mainPanel.add(Box.createHorizontalGlue()); // Ajoute un espace flexible

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

    }
}
