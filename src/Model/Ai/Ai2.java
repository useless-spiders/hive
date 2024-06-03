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
    private int timeLimit = Configuration.AI_TIME_LIMIT_MS;

    /**
     * Constructeur
     */
    public Ai2(GameActionHandler gameActionHandler, Player p) {
        this.visited = 0;
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
     * @param depth  int
     * @return double
     */
    double maxTree(Node n, HexGrid gridC, Player usC, Player otherC, int depth) {
        // If the configuration is winning for one of the players, no need to calculate the heuristic
        if (gridC.checkLoser(usC)) {
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if (gridC.checkLoser(otherC)) {
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
        if (System.currentTimeMillis() - this.startTime >= this.timeLimit || depth >= 5) {
            double heuristicValue = this.heuristic(gridC);
            n.setValue(heuristicValue);
            return heuristicValue;
        } else {
            double max = -9999;
            for (Move m : this.gameActionHandler.getMoveController().getMoves(gridC, this.aiPlayer)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, usC);
                double currentH = this.minTree(nextMove, gridC, usC, otherC, depth + 1);
                gridC.unapplyMove(m, usC); // Unapply move for usC, the player who applied the move
                if (currentH > max) {
                    max = currentH;
                }
                this.visited += 1;
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
     * @param depth  int
     * @return double
     */
    double minTree(Node n, HexGrid gridC, Player usC, Player otherC, int depth) {
        // If the configuration is winning for one of the players, no need to calculate the heuristic
        if (gridC.checkLoser(usC)) {
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if (gridC.checkLoser(otherC)) {
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
        if (System.currentTimeMillis() - this.startTime >= this.timeLimit || depth >= 5) {
            double heuristicValue = this.heuristic(gridC);
            n.setValue(heuristicValue);
            return heuristicValue;
        } else {
            double min = 9999;
            for (Move m : this.gameActionHandler.getMoveController().getMoves(gridC, this.other)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, otherC);
                double currentH = this.maxTree(nextMove, gridC, usC, otherC, depth + 1);
                gridC.unapplyMove(m, otherC); // Unapply move for themC, the player who applied the move
                if (currentH < min) {
                    min = currentH;
                }
                this.visited += 1;
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
        this.visited = 0;
        HexGrid gridC = this.gameActionHandler.getGrid().clone();
        Player usC = this.aiPlayer.clone();
        Player otherC = this.other.clone();
        this.startTime = System.currentTimeMillis();
        this.maxTree(tree.getRoot(), gridC, usC, otherC, 0);
        double max = -9999;
        Move returnMove = null;
        for (Node child : tree.getRoot().getChilds()) {
            if (child.getValue() > max) {
                max = child.getValue();
                returnMove = child.getMove();
            }
        }
        Log.addMessage(this.visited + " nodes visited");
        return returnMove;
    }

}
