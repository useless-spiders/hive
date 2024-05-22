package View;

import Pattern.PageActionHandler;
import Structure.FrameMetrics;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayRule extends JPanel {
    private PageActionHandler pageActionHandler;
    private JFrame frameRule;
    private Image imageRule;
    GridBagConstraints gbc;


    public DisplayRule(JFrame frameRule, PageActionHandler pageActionHandler) {
        this.frameRule = frameRule;
        this.pageActionHandler = pageActionHandler;


        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel


        /*
        GridBagConstraints gbc = new GridBagConstraints();
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.anchor = GridBagConstraints.CENTER;
        add(, this.gbc);
        frameRule.add(this);
        */

        frameRule.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frameRule.add(createButtonReturn());
        frameRule.pack(); // Redimensionne la JFrame pour adapter le JPanel
        frameRule.repaint();
    }

    private JButton createButtonReturn() {
        JButton button = new JButton(DisplayMain.loadIcon("Undo.png"));
        button.addActionListener(e -> {
            this.pageActionHandler.ruleToGame();
        });
        return button;
    }

    @Override
    public void paintComponent(Graphics g) {
        //Affichage du background
        this.imageRule = DisplayMain.loadBackground("Opening.png");
        Dimension frameSize = FrameMetrics.getFrameSize(this.frameRule);
        g.drawImage(this.imageRule, 0, 0, frameSize.width, frameSize.height, this);
        System.out.println(frameSize.width + "x" + frameSize.height);
    }
}
