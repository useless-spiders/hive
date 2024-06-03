package Model.Ai;

import Global.Configuration;
import Model.HexGrid;
import Model.Move;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.Log;
import Structure.Node;
import Structure.Tree;

public class Ai3 extends Ai { //Alpha Beta

    Player other;
    int level;
    long startTime;
    long timeLimit = Configuration.AI_TIME_LIMIT_MS;

    /**
     * Constructeur
     */
    public Ai3(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayerController().getPlayer1().equals(this.aiPlayer)) {
            this.other = this.gameActionHandler.getPlayerController().getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayerController().getPlayer1();
        }
    }

    /**
     * Calcule l'heuristique pour une grille donnée
     *
     * @param g grille de jeu
     * @return double
     */
    @Override
    double heuristic(HexGrid g) {
        double result = 0;
        result -= beeNeighbors(this.aiPlayer, g) * 0.9;
        result += beeNeighbors(this.other, g) * 0.9;
        result += insectsCount(this.aiPlayer, g) * 0.1;
        result -= insectsCount(this.other, g) * 0.1;
        result += insectsBlock(this.aiPlayer, g) * 0.2;
        result += insectFree(this.aiPlayer, g) * 0.01;
        result += isWin(this.aiPlayer, g);
        result -= isWin(this.other, g);
        return result;
    }

    /**
     * Calcule le meilleur coup possible
     *
     * @param n      Node
     * @param gridC  clone HexGrid
     * @param usC    clone Player
     * @param otherC clone Player
     * @param alpha  double
     * @return double
     */
    double maxTree(Node n, HexGrid gridC, Player usC, Player otherC, double alpha, double beta) {
        if (System.currentTimeMillis() - this.startTime >= this.timeLimit || this.level >= Configuration.AI_MAX_LEVEL) {
            double heuristic = this.heuristic(gridC);
            n.setValue(heuristic);
            return heuristic;
        }
        if (gridC.checkLoser(usC)) {
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if (gridC.checkLoser(otherC)) {
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        } else {
            double max = Double.NEGATIVE_INFINITY;
            this.level++;
            for (Move m : this.gameActionHandler.getMoveController().getMoves(gridC, this.aiPlayer)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, usC);
                if (gridC.checkLoser(otherC)) {
                    Log.addMessage("Le joueur " + usC.getName() + " a gagné !");
                    Log.addMessage("Move : " + m.getInsect().getPlayer().getName() + " " + m.getInsect() + " " + m.getPreviousCoor() + " " + m.getNewCoor());
                }
                double currentH = this.minTree(nextMove, gridC, usC, otherC, alpha, beta);
                gridC.unapplyMove(m, usC);
                if (currentH > max) {
                    max = currentH;
                }
                if (max >= beta) {
                    break; // Beta cutoff
                }
                if (max > alpha) {
                    alpha = max;
                }
            }
            n.setValue(max);
            return max;
        }
    }

    /**
     * Calcule le pire coup possible
     *
     * @param n      Node
     * @param gridC  clone HexGrid
     * @param usC    clone Player
     * @param otherC clone Player
     * @param alpha  double
     * @return double
     */
    double minTree(Node n, HexGrid gridC, Player usC, Player otherC, double alpha, double beta) {
        if (System.currentTimeMillis() - this.startTime >= this.timeLimit || this.level >= Configuration.AI_MAX_LEVEL) {
            double heuristic = this.heuristic(gridC);
            n.setValue(heuristic);
            return heuristic;
        }
        if (gridC.checkLoser(usC)) {
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if (gridC.checkLoser(otherC)) {
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        } else {
            double min = Double.POSITIVE_INFINITY;
            this.level++;
            for (Move m : this.gameActionHandler.getMoveController().getMoves(gridC, this.other)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, otherC);
                if (gridC.checkLoser(otherC)) {
                    Log.addMessage("Le joueur " + usC.getName() + " a gagné !");
                    Log.addMessage("Move : " + m.getInsect().getPlayer().getName() + " " + m.getInsect() + " " + m.getPreviousCoor() + " " + m.getNewCoor());
                }
                double currentH = this.maxTree(nextMove, gridC, usC, otherC, alpha, beta);
                gridC.unapplyMove(m, otherC);
                if (currentH < min) {
                    min = currentH;
                }
                if (min <= alpha) {
                    break; // Alpha cutoff
                }
                if (min < beta) {
                    beta = min;
                }
            }
            n.setValue(min);
            return min;
        }
    }

    /**
     * Choisis le coup à jouer pour par l'Ia
     *
     * @return Move
     */
    @Override
    public Move chooseMove() {
        Tree tree = new Tree();
        this.level = 0;
        this.startTime = System.currentTimeMillis();
        HexGrid gridC = this.gameActionHandler.getGrid().clone();
        Player usC = this.aiPlayer.clone();
        Player themC = this.other.clone();
        this.maxTree(tree.getRoot(), gridC, usC, themC, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        double max = Double.NEGATIVE_INFINITY;
        Move returnMove = null;
        for (Node child : tree.getRoot().getChilds()) {
            if (child.getValue() > max) {
                max = child.getValue();
                returnMove = child.getMove();
            }
        }
        Log.addMessage("Temps écoulé : " + (System.currentTimeMillis() - this.startTime) + " ms");
        Log.addMessage(this.level + " profondeur max");
        return returnMove;
    }
}
