package Structure;

import Model.Move;

import java.util.ArrayList;

/**
 * Classe pour les noeuds de l'arbre
 */
public class Node {
    Node parent;
    ArrayList<Node> childs;

    Move move;

    double value;

    /**
     * Constructeur
     * @param m Mouvement
     */
    public Node(Move m) {
        this.move = m;
        this.childs = new ArrayList<>();
    }

    /**
     * Constructeur
     */
    public Node() {
        this.move = null;
        this.childs = new ArrayList<>();
    }

    /**
     * Renvoie le mouvement
     * @return Move
     */
    public Move getMove() {
        return this.move;
    }

    /**
     * Définit le mouvement
     * @param m Mouvement
     */
    public void setMove(Move m) {
        this.move = m;
    }

    /**
     * Renvoie la valeur
     * @return double
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Définit la valeur
     * @param value Valeur
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Ajoute un enfant
     * @param n Noeud
     */
    public void newChild(Node n) {
        n.parent = this;
        this.childs.add(n);
    }

    /**
     * Renvoie le parent
     * @return Node
     */
    public Node getParent() {
        return this.parent;
    }

    /**
     * Renvoie les enfants
     * @return ArrayList
     */
    public ArrayList<Node> getChilds() {
        return this.childs;
    }

    /**
     * Renvoie si le noeud est une feuille
     * @return boolean
     */
    public boolean isLeaf() {
        return this.childs.isEmpty();
    }
}
