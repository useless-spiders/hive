package View;

import Model.Player;
import Pattern.GameActionHandler;
import Pattern.PageActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.*;

public class DisplayWin extends JPanel{
    private static final String REPLAY = "Rejouer";
    private static final String MENU = "Menu principal";
    private PageActionHandler controllerPage;
    private GameActionHandler controllerGame;
    private Player winner;
    private JFrame frameWin;
    private JLabel Wintext;
    private GridBagConstraints gbc;

    public DisplayWin(JFrame frameWin, PageActionHandler controllerPage, GameActionHandler controllerGame){
        this.controllerPage = controllerPage;
        this.controllerGame = controllerGame;
        this.frameWin = frameWin;

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel

        JPanel column = createColumn();
        this.gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(column, gbc);
        frameWin.add(this);

    }

    public void updateWinner(Player winner) {
        this.winner = winner;
        printWinner();
        printIcon();
    }

    public void printWinner() {
        if (Wintext != null) {
            Wintext.setText("Victoire de " + this.winner);
        } else {
            Wintext = createWinnerLabel();
            JPanel panel = (JPanel) frameWin.getContentPane().getComponent(0);
            gbc.gridy = 0;
            panel.add(Wintext, gbc);
            frameWin.revalidate();
            frameWin.repaint();
        }
    }

    private JLabel createWinnerLabel() {
        return new JLabel("Victoire de " + this.winner);
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
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        switch (text) {
            case REPLAY:
                button.addActionListener(e -> {
                    this.controllerGame.restartGameWithSamePlayers();
                    this.controllerPage.winToGame();
                });
                break;
            case MENU:
                button.addActionListener(e -> {
                    this.controllerPage.winToMenu();
                    this.controllerPage.disposeGame();
                });
                break;
            default:
                Log.addMessage("Erreur dans les boutons de la frameWin");
        }
        return button;
    }
}
