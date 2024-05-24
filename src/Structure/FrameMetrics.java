package Structure;

import javax.swing.*;
import java.awt.*;

public class FrameMetrics {
    public static JFrame currentFrame;

    //Récupérer la taille de la fenêtre, notamment pour l'affichage du background
    public static Dimension getFrameSize(JFrame frame) {
        return frame.getSize();
    }

    public static void setFrameSize(JFrame frame, Dimension size) {
        frame.setSize(size.width, size.height);
    }

    public static void setupFrame(JFrame frame, boolean isVisible, int closeOperation) {
        frame.setVisible(isVisible);
        frame.setLocationRelativeTo(null); // Pour centrer l'affichage (notamment pour la frameWin)
        frame.setDefaultCloseOperation(closeOperation); // Définir l'opération de fermeture
    }

    public static void setCurrentFrame(JFrame frame) {
        currentFrame = frame;
    }

    public static void setFullScreen(JFrame frame) {
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static boolean isFullScreen(JFrame frame) {
        return (frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH && frame.isUndecorated();
    }

    public static void switchFrame(JFrame nextFrame){
        Dimension currentFrameSize = getFrameSize(currentFrame);
        boolean isFullScreen = isFullScreen(currentFrame);

        if (isFullScreen) {
            setFullScreen(nextFrame);
        } else {
            setFrameSize(nextFrame, new Dimension(currentFrameSize.width, currentFrameSize.height));
        }

        currentFrame.setVisible(false);
        nextFrame.setVisible(true);

        setCurrentFrame(nextFrame);
    }
}