package View;

import Controller.Game;
import Listener.MouseActionListener;
import Model.HexGrid;
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

    public MainDisplay(PageActionHandler pageActionHandler, JFrame frameOpening, JFrame frameMenu, JFrame frameGame){
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
        HexGrid hexGrid = new HexGrid();
        Game g = new Game(hexGrid);
        this.frameGame = setupFrame(frameGame, false);
        DisplayGame displayGame = new DisplayGame(hexGrid, this.frameGame, g, pageActionHandler);
        g.setDisplayGame(displayGame);

        MouseActionListener mouseActionListener = new MouseActionListener(g);
        displayGame.addMouseListener(mouseActionListener);
        displayGame.addMouseMotionListener(mouseActionListener);
    }
}