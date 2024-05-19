package View;

import Model.Player;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayWin {
    private static final String REPLAY = "Rejouer";
    private static final String MENU = "Menu principal";
    private PageActionHandler controllerPage;
    private GameActionHandler controllerGame;
    private Player winner;
    private JFrame frameWin;
    private JFrame frameGame;

    public DisplayWin(JFrame frameWin, JFrame frameGame, PageActionHandler controllerPage, GameActionHandler controllerGame){
        this.controllerPage = controllerPage;
        this.controllerGame = controllerGame;
        this.frameWin = frameWin;
        this.frameGame = frameGame;

        JPanel column1 = createColumn();
        frameWin.add(column1,BorderLayout.CENTER);

    }

    public void updateWinner(Player winner) {
        this.winner = winner;
        this.printWinner();
        this.printIcon();
    }

    private void printWinner() {
        JLabel Wintext = new JLabel("Victoire de " + this.winner);
        frameWin.add(Wintext,BorderLayout.NORTH);
    }

    private void printIcon() {
        //TODO : ajouter l'image de l'abeille de la bonne couleur
    }


    private JPanel createColumn() {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.add(createButton(REPLAY));
        column.add(createButton(MENU));
        return column;
    }
    //TODO: Faire les boutons tel Replay et Menu tel qu il renvoie au bon endroit
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        switch (text) {
            case REPLAY:
                frameGame.setVisible(false);
                button.addActionListener(e -> {
                    this.controllerGame.restartGameWithSamePlayers();
                    this.controllerPage.winToGame();
                });
                break;
            case MENU:
                frameGame.setVisible(false);
                button.addActionListener(e -> {
                    this.controllerPage.winToMenu();
                    frameGame.dispose();
                });
                break;
            default:
                Log.addMessage("Erreur dans les boutons de la frameWin");
        }
        return button;
    }
}
