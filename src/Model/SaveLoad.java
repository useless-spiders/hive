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
    private String fileName;

    public SaveLoad(History history, Player player1, Player player2, Player currentPlayer) {
        this.history = history;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = currentPlayer;
        this.fileName = this.formatFileName();
    }

    public void saveGame() throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.fileName))) {
            out.writeObject(this.history);
            out.writeObject(this.player1);
            out.writeObject(this.player2);
            out.writeObject(this.currentPlayer);
        }
    }

    public void loadGame(String fileName) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            this.history = (History) in.readObject();
            this.player1 = (Player) in.readObject();
            this.player2 = (Player) in.readObject();
            this.currentPlayer = (Player) in.readObject();
        }
    }

    public String getFileName() {
        return this.fileName;
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

    private String formatFileName() {
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        return SAVE_PATH + timeStamp + SAVE_EXTENSION;
    }
}
