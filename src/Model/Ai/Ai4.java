package Model.Ai;

import Global.Configuration;
import Model.HexGrid;
import Model.Move;
import Model.Player;
import Structure.Tree;
import Structure.Node;
import Pattern.GameActionHandler;
import Structure.Log;

public class Ai4 extends Ai { //Alpha Beta

    Player other;
    int node;

    /**
     * Constructeur
     */
    public Ai4(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayerController().getPlayer1() == aiPlayer) {
            this.other = this.gameActionHandler.getPlayerController().getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayerController().getPlayer1();
        }
    }

    /**
     * calcule l'heuristique pour une grille donnée
     * @param g grille de jeu
     * @return double
     */
    @Override
    double heuristic(HexGrid g) {
        double result = 0;
        result += ((beeNeighbors(this.other, g)*1.5) - beeNeighbors(this.aiPlayer, g))*20;
        result += insectsCount(this.aiPlayer, g);
        result += (insectFree(this.aiPlayer, g) - insectFree(this.other, g))*2;
        return result;
    }

    /**
     * calcule l'heuristique pour une grille donnée
     * @param g grille de jeu
     * @param usC joueur cloné
     * @param themC joueur adverse cloné
     * @return double
     */
    double heuristic(HexGrid g, Player usC, Player themC) {
        double result = 0;
        result -= beeNeighbors(usC, g)*0.7;
        result += beeNeighbors(themC, g)*0.7;
        result += insectsCount(usC, g)*0.1;
        result -= insectsCount(themC, g)*0.1;
        if(usC.isBeePlaced())
        {
            result += insectFree(usC, g)*0.2;
        }
        if (themC.isBeePlaced())
        {
            result -= insectFree(usC, g)*0.2;
        }
        return result;
    }


    /**
     * calcule le meilleur coup possible
     * @param n noeud actuel
     * @param gridC grille de jeu
     * @param usC joueur cloné
     * @param themC joueur adverse cloné
     * @param treeDepth profondeur du noeud
     * @return double
     */
    double maxTree(Node n, HexGrid gridC, Player usC, Player themC, double alpha, double beta, int treeDepth) {
        //si la configuration est gagnante pour un des joueurs, pas besoin de calculer l Heuristique, l egaitee est comptee comme une defaite
        if(gridC.checkLoser(usC))
        {
            Log.addMessage("on a  perdu!");
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if(gridC.checkLoser(themC))
        {
            Log.addMessage("on a  gagné!");
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
        int depth = treeDepth;
        if (this.node >= Configuration.AI_MAX_NODE || depth >= Configuration.AI_MAX_LEVEL) {
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


    /**
     * calcule le pire coup possible
     * @param n noeud actuel
     * @param gridC grille de jeu
     * @param usC joueur cloné
     * @param themC joueur adverse cloné
     * @param treeDepth profondeur du noeud
     * @return double
     */
    double minTree(Node n, HexGrid gridC, Player usC, Player themC, double alpha, double beta, int treeDepth) {
        //si la configuration est gagnante pour un des joueurs, pas besoin de calculer l Heuristique, l egaitee est comptee comme une defaite
        if(gridC.checkLoser(usC))
        {
            Log.addMessage("on a  perdu!");
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if(gridC.checkLoser(themC))
        {
            Log.addMessage("on a  gagné!");
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
        int depth = treeDepth;
        if (this.node >= Configuration.AI_MAX_NODE || depth >= Configuration.AI_MAX_LEVEL) {
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

    /**
     * choisis le coup à jouer pour par l'Ia
     * @return coup à jouer
     */
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