import javax.swing.*;

import Controller.GameController;

public class Hive implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Hive());
    }

    @Override
    public void run() {
        new GameController();
    }
}