package Model;

import Global.Configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe pour la sauvegarde et le chargement de la partie
 */
public class SaveLoad {

    private History history;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    /**
     * Sauvegarde la partie
     * @param history      Historique
     * @param player1      Joueur 1
     * @param player2      Joueur 2
     * @param currentPlayer Joueur courant
     * @return String
     * @throws Exception Exception
     */
    public static String saveGame(History history, Player player1, Player player2, Player currentPlayer) throws Exception {
        String fileName = formatFileName();
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
     * @param fileName Nom du fichier
     * @return SaveLoad
     * @throws Exception Exception
     */
    public static SaveLoad loadGame(String fileName) throws Exception {
        SaveLoad saveLoad = new SaveLoad();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            saveLoad.history = (History) in.readObject();
            saveLoad.player1 = (Player) in.readObject();
            saveLoad.player2 = (Player) in.readObject();
            saveLoad.currentPlayer = (Player) in.readObject();
        }
        return saveLoad;
    }

    /**
     * Renvoie l'historique
     * @return History
     */
    public History getHistory() {
        return this.history;
    }

    /**
     * Renvoie le joueur 1
     * @return Player
     */
    public Player getPlayer1() {
        return this.player1;
    }

    /**
     * Renvoie le joueur 2
     * @return Player
     */
    public Player getPlayer2() {
        return this.player2;
    }

    /**
     * Renvoie le joueur courant
     * @return Player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Formate le nom du fichier
     * @return String
     */
    private static String formatFileName() {
        String timeStamp = new SimpleDateFormat(Configuration.SAVE_FORMAT).format(new Date());
        return Configuration.SAVE_PATH + timeStamp + "." + Configuration.SAVE_EXTENSION;
    }
}