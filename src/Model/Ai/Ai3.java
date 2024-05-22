package Model.Ai;

import Model.HexCell;
import Model.HexGrid;
import Model.Move;
import Model.Player;
import Structure.Tree;
import Structure.Node;
import Model.Insect.Ant;
import Model.Insect.Bee;
import Model.Insect.Beetle;
import Model.Insect.Grasshopper;
import Model.Insect.Insect;
import Model.Insect.Spider;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
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


    double heuristique(HexGrid g) {
        int result = 0;
        for (HexCoordinate h : g.getGrid().keySet()) {
            HexCell cell = g.getCell(h);
            Insect insect = cell.getTopInsect();

            if (insect instanceof Ant && insect.getPlayer() == this.aiPlayer) {
                result += 3;
            }
            if (insect instanceof Ant && insect.getPlayer() != this.aiPlayer) {
                result -= 3;
            }

            if (insect instanceof Beetle && insect.getPlayer() == this.aiPlayer) {
                result += 2;
            }
            if (insect instanceof Beetle && insect.getPlayer() != this.aiPlayer) {
                result -= 2;
            }

            if (insect instanceof Grasshopper && insect.getPlayer() == this.aiPlayer) {
                result += 2;
            }
            if (insect instanceof Grasshopper && insect.getPlayer() != this.aiPlayer) {
                result -= 2;
            }

            if (insect instanceof Spider && insect.getPlayer() == this.aiPlayer) {
                result += 1;
            }
            if (insect instanceof Spider && insect.getPlayer() != this.aiPlayer) {
                result -= 1;
            }
            if (insect instanceof Bee) {
                if (insect.getPlayer() == this.aiPlayer) {
                    result -= g.getNeighborsCoordinates(h).size() * 20;
                } else {
                    result += g.getNeighborsCoordinates(h).size() * 20;
                }
            }
        }
        return normalizeHeuristic(result);
    }

    public static double normalizeHeuristic(int heuristic) {
        return (double) (heuristic + 87) / (87 + 87);
    }

    double maxTree(Node n, HexGrid gridC, Player usC, Player otherC, int level) {
        if (level >= 2) {
            double heuristique = heuristique(gridC);
            n.setValue(heuristique);
            return heuristique;
        } else {
            double max = 0;
            level++;
            for (Move m : getMoves(gridC, this.aiPlayer)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, usC);
                if(!(heuristique(gridC) <= 0.3)){
                    double currentH = minTree(nextMove, gridC, usC, otherC, level);
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

    double minTree(Node n, HexGrid gridC, Player usC, Player otherC, int level) {
        if (level >= 2) {
            double heuristique = heuristique(gridC);
            n.setValue(heuristique);
            return heuristique;
        } else {
            double max = 0;
            double min = 1;
            level++;

            for (Move m : getMoves(gridC, this.other)) {
                gridC.applyMove(m, otherC);
                gridC.unapplyMove(m, otherC);
                if (heuristique(gridC) > max) {
                    max = heuristique(gridC);
                }
            }
            if(max >= 0.7){
                return -1;
            }

            for (Move m : getMoves(gridC, this.other)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, otherC);
                double currentH = maxTree(nextMove, gridC, usC, otherC, level);
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
