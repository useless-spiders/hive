package Structure;

import Model.HexGrid;
import Model.Move;

import java.util.ArrayList;

/**
 * Classe pour les noeuds de l'arbre
 */
public class Node {
    private final ArrayList<Node> childs;
    private Node parent;
    private Move move;
    private double value;
    private int visitCount;
    private HexGrid state;

    /**
     * Constructeur
     *
     * @param m Mouvement
     */
    public Node(Move m) {
        this.move = m;
        this.childs = new ArrayList<>();
        this.visitCount = 0;
    }

    public Node(Move m, HexGrid g) {
        this.move = m;
        this.childs = new ArrayList<>();
        this.visitCount = 0;
        this.state = g.clone();
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
     *
     * @return Move
     */
    public Move getMove() {
        return this.move;
    }

    /**
     * Définit le mouvement
     *
     * @param m Mouvement
     */
    public void setMove(Move m) {
        this.move = m;
    }

    /**
     * Renvoie la valeur
     *
     * @return double
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Définit la valeur
     *
     * @param value Valeur
     */
    public void setValue(double value) {
        this.value = value;
    }

    public HexGrid getState() {
        return this.state;
    }

    public void setState(HexGrid g) {
        this.state = g.clone();
    }

    /**
     * Ajoute a la valeur
     *
     * @param value Valeur
     */
    public void addValue(double value) {
        this.value += value;
    }

    /**
     * Ajoute un enfant
     *
     * @param n Noeud
     */
    public void newChild(Node n) {
        n.parent = this;
        this.childs.add(n);
    }

    /**
     * Renvoie le parent
     *
     * @return Node
     */
    public Node getParent() {
        return this.parent;
    }

    /**
     * Renvoie les enfants
     *
     * @return ArrayList
     */
    public ArrayList<Node> getChilds() {
        return this.childs;
    }

    /**
     * Renvoie si le noeud est une feuille
     *
     * @return boolean
     */
    public boolean isLeaf() {
        return this.childs.isEmpty();
    }

    /**
     * Incremente visitCount
     */
    public void incrementVisitCount() {
        this.visitCount++;
    }

    /**
     * Renvoie le visitCount
     *
     * @return int
     */
    public int getVisitCount() {
        return this.visitCount;
    }
}
