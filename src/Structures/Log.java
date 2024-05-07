package Structures;

import java.util.ArrayList;

public class Log {
    private static ArrayList<String> messages = new ArrayList<>();
    private static int last = 0;

    public static void addMessage(String message) {
        messages.add(message);
        displayLogs();
    }

    public static ArrayList<String> getMessages() {
        return messages;
    }

    public static void displayLogs() {
        for (int i = last; i < messages.size(); i++) {
            System.out.println(messages.get(i));
        }
        last = messages.size();
    }
}
