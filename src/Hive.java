import javax.swing.*;

import Controller.PageManager;

public class Hive implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Hive());
    }

    @Override
    public void run() {
        //JFrame frame = new JFrame("Hive game");
        //Game.start(frame);
        PageManager pageManager = new PageManager();
        pageManager.start();
    }
}