package Structure;

/**
 * Classe pour les logs
 */
public class Tree {
    private final Node current;
    private Node root;

    /**
     * Constructeur
     */
    public Tree() {
        this.root = new Node();
        this.current = root;
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