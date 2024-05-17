import javax.swing.*;

import Controller.Game;
import Controller.PageManager;

public class Hive implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Hive());
    }

    @Override
    public void run() {
        new Game();
    }
}