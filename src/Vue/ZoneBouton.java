package Vue;

import Modele.Case;
import Modele.Grille;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZoneBouton {
    public ZoneBouton(JFrame frame) {
        JButton button1 = new JButton("Cliquez ici");
        JButton button2 = new JButton("Cliquez ici");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Le bouton a été cliqué !");
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Le bouton 2 a été cliqué !");
            }
        });

        // Créer une boîte verticale pour le bouton
        Box boiteTexte = Box.createVerticalBox();
        boiteTexte.add(button1);
        boiteTexte.add(button2);

        // Ajouter les composants à la frame
        frame.add(boiteTexte, BorderLayout.EAST);

    }

    public void paintBouton(JFrame frame) {

    }



}
