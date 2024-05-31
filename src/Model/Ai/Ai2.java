package Model.Ai;

import Global.Configuration;
import Model.HexGrid;
import Model.Move;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.Log;
import Structure.Node;
import Structure.Tree;

public class Ai2 extends Ai { // MinMax

    Player other;
    int visited;
    private long startTime;
    private int timeLimit;
    private int level;

    /**
     * Constructor
     */
    public Ai2(GameActionHandler gameActionHandler, Player p) {
        this.visited = 0;
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayerController().getPlayer1() == aiPlayer) {
            this.other = this.gameActionHandler.getPlayerController().getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayerController().getPlayer1();
        }
    }

    /**
     * Calculate the heuristic for a given grid
     *
     * @param g game grid
     * @return double
     */
    @Override
    double heuristic(HexGrid g) {
        double result = 0;
        result -= beeNeighbors(this.aiPlayer, g) * 0.9;
        result += beeNeighbors(this.other, g) * 0.9;
        result += insectsCount(this.aiPlayer, g) * 0.1;
        result -= insectsCount(this.other, g) * 0.1;
        result += insectsBlock(aiPlayer, g) * 0.2;
        result += insectFree(aiPlayer, g) * 0.01;
        result += isWin(this.aiPlayer, g);
        result -= isWin(this.other, g);
        return result;
    }

    /**
     * Calculate the best possible move
     *
     * @param n     current node
     * @param gridC cloned grid
     * @param usC   cloned player
     * @param themC cloned opponent
     * @param depth depth of the node
     * @return double
     */
    double maxTree(Node n, HexGrid gridC, Player usC, Player themC, int depth) {
        // If the configuration is winning for one of the players, no need to calculate the heuristic
        if (gridC.checkLoser(usC)) {
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if (gridC.checkLoser(themC)) {
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
        if (System.currentTimeMillis() - startTime >= timeLimit || depth >= 5) {
            double heuristicValue = heuristic(gridC);
            n.setValue(heuristicValue);
            return heuristicValue;
        } else {
            double max = -9999;
            for (Move m : this.gameActionHandler.getMoveController().getMoves(gridC, this.aiPlayer)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, usC);
                double currentH = minTree(nextMove, gridC, usC, themC, depth + 1);
                gridC.unapplyMove(m, usC); // Unapply move for usC, the player who applied the move
                if (currentH > max) {
                    max = currentH;
                }
                visited += 1;
            }
            n.setValue(max);
            return max;
        }
    }

    /**
     * Calculate the worst possible move
     *
     * @param n     current node
     * @param gridC cloned grid
     * @param usC   cloned player
     * @param themC cloned opponent
     * @param depth depth of the node
     * @return double
     */
    double minTree(Node n, HexGrid gridC, Player usC, Player themC, int depth) {
        // If the configuration is winning for one of the players, no need to calculate the heuristic
        if (gridC.checkLoser(usC)) {
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if (gridC.checkLoser(themC)) {
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
        if (System.currentTimeMillis() - startTime >= timeLimit || depth >= 5) {
            double heuristicValue = heuristic(gridC);
            n.setValue(heuristicValue);
            return heuristicValue;
        } else {
            double min = 9999;
            for (Move m : this.gameActionHandler.getMoveController().getMoves(gridC, this.other)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, themC);
                double currentH = maxTree(nextMove, gridC, usC, themC, depth + 1);
                gridC.unapplyMove(m, themC); // Unapply move for themC, the player who applied the move
                if (currentH < min) {
                    min = currentH;
                }
                visited += 1;
            }
            n.setValue(min);
            return min;
        }
    }

    /**
     * Choose the move to play for the AI
     *
     * @return move to play
     */
    public Move chooseMove() {
        Tree tree = new Tree();
        this.visited = 0;
        HexGrid gridC = this.gameActionHandler.getGrid().clone();
        Player usC = this.aiPlayer.clone();
        Player themC = this.other.clone();
        this.startTime = System.currentTimeMillis();
        this.timeLimit = Configuration.AI_TIME_LIMIT_MS; // Time limit in milliseconds
        maxTree(tree.getRoot(), gridC, usC, themC, 0);
        double max = -9999;
        Move returnMove = null;
        for (Node child : tree.getRoot().getChilds()) {
            if (child.getValue() > max) {
                max = child.getValue();
                returnMove = child.getMove();
            }
        }
        Log.addMessage(visited + " nodes visited");
        return returnMove;
    }

}
