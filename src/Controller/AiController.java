package Controller;


import Model.Ai.Ai;
import Model.Move;
import Pattern.GameActionHandler;
import Structure.Log;

import javax.swing.*;

public class AiController {
    private GameActionHandler gameActionHandler;
    private Timer delay;

    public AiController(GameActionHandler gameActionHandler) {
        this.gameActionHandler = gameActionHandler;
    }

    public void startAi() {
        if (this.gameActionHandler.getPlayerController().getCurrentPlayer().getTurn() <= 1 && (this.gameActionHandler.getPlayerController().getPlayer1().isAi() || this.gameActionHandler.getPlayerController().getPlayer2().isAi())) {
            this.delay = new Timer(1000, e -> new Thread(() -> {
                this.delay.stop();
                Ai ai = this.gameActionHandler.getPlayerController().getCurrentPlayer().getAi();
                if (ai != null) {
                    try {
                        Move iaMove = ai.chooseMove();
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
            this.delay.start();
        }
    }

    public void stopAi() {
        if (this.delay != null) {
            this.delay.stop();
        }
    }

    public void changeStateAi() {
        if (this.delay != null) {
            if (this.delay.isRunning()) {
                this.delay.stop();
            } else {
                this.delay.start();
            }
        }
    }

    public boolean isAiRunning() {
        return this.delay.isRunning();
    }

    public Timer getDelay() {
        return this.delay;
    }
}
