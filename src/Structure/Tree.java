package Structure;

/**
 * Classe pour les logs
 */
public class Tree {
    Node root;
    public Node current;

    /**
     * Constructeur
     */
    public Tree() {
        this.root = new Node();
        this.current = root;
    }

    /**
     * Retourne la racine
     * @return Node
     */
    public Node getRoot() {
        return this.root;
    }

    /**
     * Retourne le noeud courant
     * @return Node
     */
    public Node getCurrent() {
        return this.current;
    }

}