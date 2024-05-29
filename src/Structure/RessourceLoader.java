package Structure;

import Global.Configuration;
import Model.Insect.Bee;
import Model.Insect.Insect;
import Model.Player;
import Pattern.GameActionHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

public class RessourceLoader {
    private GameActionHandler gameActionHandler;

    public RessourceLoader(GameActionHandler gameActionHandler){
        this.gameActionHandler = gameActionHandler;
    }

    public Image loadImageHexagons(String nom) {
        try {
            return ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(Configuration.IMAGE_PATH_HEXAGONS + nom));
        } catch (Exception e) {
            Log.addMessage(MessageFormat.format(this.gameActionHandler.getMessages().getString("structure.ressource.load.error"), nom));
            System.exit(1);
            return null;
        }
    }

    public Image loadImageInsects(String nom) {
        try {
            return ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(Configuration.IMAGE_PATH_INSECTS + Configuration.DEFAULT_SKINS + nom));
        } catch (Exception e) {
            Log.addMessage(MessageFormat.format(this.gameActionHandler.getMessages().getString("structure.ressource.load.error"), nom));
            System.exit(1);
            return null;
        }
    }

    public ImageIcon loadIcon(String nom) {
        try {
            return new ImageIcon(ClassLoader.getSystemClassLoader().getResource(Configuration.IMAGE_PATH_ICONS + nom));
        } catch (Exception e) {
            Log.addMessage(MessageFormat.format(this.gameActionHandler.getMessages().getString("structure.ressource.load.error"), nom));
            System.exit(1);
            return null;
        }
    }

    public ImageIcon loadIconInsects(String nom) {
        try {
            return new ImageIcon(ClassLoader.getSystemClassLoader().getResource(Configuration.IMAGE_PATH_INSECTS + Configuration.DEFAULT_SKINS + nom));
        } catch (Exception e) {
            Log.addMessage(MessageFormat.format(this.gameActionHandler.getMessages().getString("structure.ressource.load.error"), nom));
            System.exit(1);
            return null;
        }
    }

    public Image loadBackground(String nom) {
        try {
            return ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(Configuration.IMAGE_PATH_BACKGROUNDS + nom));
        } catch (Exception e) {
            Log.addMessage(MessageFormat.format(this.gameActionHandler.getMessages().getString("structure.ressource.load.error"), nom));
            System.exit(1);
            return null;
        }
    }

    public Image loadRules(String nom) {
        try {
            return ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(Configuration.IMAGE_PATH_RULES + nom));
        } catch (Exception e) {
            Log.addMessage(MessageFormat.format(this.gameActionHandler.getMessages().getString("structure.ressource.load.error"), nom));
            System.exit(1);
            return null;
        }
    }

    public String getImageInsectName(Class<? extends Insect> insectClass, Player player, Player currentPlayer) {
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
}
