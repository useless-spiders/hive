import javax.swing.*;

import Controleur.Game;

public class Hive implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Hive());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Hive game");
        Game.start(frame);

    }
}