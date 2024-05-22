package View;

import Pattern.PageActionHandler;
import Structure.FrameMetrics;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayRule extends JPanel {
    private PageActionHandler controllerPage;
    private JFrame frameRule;
    private Image imageRule;
    int numRule;


    public DisplayRule(JFrame frameRule, PageActionHandler controllerPage) {
        this.frameRule = frameRule;
        this.controllerPage = controllerPage;
        this.numRule = 1;

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1; // colonne (commence à 0)
        gbc.gridy = 0; // ligne (commence à 0)
        gbc.anchor = GridBagConstraints.NORTHEAST; // ancre dans le coin nord-est
        gbc.weightx = 1.0; // étendre horizontalement
        gbc.weighty = 1.0; // étendre verticalement
        add(createButton("RETOUR"), gbc);

        JPanel navigatorButtonContenaire = new JPanel(new GridBagLayout());
        navigatorButtonContenaire.setOpaque(false);
        GridBagConstraints navigatorGbc = new GridBagConstraints();
        navigatorGbc.gridx=0;
        navigatorGbc.gridy=0;
        navigatorButtonContenaire.add(createButton("preview"), navigatorGbc);
        navigatorGbc.gridx=1;
        navigatorButtonContenaire.add(createButton("next"), navigatorGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(navigatorButtonContenaire, gbc);

        frameRule.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frameRule.pack(); // Redimensionne la JFrame pour adapter le JPanel
    }

    private void actionPreview(){
        this.numRule--;
        repaint();
    }

    private void actionNext(){
        this.numRule++;
        repaint();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        switch (text){
            case "RETOUR":
                button.addActionListener(e -> this.controllerPage.ruleToGame());
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
        this.controllerPage.getDisplayBackground().paintRule(g, frameRule, "rule_" + numRule + ".png");
    }
}
