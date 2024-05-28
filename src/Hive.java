import javax.swing.*;

import Controller.GameController;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Hive implements Runnable {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Hive());
    }

    @Override
    public void run() {
        // Obtenez la langue de l'utilisateur
        Locale userLocale = Locale.getDefault();

        // Chargez le ResourceBundle approprié
        ResourceBundle messages = ResourceBundle.getBundle("Languages.messages", userLocale);
        new GameController(messages);
    }
}