package Model.Ai;

import Model.HexGrid;
import Model.Move;
import Model.Player;
import Structure.Tree;
import Structure.Node;
import Pattern.GameActionHandler;
import Structure.Log;

public class Ai4 extends Ai { //Alpha Beta

    Player other;
    int visited;
    private Tree config;

    public Ai4(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        this.visited = 0;
        if (this.gameActionHandler.getPlayer1() == aiPlayer) {
            this.other = this.gameActionHandler.getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayer1();
        }
    }

    @Override
    double heuristic(HexGrid g) {
        double result = 0;
        result -= beeNeighbors(this.aiPlayer, g) * 0.9;
        result += beeNeighbors(this.other, g) * 0.9;
        result += insectsCount(this.aiPlayer, g) * 0.1;
        result -= insectsCount(this.other, g) * 0.1;
        return result;
    }

    double maxTree(Node n, HexGrid gridC, Player usC, Player otherC, int level, double alpha, double beta) {
        if (this.visited >= 10000 || level >= 10) {
            double heuristic = heuristic(gridC);
            n.setValue(heuristic);
            return heuristic;
        } else {
            double max = Double.NEGATIVE_INFINITY;
            level++;
            for (Move m : getMoves(gridC, this.aiPlayer)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, usC);
                double currentH = minTree(nextMove, gridC, usC, otherC, level, alpha, beta);
                gridC.unapplyMove(m, usC);
                visited += 1;
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

    double minTree(Node n, HexGrid gridC, Player usC, Player otherC, int level, double alpha, double beta) {
        if (this.visited >= 10000 || level >= 10) {
            double heuristic = heuristic(gridC);
            n.setValue(heuristic);
            return heuristic;
        } else {
            double min = Double.POSITIVE_INFINITY;
            level++;
            for (Move m : getMoves(gridC, this.other)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, otherC);
                double currentH = maxTree(nextMove, gridC, usC, otherC, level, alpha, beta);
                gridC.unapplyMove(m, otherC);
                visited += 1;
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

    public Move chooseMove() {
        this.config = new Tree();
        this.visited = 0;
        HexGrid gridC = this.gameActionHandler.getGrid().clone();
        Player usC = this.aiPlayer.clone();
        Player themC = this.other.clone();
        maxTree(this.config.getCurrent(), gridC, usC, themC, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        double max = Double.NEGATIVE_INFINITY;
        Move returnMove = null;
        for (Node child : this.config.getCurrent().getChilds()) {
            if (child.getValue() > max) {
                max = child.getValue();
                returnMove = child.getMove();
            }
        }
        Log.addMessage(visited + " noeuds visités");
        return returnMove;
    }
}