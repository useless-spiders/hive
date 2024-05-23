package View;

import Pattern.PageActionHandler;

import javax.swing.*;
import java.awt.*;

public class DisplayRules extends JPanel {
    private PageActionHandler pageActionHandler;
    private JFrame frameRules;
    int numRules = 1;
    Image background = DisplayMain.loadRules("Rule_1.png");
    private static int MIN = 1;
    private static int MAX = 11;

    private JButton previous;
    private JButton next;
    private JButton close;

    public DisplayRules(JFrame frameRules, PageActionHandler pageActionHandler) {
        this.frameRules = frameRules;
        this.pageActionHandler = pageActionHandler;

        this.previous = this.createButtonPrevious();
        this.next = this.createButtonNext();
        this.close = this.createButtonCancel();

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1; // colonne (commence à 0)
        gbc.gridy = 0; // ligne (commence à 0)
        gbc.anchor = GridBagConstraints.NORTHEAST; // ancre dans le coin nord-est
        gbc.weightx = 1.0; // étendre horizontalement
        gbc.weighty = 1.0; // étendre verticalement
        add(this.close, gbc);

        JPanel navigatorButtonContainer = new JPanel(new GridBagLayout());
        navigatorButtonContainer.setOpaque(false);
        GridBagConstraints navigatorGbc = new GridBagConstraints();
        navigatorGbc.gridx = 0;
        navigatorGbc.gridy = 0;
        navigatorButtonContainer.add(this.previous, navigatorGbc);
        navigatorGbc.gridx = 1;
        navigatorButtonContainer.add(this.next, navigatorGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
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

    private JButton createButtonCancel() {
        JButton button = new JButton("Close");
        button.addActionListener(e -> this.pageActionHandler.rulesToGame());
        return button;
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
