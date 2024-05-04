package Vue;

import Pattern.InsectButtonListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayBankInsects {
    private static final String IMAGE_PATH = "res/Images/";
    private InsectButtonListener listener;

    public DisplayBankInsects(JFrame frame, InsectButtonListener listener) {
        this.listener = listener;

        JPanel panelButtonBankJ1 = createButtonPanel(1);
        JPanel panelButtonBankJ2 = createButtonPanel(2);

        frame.getContentPane().add(panelButtonBankJ1, BorderLayout.SOUTH);
        frame.getContentPane().add(panelButtonBankJ2, BorderLayout.NORTH);
    }

    private JPanel createButtonPanel(int player) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        if (player == 1) {
            panel.add(createButton("Araignee_blanche.png"));
            panel.add(createButton("Fourmi_blanche.png"));
            panel.add(createButton("Reine_abeille_blanche.png"));
            panel.add(createButton("Sauterelle_blanche.png"));
            panel.add(createButton("Scarabee_blanc.png"));
        } else {
            panel.add(createButton("Araignee_noir.png"));
            panel.add(createButton("Fourmi_noir.png"));
            panel.add(createButton("Reine_abeille_noir.png"));
            panel.add(createButton("Sauterelle_noir.png"));
            panel.add(createButton("Scarabee_noir.png"));
        }
        return panel;
    }

    private JButton createButton(String imageName) {
        ImageIcon icon = new ImageIcon(IMAGE_PATH + imageName);
        JButton button = new JButton(icon);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.clicInsectButton();
            }
        });
        return button;
    }


    /*
    J'ai laissé ça ici, cétait commenté dans la version originale dans le constructeur de DisplayBankInsects
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Obtenir la nouvelle taille de la fenêtre
                Dimension frameSize = frame.getSize();

                // Calculer la nouvelle taille des boutons en fonction de la taille de la fenêtre
                // Exemple : Utilisez un facteur de redimensionnement (e.g., 20% de la largeur et de la hauteur de la fenêtre)
                int newButtonWidth = (int) (frameSize.width * 0.1);
                int newButtonHeight = (int) (frameSize.height * 0.1);

                // Redimensionner le bouton
                buttonJ1BankAnt.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
                buttonJ1BankBee.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
                buttonJ1BankBeetle.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
                buttonJ1BankGrasshopper.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
                buttonJ1BankSpider.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
                buttonJ2BankAnt.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
                buttonJ2BankBee.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
                buttonJ2BankBeetle.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
                buttonJ2BankGrasshopper.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
                buttonJ2BankSpider.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));


                // Redimensionner l'image et mettre à jour l'icône
                Image imgAnt = iconAnt.getImage().getScaledInstance(newButtonWidth, newButtonHeight, Image.SCALE_SMOOTH);
                Image imgBee = iconBee.getImage().getScaledInstance(newButtonWidth, newButtonHeight, Image.SCALE_SMOOTH);
                Image imgBeetle = iconBeetle.getImage().getScaledInstance(newButtonWidth, newButtonHeight, Image.SCALE_SMOOTH);
                Image imgGrasshopper = iconGrasshopper.getImage().getScaledInstance(newButtonWidth, newButtonHeight, Image.SCALE_SMOOTH);
                Image imgSpider = iconSpider.getImage().getScaledInstance(newButtonWidth, newButtonHeight, Image.SCALE_SMOOTH);

                iconAnt.setImage(imgAnt);
                iconBee.setImage(imgBee);
                iconBeetle.setImage(imgBeetle);
                iconGrasshopper.setImage(imgGrasshopper);
                iconSpider.setImage(imgSpider);

                buttonJ1BankAnt.setIcon(iconAnt);
                buttonJ1BankBee.setIcon(iconBee);
                buttonJ1BankBeetle.setIcon(iconBeetle);
                buttonJ1BankGrasshopper.setIcon(iconGrasshopper);
                buttonJ1BankSpider.setIcon(iconSpider);


                // Redessiner le cadre pour appliquer les changements
                frame.revalidate();
            }
        });
        */
}