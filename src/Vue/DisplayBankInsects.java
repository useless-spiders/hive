package Vue;

import Modele.Insect.*;
import Pattern.InsectButtonListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayBankInsects {
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
        String color;
        if (player == 1) {
            color = "white";
        } else {
            color = "black";
        }
        panel.add(createButton(new Spider(color)));
        panel.add(createButton(new Ant(color)));
        panel.add(createButton(new Bee(color)));
        panel.add(createButton(new Grasshopper(color)));
        panel.add(createButton(new Beetle(color)));
        return panel;
    }

    private JButton createButton(Insect insect) {
        JButton button = new JButton(Display.loadIcon(insect.getImageName()));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.clicInsectButton(insect);
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