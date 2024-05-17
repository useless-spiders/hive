package View;

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

public class MainDisplay {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;
    private static final String IMAGE_PATH = "res/Images/";
    private static final String BACKGROUND_PATH = "res/Backgrounds/";

    public static Image loadImage(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(IMAGE_PATH + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public static ImageIcon loadIcon(String nom) {
        try {
            return new ImageIcon(IMAGE_PATH + nom);
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'icon " + nom);
            System.exit(1);
            return null;
        }
    }

    public static Image loadBackground(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(BACKGROUND_PATH + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger le fond " + nom);
            System.exit(1);
            return null;
        }
    }

    public static String getImageInsectName(Class<? extends Insect> insectClass, Player player) {
        return insectClass.getSimpleName() + "_" + player.getColor() + ".png";
    }

    public MainDisplay(PageActionHandler pageActionHandler, GameActionHandler gameActionHandler, JFrame frameOpening, JFrame frameMenu, JFrame frameWin, JFrame frameGame){
        MouseActionListener mouseActionListener = new MouseActionListener(gameActionHandler);

        //Affichage de l'opening
        new DisplayOpening(frameOpening, pageActionHandler);
        setupFrame(frameOpening, true);

        //Affichage du menu
        new DisplayConfigParty(frameMenu, pageActionHandler);
        setupFrame(frameMenu, false);

        new DisplayWin(frameWin, pageActionHandler);
        setupFrame(frameGame, false);

        //Affichage du jeu
        DisplayGame displayGame = new DisplayGame(gameActionHandler.getGrid(), frameGame, gameActionHandler, pageActionHandler);
        setupFrame(frameGame, false);
        gameActionHandler.setDisplayGame(displayGame);
        displayGame.addMouseListener(mouseActionListener);
        displayGame.addMouseMotionListener(mouseActionListener);
    }

    private JFrame setupFrame(JFrame frame, boolean isVisible) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(isVisible);
        return frame;
    }
}