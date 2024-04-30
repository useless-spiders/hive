import javax.swing.*;

import Controleur.Game;

public class JeuPrincipal implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new JeuPrincipal());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Gaufre empoisonee");
        Game.start(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}