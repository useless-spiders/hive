package View;

import Listener.ComponentActionListener;
import Listener.KeyActionListener;
import Listener.MouseActionListener;
import Model.Insect.Insect;
import Model.Player;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DisplayMain {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;
    private static final String IMAGE_PATH_INSECTS = "res/Images/Skins/";
    private static final String IMAGE_PATH_ICONS = "res/Images/Icons/";
    private static final String IMAGE_PATH_BACKGROUNDS = "res/Images/Backgrounds/";
    private static final String IMAGE_PATH_HEXAGONS = "res/Images/Hexagons/";
    private static String SKIN_FOLDER = "Default/";

    private DisplayWin displayWin;

    public static Image loadImageHexagons(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(IMAGE_PATH_HEXAGONS + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public static Image loadImageInsects(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(IMAGE_PATH_INSECTS + SKIN_FOLDER + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public static ImageIcon loadIcon(String nom) {
        try {
            return new ImageIcon(IMAGE_PATH_ICONS + nom);
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'icon " + nom);
            System.exit(1);
            return null;
        }
    }

    public static ImageIcon loadIconInsects(String nom) {
        try {
            return new ImageIcon(IMAGE_PATH_INSECTS + SKIN_FOLDER + nom);
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'icon " + nom);
            System.exit(1);
            return null;
        }
    }

    public static Image loadBackground(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(IMAGE_PATH_BACKGROUNDS + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger le fond " + nom);
            System.exit(1);
            return null;
        }
    }

    public static String getImageInsectName(Class<? extends Insect> insectClass, Player player) {
        return insectClass.getSimpleName() + "_" + player.getColor() + ".png";
    }

    public DisplayMain(PageActionHandler pageActionHandler, GameActionHandler gameActionHandler, JFrame frameOpening, JFrame frameMenu, JFrame frameGame, JFrame frameWin) {
        //Affichage de l'opening
        new DisplayOpening(frameOpening, pageActionHandler);
        setupFrame(frameOpening, true, FRAME_WIDTH, FRAME_HEIGHT, JFrame.EXIT_ON_CLOSE);

        //Affichage du menu
        new DisplayConfigParty(frameMenu, pageActionHandler, gameActionHandler);
        setupFrame(frameMenu, false, FRAME_WIDTH, FRAME_HEIGHT, JFrame.EXIT_ON_CLOSE);

        //Affichage du jeu
        DisplayGame displayGame = new DisplayGame(frameGame, pageActionHandler, gameActionHandler);
        setupFrame(frameGame, false, FRAME_WIDTH, FRAME_HEIGHT, JFrame.EXIT_ON_CLOSE);

        //Ajouter les écouteurs
        new MouseActionListener(gameActionHandler, displayGame);
        new KeyActionListener(frameGame, gameActionHandler);
        new ComponentActionListener(frameGame, displayGame);

        //Affichage de la frame de fin de jeu
        this.displayWin = new DisplayWin(frameWin, pageActionHandler, gameActionHandler);
        setupFrame(frameWin, false, 400, 800, JFrame.DO_NOTHING_ON_CLOSE); //Peut être faire des variables globales, j'attends de voir s'il y aura d'autres dimensions);
    }

    private JFrame setupFrame(JFrame frame, boolean isVisible, int frameWidth, int frameHeight, int closeOperation) {
        frame.setSize(frameWidth, frameHeight); // Définir la taille de la fenêtre
        frame.setVisible(isVisible);
        frame.setLocationRelativeTo(null); // Pour centrer l'affichage (notamment pour la frameWin)
        frame.setDefaultCloseOperation(closeOperation); // Définir l'opération de fermeture
        return frame;
    }

    public DisplayWin getDisplayWin() {
        return this.displayWin;
    }
}