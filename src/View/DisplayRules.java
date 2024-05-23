package View;

import Pattern.PageActionHandler;

import javax.swing.*;
import java.awt.*;

public class DisplayRules extends JPanel {
    private PageActionHandler pageActionHandler;
    private JFrame frameRules;
    int numRules = 1;
    Image background = DisplayMain.loadRules("rule_1.png");


    public DisplayRules(JFrame frameRules, PageActionHandler pageActionHandler) {
        this.frameRules = frameRules;
        this.pageActionHandler = pageActionHandler;

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1; // colonne (commence à 0)
        gbc.gridy = 0; // ligne (commence à 0)
        gbc.anchor = GridBagConstraints.NORTHEAST; // ancre dans le coin nord-est
        gbc.weightx = 1.0; // étendre horizontalement
        gbc.weighty = 1.0; // étendre verticalement
        add(createButton("RETOUR"), gbc);

        JPanel navigatorButtonContainer = new JPanel(new GridBagLayout());
        navigatorButtonContainer.setOpaque(false);
        GridBagConstraints navigatorGbc = new GridBagConstraints();
        navigatorGbc.gridx = 0;
        navigatorGbc.gridy = 0;
        navigatorButtonContainer.add(createButton("preview"), navigatorGbc);
        navigatorGbc.gridx = 1;
        navigatorButtonContainer.add(createButton("next"), navigatorGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(navigatorButtonContainer, gbc);

        frameRules.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frameRules.pack(); // Redimensionne la JFrame pour adapter le JPanel
    }

    private void actionPreview() {
        this.numRules--;
        this.updateImage();
    }

    private void actionNext() {
        this.numRules++;
        this.updateImage();
    }

    private void updateImage() {
        this.background = DisplayMain.loadRules("rule_" + this.numRules + ".png");
        repaint();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        switch (text) {
            case "RETOUR":
                button.addActionListener(e -> this.pageActionHandler.rulesToGame());
                break;
            case "preview":
                button.addActionListener(e -> actionPreview());
                break;
            case "next":
                button.addActionListener(e -> actionNext());
                break;
            default:
                break;
        }

        return button;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.background, 25, 25, this.frameRules.getWidth() - 130, this.frameRules.getHeight() - 130, this);
    }
}
