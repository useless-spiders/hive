package View;

import Pattern.PageActionHandler;

import javax.swing.*;
import java.awt.*;

public class DisplayRules extends JPanel {
    private PageActionHandler pageActionHandler;
    private JFrame frameRules;
    private Image imageRule;
    int numRules;


    public DisplayRules(JFrame frameRules, PageActionHandler pageActionHandler) {
        this.frameRules = frameRules;
        this.pageActionHandler = pageActionHandler;
        this.numRules = 1;

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1; // colonne (commence à 0)
        gbc.gridy = 0; // ligne (commence à 0)
        gbc.anchor = GridBagConstraints.NORTHEAST; // ancre dans le coin nord-est
        gbc.weightx = 1.0; // étendre horizontalement
        gbc.weighty = 1.0; // étendre verticalement
        add(createButton("RETOUR"), gbc);

        JPanel navigatorButtonContener = new JPanel(new GridBagLayout());
        navigatorButtonContener.setOpaque(false);
        GridBagConstraints navigatorGbc = new GridBagConstraints();
        navigatorGbc.gridx=0;
        navigatorGbc.gridy=0;
        navigatorButtonContener.add(createButton("preview"), navigatorGbc);
        navigatorGbc.gridx=1;
        navigatorButtonContener.add(createButton("next"), navigatorGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(navigatorButtonContener, gbc);

        frameRules.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frameRules.pack(); // Redimensionne la JFrame pour adapter le JPanel
    }

    private void actionPreview(){
        this.numRules--;
        repaint();
    }

    private void actionNext(){
        this.numRules++;
        repaint();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        switch (text){
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
        this.pageActionHandler.getDisplayBackground().paintRule(g, frameRules, "rule_" + numRules + ".png");
    }
}
