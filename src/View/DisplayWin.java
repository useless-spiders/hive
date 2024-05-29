package View;

import Model.Insect.Bee;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.RessourceLoader;

import javax.swing.*;
import java.awt.*;


public class DisplayWin extends JPanel {
    private GameActionHandler gameActionHandler;
    private Player winner;
    private RessourceLoader ressourceLoader;

    public DisplayWin(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);
    }

    public void updateWinner(int winner) {
        if (winner == 1) {
            this.winner = this.gameActionHandler.getPlayerController().getPlayer1();
        } else if (winner == 2) {
            this.winner = this.gameActionHandler.getPlayerController().getPlayer2();
        } else {
            this.winner = null;
        }
    }

    public void printVictoryDialog() {
        int choice;

        if (this.winner == null) { // Si égalité
            choice = JOptionPane.showOptionDialog(null, "Félicitations, il y a une égalité parfaite ! Que voulez-vous faire maintenant ?", "Egalité",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Rejouer", "Menu principal"}, null);
        } else {
            Image insectImage = this.ressourceLoader.loadImageInsects(this.ressourceLoader.getImageInsectName(Bee.class, this.winner, this.gameActionHandler.getPlayerController().getCurrentPlayer()));
            ImageIcon icon = new ImageIcon(insectImage);
            choice = JOptionPane.showOptionDialog(null, "Félicitations, " + this.winner + " a gagné ! Que voulez-vous faire maintenant ?", "Victoire",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, new String[]{"Rejouer", "Menu principal"}, null);
        }

        if (choice == JOptionPane.YES_OPTION) {
            this.gameActionHandler.restartGameWithSamePlayers();
        } else {
            this.gameActionHandler.getPageController().winToMenu();
            this.gameActionHandler.getPageController().disposeGame();
        }
    }
}
