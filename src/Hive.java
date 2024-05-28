import javax.swing.*;

import Controller.GameController;
import Global.Configuration;

import java.util.ResourceBundle;

public class Hive implements Runnable {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Hive());
    }

    @Override
    public void run() {
        ResourceBundle messages;

        messages = ResourceBundle.getBundle(Configuration.LANGUAGE_PATH + Configuration.LANGUAGE_FILENAME, Configuration.DEFAULT_LANGUAGE);

        new GameController(messages);
    }
}