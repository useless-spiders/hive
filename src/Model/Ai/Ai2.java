package Model.Ai;
import Model.HexGrid;
import Model.Move;
import Model.Player;
import Structure.Tree;
import Structure.Node;
import Pattern.GameActionHandler;
import Structure.Log;

public class Ai2 extends Ai {

    Player other;
    int visited;
    private Tree config;

    public Ai2(GameActionHandler gameActionHandler, Player p) {
        this.visited = 0;
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
        result -= beeNeighbors(this.aiPlayer, g)*0.5;
        result += beeNeighbors(this.other, g)*0.5;
        return result;
    }

    double maxTree(Node n, HexGrid gridC, Player usC, Player otherC, int level) {
        if (level >= 2) {
            double heuristique = heuristic(gridC);
            n.setValue(heuristique);
            return heuristique;
        } else {
            double max = -9999;
            level++;
            for (Move m : getMoves(gridC, this.aiPlayer)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, usC);
                double currentH = minTree(nextMove, gridC, usC, otherC, level);
                gridC.unapplyMove(m, otherC);
                if (currentH > max) {
                    max = currentH;
                }
                visited += 1;
            }
            n.setValue(max);
            return max;
        }

    }

    double minTree(Node n, HexGrid gridC, Player usC, Player otherC, int level) {
        if (level >= 2) {
            double heuristique = heuristic(gridC);
            n.setValue(heuristique);
            return heuristique;
        } else {
            double min = 9999;
            level++;
            for (Move m : getMoves(gridC, this.other)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, otherC);
                double currentH = maxTree(nextMove, gridC, usC, otherC, level);
                gridC.unapplyMove(m, otherC);
                if (currentH < min) {
                    min = currentH;
                }
                visited +=1;
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
        maxTree(this.config.getCurrent(), gridC, usC, themC, 0);
        double max = -9999;
        Move returnMove = null;
        for (Node child : this.config.getCurrent().getChilds()) {
            if (child.getValue() > max) {
                max = child.getValue();
                returnMove = child.getMove();
            }
        }
        Log.addMessage(visited+" noeuds visités");
        return returnMove;
    }

}
