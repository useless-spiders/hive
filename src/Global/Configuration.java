package Global;

import java.util.Locale;

/**
 * Classe de configuration pour l'ensemble du jeu
 */
public class Configuration {
    public static final int PLAYER_WHITE = 0;
    public static final int PLAYER_BLACK = 1;
    public static final String PLAYER_1 = "1";
    public static final String PLAYER_2 = "2";
    public static final int PLAYER_NUMBER = 2;
    public static final int PLAYER_MAX_NAME_LENGTH = 15;

    public static final int MAX_ANT = 3;
    public static final int MAX_GRASSHOPPER = 3;
    public static final int MAX_BEETLE = 2;
    public static final int MAX_SPIDER = 2;
    public static final int MAX_BEE = 1;

    public static final String SAVE_PATH = "./Hive_Saved_Games/";
    public static final String SAVE_EXTENSION = "save";
    public static final String SAVE_FORMAT = "dd-MM-yyyy_HH-mm-ss";

    public static final int HEX_DEFAULT_WIDTH = 100;
    public static final int MAX_HEX_WIDTH = 200;
    public static final int MIN_HEX_WIDTH = 50;

    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 720;
    public static final String IMAGE_PATH_INSECTS = "Images/Skins/";
    public static final String IMAGE_PATH_ICONS = "Images/Icons/";
    public static final String IMAGE_PATH_BACKGROUNDS = "Images/Backgrounds/";
    public static final String IMAGE_PATH_HEXAGONS = "Images/Hexagons/";
    public static final String IMAGE_PATH_RULES = "Images/Rules/";
    public static final int AI_MAX_LEVEL = 10;
    public static final int AI_WAITING_TIME = 2000;
    public static final String DEFAULT_FONT = "Times New Roman";
    public static final int DEFAULT_FONT_SIZE = 30;
    public static final int AI_TIME_LIMIT_MS = 1000;
    public static String DEFAULT_SKINS = "Default/";
    public static String LANGUAGE_PATH = "Languages/";
    public static String LANGUAGE_FILENAME = "messages";
    public static Locale DEFAULT_LANGUAGE = Locale.FRENCH;


}
