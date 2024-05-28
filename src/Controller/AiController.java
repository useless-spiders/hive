package Controller;


import Model.Ai.Ai;
import Model.Move;
import Pattern.GameActionHandler;
import Structure.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controleur pour l'IA
 */
public class AiController implements ActionListener {
    private GameActionHandler gameActionHandler;
    private Timer delay;

    /**
     * Constructeur
     *
     * @param gameActionHandler GameActionHandler
     */
    public AiController(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
        this.delay = new Timer(1000, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (this.gameActionHandler.getPlayerController().getCurrentPlayer().isAi()) {
            Ai ai = this.gameActionHandler.getPlayerController().getCurrentPlayer().getAi();
            if (ai != null) {
                this.delay.stop();
                new Thread(() -> {
                        try {
                            Move iaMove = ai.chooseMove(); // On récupère le mouvement choisi par l'IA
                            if (iaMove != null) {
                                SwingUtilities.invokeLater(() -> {
                                    this.gameActionHandler.getGrid().applyMove(iaMove, this.gameActionHandler.getPlayerController().getCurrentPlayer());
                                    this.gameActionHandler.getHistoryController().addMove(iaMove);
                                    this.gameActionHandler.getPlayerController().switchPlayer();
                                    this.gameActionHandler.getDisplayGame().getDisplayBankInsects().updateAllLabels();
                                    this.gameActionHandler.getDisplayGame().repaint();
                                });
                            } else {
                                SwingUtilities.invokeLater(() -> {
                                    Log.addMessage("L'IA n'a pas pu jouer, on arrete l'IA");
                                });
                            }
                        } catch (Exception ex) {
                            SwingUtilities.invokeLater(() -> {
                                Log.addMessage("Erreur lors de l'exécution de l'IA dans le thread " + ex);
                            });
                        }
                }).start();
            }
        }
    }

    /**
     * Démarre l'IA dans un thread
     */
    public void startAi() {
        this.delay.start(); // Lance le timer
    }

    /**
     * Arrête l'IA
     */
    public void stopAi() {
        this.delay.stop();
    }

    /**
     * Change l'état de l'IA
     */
    public void changeStateAi() {
        if (this.delay.isRunning()) {
            this.delay.stop();
        } else {
            this.delay.start();
        }
    }

    /**
     * Vérifie si l'IA est en cours
     *
     * @return boolean
     */
    public boolean isAiRunning() {
        return this.delay.isRunning();
    }
}
