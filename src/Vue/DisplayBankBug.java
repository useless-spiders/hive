package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileInputStream;

public class DisplayBankBug {

    public DisplayBankBug(JFrame frame){
        //TODO:cahnger le nom des images et gerer la couleur en fonction de qui commence
        ImageIcon iconAnt = new ImageIcon("res/Images/Araignee_blanche.png");
        ImageIcon iconBee = new ImageIcon("res/Images/Araignee_blanche.png");
        ImageIcon iconBeetle = new ImageIcon("res/Images/Araignee_blanche.png");
        ImageIcon iconGrasshopper = new ImageIcon("res/Images/Araignee_blanche.png");
        ImageIcon iconSpider = new ImageIcon("res/Images/Araignee_blanche.png");

        // Créer les panneaux de boutons pour J1 et J2
        JPanel panelButtonBankJ1 = new JPanel();
        JPanel panelButtonBankJ2 = new JPanel();

        // Configurer les FlowLayouts pour centrer les boutons horizontalement
        panelButtonBankJ1.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelButtonBankJ2.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Créer et ajouter les boutons à panelButtonBankJ1
        JButton buttonJ1BankAnt = new JButton(iconAnt);
        JButton buttonJ1BankBee = new JButton(iconBee);
        JButton buttonJ1BankBeetle = new JButton(iconBeetle);
        JButton buttonJ1BankGrasshopper = new JButton(iconGrasshopper);
        JButton buttonJ1BankSpider = new JButton(iconSpider);


        // Ajoutez chaque bouton à `panelButtonBankJ1`
        panelButtonBankJ1.add(buttonJ1BankAnt);
        panelButtonBankJ1.add(buttonJ1BankBee);
        panelButtonBankJ1.add(buttonJ1BankBeetle);
        panelButtonBankJ1.add(buttonJ1BankGrasshopper);
        panelButtonBankJ1.add(buttonJ1BankSpider);

        // Créer et ajouter les boutons à panelButtonBankJ2
        JButton buttonJ2BankAnt = new JButton(iconAnt);
        JButton buttonJ2BankBee = new JButton(iconBee);
        JButton buttonJ2BankBeetle = new JButton(iconBeetle);
        JButton buttonJ2BankGrasshopper = new JButton(iconGrasshopper);
        JButton buttonJ2BankSpider = new JButton(iconSpider);

        // Ajoutez chaque bouton à `panelButtonBankJ2`
        panelButtonBankJ2.add(buttonJ2BankAnt);
        panelButtonBankJ2.add(buttonJ2BankBee);
        panelButtonBankJ2.add(buttonJ2BankBeetle);
        panelButtonBankJ2.add(buttonJ2BankGrasshopper);
        panelButtonBankJ2.add(buttonJ2BankSpider);

        buttonJ1BankAnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonJ1BankBee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonJ1BankBeetle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonJ1BankGrasshopper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonJ1BankSpider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });


        buttonJ2BankAnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonJ2BankBee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonJ2BankBeetle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonJ2BankGrasshopper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonJ2BankSpider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        frame.getContentPane().add(panelButtonBankJ1, BorderLayout.SOUTH);
        frame.getContentPane().add(panelButtonBankJ2, BorderLayout.NORTH);

        /*
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

}
