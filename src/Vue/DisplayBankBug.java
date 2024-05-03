package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    }
}
