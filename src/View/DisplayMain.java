package View;

import Global.Configuration;
import Listener.ComponentActionListener;
import Listener.KeyActionListener;
import Listener.MouseActionListener;
import Model.Insect.Bee;
import Model.Insect.Insect;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.FrameMetrics;
import Structure.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DisplayMain {

    private DisplayWin displayWin;
    private DisplayAbort displayAbort;
    private DisplayRestart displayRestart;
    private DisplayGame displayGame;

    public static Image loadImageHexagons(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(Configuration.IMAGE_PATH_HEXAGONS + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public void setHexagonSkin(String selectedSkin) {
        Configuration.DEFAULT_SKINS = selectedSkin;
        this.displayGame.getDisplayBankInsects().updateButtons();
        this.displayGame.getDisplayBankInsects().updateBorderBank();
    }

    public static Image loadImageInsects(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(Configuration.IMAGE_PATH_INSECTS + Configuration.DEFAULT_SKINS + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'image " + nom);
            System.exit(1);
            return null;
        }
    }

    public static ImageIcon loadIcon(String nom) {
        try {
            return new ImageIcon(Configuration.IMAGE_PATH_ICONS + nom);
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'icon " + nom);
            System.exit(1);
            return null;
        }
    }

    public static ImageIcon loadIconInsects(String nom) {
        try {
            return new ImageIcon(Configuration.IMAGE_PATH_INSECTS + Configuration.DEFAULT_SKINS + nom);
        } catch (Exception e) {
            Log.addMessage("Impossible de charger l'icon " + nom);
            System.exit(1);
            return null;
        }
    }

    public static Image loadBackground(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(Configuration.IMAGE_PATH_BACKGROUNDS + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger le fond " + nom);
            System.exit(1);
            return null;
        }
    }

    public static Image loadRules(String nom) {
        try {
            return ImageIO.read(Files.newInputStream(Paths.get(Configuration.IMAGE_PATH_RULES + nom)));
        } catch (Exception e) {
            Log.addMessage("Impossible de charger la règle " + nom);
            System.exit(1);
            return null;
        }
    }

    public static String getImageInsectName(Class<? extends Insect> insectClass, Player player, Player currentPlayer) {
        String color;
        if (player.getColor() == Configuration.PLAYER_WHITE) {
            color = "white";
        } else {
            color = "black";
        }
        if(insectClass.equals(Bee.class) && player.equals(currentPlayer) && player.getTurn() == 4 && !player.isBeePlaced()) {
            return insectClass.getSimpleName() + "_" + color + "_last_tour" + ".png";
        }
        return insectClass.getSimpleName() + "_" + color + ".png";
    }

    public DisplayMain(GameActionHandler gameActionHandler, JFrame frameOpening,
                       JFrame frameMenu, JFrame frameGame, JFrame frameRules) {

        FrameMetrics.setCurrentFrame(frameOpening);

        //Affichage de l'opening
        new DisplayOpening(frameOpening, gameActionHandler);
        FrameMetrics.setupFrame(frameOpening, true, JFrame.EXIT_ON_CLOSE);
        FrameMetrics.setFrameSize(frameOpening, new Dimension(Configuration.FRAME_WIDTH, Configuration.FRAME_HEIGHT)); //Mettre une taille par défaut
        FrameMetrics.setFullScreen(frameOpening);

        //Affichage du menu
        new DisplayConfigGame(frameMenu, gameActionHandler);
        FrameMetrics.setupFrame(frameMenu, false, JFrame.EXIT_ON_CLOSE);

        //Affichage du jeu
        this.displayGame = new DisplayGame(frameGame, gameActionHandler);
        FrameMetrics.setupFrame(frameGame, false, JFrame.EXIT_ON_CLOSE);


        //Ajouter les écouteurs
        new MouseActionListener(gameActionHandler, displayGame);
        new KeyActionListener(frameGame, gameActionHandler);
        new ComponentActionListener(frameGame, displayGame);

        //Affichage du pop up de fin de jeu
        this.displayWin = new DisplayWin(gameActionHandler);

        //Affichage du pop up d'abandon
        this.displayAbort = new DisplayAbort(gameActionHandler);

        //Affichage du pop up pour recommencer la partie
        this.displayRestart = new DisplayRestart(gameActionHandler);

        //Affichage des regles
        DisplayRules displayRules = new DisplayRules(frameRules);
        FrameMetrics.setupFrame(frameRules, false, JFrame.DISPOSE_ON_CLOSE);
        FrameMetrics.setFrameSize(frameRules, new Dimension(700, 800));
    }

    public DisplayGame getDisplayGame() {
        return this.displayGame;
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