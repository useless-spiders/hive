import javax.swing.*;

import Controleur.Jeu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JeuPrincipal implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new JeuPrincipal());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Gaufre empoisonee");
        Jeu.start(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}