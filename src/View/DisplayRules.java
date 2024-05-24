package View;

import Pattern.PageActionHandler;

import javax.swing.*;
import java.awt.*;

public class DisplayRules extends JPanel {
    private JFrame frameRules;
    int numRules = 1;
    Image background = DisplayMain.loadRules("Rule_1.png");
    private static int MIN = 1;
    private static int MAX = 11;

    private JButton previous;
    private JButton next;

    public DisplayRules(JFrame frameRules) {
        this.frameRules = frameRules;

        this.previous = createButtonPrevious();
        this.next = createButtonNext();

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel navigatorButtonContainer = new JPanel(new GridBagLayout());
        navigatorButtonContainer.setOpaque(false);
        GridBagConstraints navigatorGbc = new GridBagConstraints();
        navigatorGbc.anchor = GridBagConstraints.CENTER; // Center alignment within the container
        navigatorGbc.gridheight = 1;
        navigatorGbc.gridx = 0;
        navigatorGbc.gridy = 0;
        navigatorButtonContainer.add(this.previous, navigatorGbc);
        navigatorGbc.gridx = 1;
        navigatorButtonContainer.add(this.next, navigatorGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTH; // Align the container to the bottom of the main panel
        gbc.weightx = 1.0; // Allow horizontal stretching
        gbc.weighty = 1.0; // Allow vertical stretching to push the container down
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around the container
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow horizontal stretching
        add(navigatorButtonContainer, gbc);

        frameRules.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frameRules.pack(); // Redimensionne la JFrame pour adapter le JPanel
    }

    private void actionPrevious() {
        if(this.numRules > MIN){
            this.numRules--;
            this.updateImage();
        }
    }

    private void actionNext() {
        if(this.numRules < MAX){
            this.numRules++;
            this.updateImage();
        }
    }

    private void updateImage() {
        this.background = DisplayMain.loadRules("Rule_" + this.numRules + ".png");
        repaint();
    }

    private JButton createButtonPrevious() {
        JButton button = new JButton("Précédent");
        button.setEnabled(this.numRules > MIN);
        button.addActionListener(e -> actionPrevious());
        return button;
    }


    private JButton createButtonNext() {
        JButton button = new JButton("Suivant");
        button.setEnabled(this.numRules < MAX);
        button.addActionListener(e -> actionNext());
        return button;
    }

    private void updateButtons() {
        this.previous.setEnabled(this.numRules > MIN);
        this.next.setEnabled(this.numRules < MAX);
    }


    @Override
    public void paintComponent(Graphics g) {
        this.updateButtons();
        g.drawImage(this.background, 25, 25, this.frameRules.getWidth() - 130, this.frameRules.getHeight() - 130, this);
    }
}
