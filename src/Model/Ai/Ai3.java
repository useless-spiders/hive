package Model.Ai;
import Model.HexGrid;
import Model.Move;
import Model.Player;
import Structure.Tree;
import Structure.Node;
import Pattern.GameActionHandler;
import Structure.Log;

public class Ai3 extends Ai {

    Player other;
    int visited;
    private Tree config;

    public Ai3(GameActionHandler gameActionHandler, Player p) {
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
        result -= beeNeighbors(this.aiPlayer, g)*0.5;
        result += beeNeighbors(this.other, g)*0.5;
        return result;
    }

    double maxTree(Node n, HexGrid gridC, Player usC, Player otherC, int level, double a) {
        if(level == 0){
            a = heuristic(gridC);
        }
        if (level >= 2) {
            double heuristique = heuristic(gridC);
            n.setValue(heuristique);
            return heuristique;
        } else {
            double max = 0;
            level++;
            for (Move m : getMoves(gridC, this.aiPlayer)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, usC);
                double h = heuristic(gridC);
                if(!(h <= h*0.95)){
                    double currentH = minTree(nextMove, gridC, usC, otherC, level, h);
                    if (currentH > max) {
                        max = currentH;
                    }
                }
                gridC.unapplyMove(m, otherC);
                visited += 1;
            }
            n.setValue(max);
            return max;
        }

    }

    double minTree(Node n, HexGrid gridC, Player usC, Player otherC, int level, double a) {
        if (level >= 2) {
            double heuristic = heuristic(gridC);
            n.setValue(heuristic);
            return heuristic;
        } else {
            double max = 0;
            double min = 1;
            level++;

            for (Move m : getMoves(gridC, this.other)) {
                gridC.applyMove(m, otherC);
                gridC.unapplyMove(m, otherC);
                double heuristic = heuristic(gridC);
                if (heuristic > max) {
                    max = heuristic;
                }
            }
            if(max >= a*1.05){
                return -1;
            }

            for (Move m : getMoves(gridC, this.other)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, otherC);
                double currentH = maxTree(nextMove, gridC, usC, otherC, level, heuristic(gridC));
                gridC.unapplyMove(m, otherC);
                if (currentH < min) {
                    min = currentH;
                }
                visited += 1;
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
        maxTree(this.config.getCurrent(), gridC, usC, themC, 0, 0);
        double max = 0;
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
