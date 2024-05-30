package Model;

import Global.Configuration;
import Pattern.GameActionHandler;
import Structure.Log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe pour la sauvegarde et le chargement de la partie
 */
public class SaveLoad {

    private final GameActionHandler gameActionHandler;
    private History history;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    /**
     * Constructeur
     */
    public SaveLoad(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    /**
     * Sauvegarde la partie
     *
     * @param history       Historique
     * @param player1       Joueur 1
     * @param player2       Joueur 2
     * @param currentPlayer Joueur courant
     * @return String
     * @throws Exception Exception
     */
    public String saveGame(History history, Player player1, Player player2, Player currentPlayer) throws Exception {
        String fileName = formatFileName();
        File file = new File(fileName);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                Log.addMessage(this.gameActionHandler.getLang().getString("save.folder.error"));
            }
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(history);
            out.writeObject(player1);
            out.writeObject(player2);
            out.writeObject(currentPlayer);
        }
        return fileName;
    }

    /**
     * Charge la partie
     *
     * @param fileName Nom du fichier
     * @return SaveLoad
     * @throws Exception Exception
     */
    public SaveLoad loadGame(String fileName) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            this.history = (History) in.readObject();
            this.player1 = (Player) in.readObject();
            this.player2 = (Player) in.readObject();
            this.currentPlayer = (Player) in.readObject();
        }
        return this;
    }

    /**
     * Renvoie l'historique
     *
     * @return History
     */
    public History getHistory() {
        return this.history;
    }

    /**
     * Renvoie le joueur 1
     *
     * @return Player
     */
    public Player getPlayer1() {
        return this.player1;
    }

    /**
     * Renvoie le joueur 2
     *
     * @return Player
     */
    public Player getPlayer2() {
        return this.player2;
    }

    /**
     * Renvoie le joueur courant
     *
     * @return Player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Formate le nom du fichier
     *
     * @return String
     */
    private static String formatFileName() {
        String timeStamp = new SimpleDateFormat(Configuration.SAVE_FORMAT).format(new Date());
        return Configuration.SAVE_PATH + timeStamp + "." + Configuration.SAVE_EXTENSION;
    }
}