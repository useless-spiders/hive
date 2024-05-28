package Model.Ai;

import Model.HexGrid;
import Model.Move;
import Model.Player;
import Structure.Tree;
import Structure.Node;
import Pattern.GameActionHandler;
import Structure.Log;

public class Ai5 extends Ai { //Alpha Beta

    Player other;
    int node;
    private final int MAX_NODE = 1000;
    private final int MAX_LEVEL = 10;

    public Ai5(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer =p;
        if (this.gameActionHandler.getPlayerController().getPlayer1() == aiPlayer) {
            this.other = this.gameActionHandler.getPlayerController().getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayerController().getPlayer1();
        }
    }

    @Override
    double heuristic(HexGrid g) {
        double result = 0;
        result += (beeNeighbors(this.other, g) - beeNeighbors(this.aiPlayer, g))*20;
        result += insectsCount(this.aiPlayer, g) - insectsCount(this.other, g);
        result += (insectFree(this.aiPlayer, g) - insectFree(this.other, g))*2;
        return result;
    }

    double maxTree(Node n, HexGrid gridC, Player usC, Player themC, double alpha, double beta, int treeDepth) {
        //si la configuration est gagnante pour un des joueurs, pas besoin de calculer l Heuristique, l egaitee est comptee comme une defaite
        if(gridC.checkLoser(usC))
        {
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if(gridC.checkLoser(themC))
        {
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
        int depth = treeDepth;
        if (this.node >= MAX_NODE || depth >= MAX_LEVEL) {
            double heuristic = heuristic(gridC);
            n.setValue(heuristic);
            return heuristic;
        } else {
            double max = Double.NEGATIVE_INFINITY;
            depth++;
            for (Move m : this.gameActionHandler.getMoveController().getMoves(gridC, this.aiPlayer)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, usC);
                double currentH = minTree(nextMove, gridC, usC, themC, alpha, beta, depth);
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
                node ++;
            }
            n.setValue(max);
            return max;
        }
    }

    double minTree(Node n, HexGrid gridC, Player usC, Player themC, double alpha, double beta, int treeDepth) {
        //si la configuration est gagnante pour un des joueurs, pas besoin de calculer l Heuristique, l egaitee est comptee comme une defaite
        if(gridC.checkLoser(usC))
        {
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if(gridC.checkLoser(themC))
        {
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
        int depth = treeDepth;
        if (this.node >= MAX_NODE || depth >= MAX_LEVEL) {
            double heuristic = heuristic(gridC);
            n.setValue(heuristic);
            return heuristic;
        } else {
            double min = Double.POSITIVE_INFINITY;
            depth++;
            for (Move m : this.gameActionHandler.getMoveController().getMoves(gridC, this.other)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, themC);
                double currentH = maxTree(nextMove, gridC, usC, themC, alpha, beta, depth);
                gridC.unapplyMove(m, themC);
                if (currentH < min) {
                    min = currentH;
                }
                if (min <= alpha) {
                    break; // Alpha cutoff
                }
                if (min < beta) {
                    beta = min;
                }
                node++;
            }
            n.setValue(min);
            return min;
        }
    }

    public Move chooseMove() {
        Tree tree = new Tree();
        this.node = 0;
        HexGrid gridC = this.gameActionHandler.getGrid().clone();
        Player usC = this.aiPlayer.clone();
        Player themC = this.other.clone();
        maxTree(tree.getRoot(), gridC, usC, themC, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
        double max = Double.NEGATIVE_INFINITY;
        Move returnMove = null;
        for (Node child : tree.getRoot().getChilds()) {
            if (child.getValue() > max) {
                max = child.getValue();
                returnMove = child.getMove();
            }
        }
        Log.addMessage(node + " noeuds visités");
        return returnMove;
    }
}