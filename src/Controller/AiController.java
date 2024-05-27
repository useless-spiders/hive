package Controller;


import Model.Ai.Ai;
import Model.Move;
import Pattern.GameActionHandler;
import Structure.Log;

import javax.swing.*;

/**
 * Controleur pour l'IA
 */
public class AiController {
    private GameActionHandler gameActionHandler;
    private Timer delay;

    /**
     * Constructeur
     * @param gameActionHandler
     */
    public AiController(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    /**
     * Démarre l'IA dans un thread
     */
    public void startAi() {
        if (this.gameActionHandler.getPlayerController().getCurrentPlayer().getTurn() <= 1 && (this.gameActionHandler.getPlayerController().getPlayer1().isAi() || this.gameActionHandler.getPlayerController().getPlayer2().isAi())) {
            this.delay = new Timer(1000, e -> new Thread(() -> {
                this.delay.stop();
                Ai ai = this.gameActionHandler.getPlayerController().getCurrentPlayer().getAi();
                if (ai != null) {
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
                }
            }).start());
            this.delay.start(); // Lance le timer
        }
    }

    /**
     * Arrête l'IA
     */
    public void stopAi() {
        if (this.delay != null) {
            this.delay.stop();
        }
    }

    /**
     * Change l'état de l'IA
     */
    public void changeStateAi() {
        if (this.delay != null) {
            if (this.delay.isRunning()) {
                this.delay.stop();
            } else {
                this.delay.start();
            }
        }
    }

    /**
     * Vérifie si l'IA est en cours
     * @return boolean
     */
    public boolean isAiRunning() {
        return this.delay.isRunning();
    }

    /**
     * Retourne le timer
     * @return Timer
     */
    public Timer getDelay() {
        return this.delay;
    }
}
