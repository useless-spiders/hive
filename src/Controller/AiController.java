package Controller;


import Global.Configuration;
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
        this.delay = new Timer(Configuration.AI_WAITING_TIME, this);
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
                                Log.addMessage(this.gameActionHandler.getMessages().getString("ia.play.error"));
                            });
                        }
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            Log.addMessage(this.gameActionHandler.getMessages().getString("ia.error") + ex);
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
     * Vérifie si l'IA est en cours
     *
     * @return boolean
     */
    public boolean isAiRunning() {
        return this.delay.isRunning();
    }
}
