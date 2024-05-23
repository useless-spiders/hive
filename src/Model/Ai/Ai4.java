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
    private Tree config;
    int node;
    int level;
    private final int MAX_NODE = 1000;
    private final int MAX_LEVEL = 10;

    public Ai4(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayer1() == aiPlayer) {
            this.other = this.gameActionHandler.getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayer1();
        }
    }

    @Override
    double heuristic(HexGrid g) {
        double result = 0;
        result += (beeNeighbors(this.other, g) - beeNeighbors(this.aiPlayer, g))*10;
        result += insectsCount(this.aiPlayer, g) - insectsCount(this.other, g);
        result += (insectFree(this.aiPlayer, g) - insectFree(this.other, g))*2;
        return result;
    }

    double maxTree(Node n, HexGrid gridC, Player usC, Player otherC, double alpha, double beta) {
        if (this.node >= MAX_NODE || level >= MAX_LEVEL) {
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
                double currentH = minTree(nextMove, gridC, usC, otherC, alpha, beta);
                gridC.unapplyMove(m, usC);
                node ++;
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

    double minTree(Node n, HexGrid gridC, Player usC, Player otherC, double alpha, double beta) {
        if (this.node >= MAX_NODE || level >= MAX_LEVEL) {
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
                double currentH = maxTree(nextMove, gridC, usC, otherC, alpha, beta);
                gridC.unapplyMove(m, otherC);
                node++;
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
        this.node = 0;
        this.level = 0;
        HexGrid gridC = this.gameActionHandler.getGrid().clone();
        Player usC = this.aiPlayer.clone();
        Player themC = this.other.clone();
        maxTree(this.config.getRoot(), gridC, usC, themC, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        double max = Double.NEGATIVE_INFINITY;
        Move returnMove = null;
        for (Node child : this.config.getRoot().getChilds()) {
            if (child.getValue() > max) {
                max = child.getValue();
                returnMove = child.getMove();
            }
        }
        Log.addMessage(node + " noeuds visités");
        Log.addMessage(level + " profondeur max");
        return returnMove;
    }
}