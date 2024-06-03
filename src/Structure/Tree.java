package Structure;

/**
 * Classe pour les logs
 */
public class Tree {
    private Node root;

    /**
     * Constructeur
     */
    public Tree() {
        this.root = new Node();
    }

    /**
     * Retourne la racine
     *
     * @return Node
     */
    public Node getRoot() {
        return this.root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

}