package Structures;

import javax.swing.*;
import java.awt.*;

public class FrameMetrics {

    //Récupérer la taille de la fenêtre, notamment pour l'affichage du background
    public static Dimension getFrameSize(JFrame frame) {
        return frame.getSize();
    }

    public void cleanFrame(JFrame frame){
        frame.getContentPane().removeAll();
    }
}
