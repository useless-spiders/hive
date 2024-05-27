package Model;

import Global.Configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveLoad {

    private History history;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

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

    public History getHistory() {
        return this.history;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    private static String formatFileName() {
        String timeStamp = new SimpleDateFormat(Configuration.SAVE_FORMAT).format(new Date());
        return Configuration.SAVE_PATH + timeStamp + "." + Configuration.SAVE_EXTENSION;
    }
}