package View;

import Listener.ComponentActionListener;
import Listener.KeyActionListener;
import Listener.MouseActionListener;
import Model.Insect.Insect;
import Model.Player;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.FrameMetrics;
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
    private static final String IMAGE_PATH_RULES = "res/Images/Rules/";
    private static String SKIN_FOLDER = "Default/";

    private DisplayWin displayWin;
    private DisplayAbort displayAbort;
    private DisplayRestart displayRestart;

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

    public static Image loadRules(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(IMAGE_PATH_RULES + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger la règle " + nom);
            System.exit(1);
            return null;
        }
    }

    public static String getImageInsectName(Class<? extends Insect> insectClass, Player player) {
        String color;
        if(player.getColor() == Player.WHITE){
            color = "white";
        } else {
            color = "black";
        }
        return insectClass.getSimpleName() + "_" + color + ".png";
    }

    public DisplayMain(PageActionHandler pageActionHandler, GameActionHandler gameActionHandler, JFrame frameOpening,
                       JFrame frameMenu, JFrame frameGame, JFrame frameRules) {

        FrameMetrics.setCurrentFrame(frameOpening);

        //Affichage de l'opening
        new DisplayOpening(frameOpening, pageActionHandler);
        FrameMetrics.setupFrame(frameOpening, true, JFrame.EXIT_ON_CLOSE);
        FrameMetrics.setFrameSize(frameOpening, new Dimension(FRAME_WIDTH, FRAME_HEIGHT)); //Mettre une taille par défaut
        FrameMetrics.setFullScreen(frameOpening);

        //Affichage du menu
        new DisplayConfigParty(frameMenu, pageActionHandler, gameActionHandler);
        FrameMetrics.setupFrame(frameMenu, false, JFrame.EXIT_ON_CLOSE);

        //Affichage du jeu
        DisplayGame displayGame = new DisplayGame(frameGame, pageActionHandler, gameActionHandler);
        FrameMetrics.setupFrame(frameGame, false, JFrame.EXIT_ON_CLOSE);

        //Ajouter les écouteurs
        new MouseActionListener(gameActionHandler, displayGame);
        new KeyActionListener(frameGame, gameActionHandler);
        new ComponentActionListener(frameGame, displayGame);

        //Affichage du pop up de fin de jeu
        this.displayWin = new DisplayWin(pageActionHandler, gameActionHandler);

        //Affichage du pop up d'abandon
        this.displayAbort = new DisplayAbort(gameActionHandler, pageActionHandler);

        //Affichage du pop up pour recommencer la partie
        this.displayRestart = new DisplayRestart(gameActionHandler);

        //Affichage des regles
        DisplayRules displayRules = new DisplayRules(frameRules, pageActionHandler);
        FrameMetrics.setupFrame(frameRules, false, JFrame.DISPOSE_ON_CLOSE);
        FrameMetrics.setFrameSize(frameRules, new Dimension(700, 800));
    }

    public DisplayWin getDisplayWin() {
        return this.displayWin;
    }
    public DisplayAbort getDisplayAbort() {
        return this.displayAbort;
    }
    public DisplayRestart getDisplayRestart() {
        return this.displayRestart;
    }
}