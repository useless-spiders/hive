package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveLoad {

    private static final String SAVE_PATH = "res/Saves/";
    private static final String SAVE_EXTENSION = ".save";

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
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        return SAVE_PATH + timeStamp + SAVE_EXTENSION;
    }
}