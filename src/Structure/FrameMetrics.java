package Structure;

import javax.swing.*;
import java.awt.*;

/**
 * Classe pour les métriques de la fenêtre
 */
public class FrameMetrics {
    public static JFrame currentFrame;

    /**
     * Renvoie la taille de la fenêtre
     *
     * @param frame Fenêtre
     * @return Dimension
     */
    public static Dimension getFrameSize(JFrame frame) {
        return frame.getSize();
    }

    /**
     * Définit la taille de la fenêtre
     *
     * @param frame Fenêtre
     * @param size  Taille
     */
    public static void setFrameSize(JFrame frame, Dimension size) {
        frame.setSize(size.width, size.height);
    }

    /**
     * Configure la fenêtre
     */
    public static void setupFrame(JFrame frame, boolean isVisible, int closeOperation) {
        frame.setVisible(isVisible);
        frame.setLocationRelativeTo(null); // Pour centrer l'affichage (notamment pour la frameWin)
        frame.setDefaultCloseOperation(closeOperation); // Définir l'opération de fermeture
    }

    /**
     * Définit la fenêtre actuelle
     *
     * @param frame Frame
     */
    public static void setCurrentFrame(JFrame frame) {
        currentFrame = frame;
    }

    /**
     * Mettre en plein écran
     */
    public static void setFullScreen(JFrame frame) {
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * Vérifie si la fenêtre est en plein écran
     *
     * @param frame Fenêtre
     * @return boolean
     */
    public static boolean isFullScreen(JFrame frame) {
        return (frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH && frame.isUndecorated();
    }

    /**
     * Changer de fenêtre
     *
     * @param nextFrame Fenêtre suivante
     */
    public static void switchFrame(JFrame nextFrame) {
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