import Controller.GameController;

import javax.swing.*;

public class Hive implements Runnable {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Hive());
    }

    @Override
    public void run() {
        new GameController();
    }
}