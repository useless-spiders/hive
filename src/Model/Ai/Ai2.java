package Model.Ai;

import Model.HexGrid;
import Model.Move;
import Model.Player;
import Pattern.GameActionHandler;
import Structure.Log;
import Structure.Node;
import Structure.Tree;

public class Ai2 extends Ai { //MinMax

    Player other;
    int visited;

    /**
     * constructeur
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
     * calcule l'heuristique pour une grille donnée
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
        return result;
    }

    /**
     * calcule le meilleur coup possible
     *
     * @param n     noeud actuel
     * @param gridC grille de jeu
     * @param usC   joueur cloné
     * @param themC joueur adverse cloné
     * @param depth profondeur du noeud
     * @return double
     */
    double maxTree(Node n, HexGrid gridC, Player usC, Player themC, int depth) {
        //si la configuration est gagnante pour un des joueurs, pas besoin de calculer l Heuristique, l egaitee est comptee comme une defaite
        if (gridC.checkLoser(usC)) {
            Log.addMessage("on a perdu");
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if (gridC.checkLoser(themC)) {
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
        if (depth >= 2) {
            double heuristique = heuristic(gridC);
            n.setValue(heuristique);
            return heuristique;
        } else {
            double max = -9999;
            depth++;
            for (Move m : this.gameActionHandler.getMoveController().getMoves(gridC, this.aiPlayer)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, usC);
                double currentH = minTree(nextMove, gridC, usC, themC, depth);
                gridC.unapplyMove(m, themC);
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
     * calcule le pire coup possible
     *
     * @param n     noeud actuel
     * @param gridC grille de jeu
     * @param usC   joueur cloné
     * @param themC joueur adverse cloné
     * @param depth profondeur du noeud
     * @return double
     */
    double minTree(Node n, HexGrid gridC, Player usC, Player themC, int level) {
        //si la configuration est gagnante pour un des joueurs, pas besoin de calculer l Heuristique, l egalitee est comptee comme une defaite
        if (gridC.checkLoser(usC)) {
            n.setValue(Double.MIN_VALUE);
            return Double.MIN_VALUE;
        }
        if (gridC.checkLoser(themC)) {
            n.setValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }
        if (level >= 2) {
            double heuristique = heuristic(gridC);
            n.setValue(heuristique);
            return heuristique;
        } else {
            double min = 9999;
            level++;
            for (Move m : this.gameActionHandler.getMoveController().getMoves(gridC, this.other)) {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                gridC.applyMove(m, themC);
                double currentH = maxTree(nextMove, gridC, usC, themC, level);
                gridC.unapplyMove(m, themC);
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
     * choisis le coup à jouer pour par l'Ia
     *
     * @return coup à jouer
     */
    public Move chooseMove() {
        Tree tree = new Tree();
        this.visited = 0;
        HexGrid gridC = this.gameActionHandler.getGrid().clone();
        Player usC = this.aiPlayer.clone();
        Player themC = this.other.clone();
        maxTree(tree.getRoot(), gridC, usC, themC, 0);
        double max = -9999;
        Move returnMove = null;
        for (Node child : tree.getRoot().getChilds()) {
            if (child.getValue() > max) {
                max = child.getValue();
                returnMove = child.getMove();
            }
        }
        Log.addMessage(visited + " noeuds visités");
        return returnMove;
    }

}
