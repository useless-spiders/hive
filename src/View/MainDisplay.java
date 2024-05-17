package View;

import Controller.Game;
import Listener.MouseActionListener;
import Model.Insect.Insect;
import Model.Player;
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
    private JFrame frameGame;
    private Game game;

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

    public MainDisplay(PageActionHandler pageActionHandler, Game game, JFrame frameOpening, JFrame frameMenu, JFrame frameGame){
        this.game = game;
        //Affichage de l'opening
        new DisplayOpening(frameOpening, pageActionHandler);
        setupFrame(frameOpening, true);

        //Affichage du menu
        new DisplayConfigParty(frameMenu, pageActionHandler);
        setupFrame(frameMenu, false);

        //Affichage du jeu
        initializeGame(pageActionHandler, frameGame);
    }

    private JFrame setupFrame(JFrame frame, boolean isVisible) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(isVisible);
        return frame;
    }

    private void initializeGame(PageActionHandler pageActionHandler, JFrame frameGame) {

        this.frameGame = setupFrame(frameGame, false);
        DisplayGame displayGame = new DisplayGame(this.game.getGrid(), this.frameGame, game, pageActionHandler);
        this.game.setDisplayGame(displayGame);

        MouseActionListener mouseActionListener = new MouseActionListener(this.game);
        displayGame.addMouseListener(mouseActionListener);
        displayGame.addMouseMotionListener(mouseActionListener);
    }
}