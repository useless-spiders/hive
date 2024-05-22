package View;

import Structure.Log;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DisplayRules extends JPanel {

    private static final String RULES_FILE_PATH = "res/Images/Rules/rules_hive.pdf";

    public DisplayRules() {
    }

    public static void openRules() {
        File file = new File(RULES_FILE_PATH);

        if (file.exists()) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.addMessage("Awt Desktop n'est pas supporté!");
            }
        } else {
            Log.addMessage("Le fichier n'existe pas!");
        }
    }
}
