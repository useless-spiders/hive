package Model;

public class Tree 
{
    Node root;
    public Node current;

    public Tree()
    {
        this.root = new Node();
        this.current = root;
    }

    public Node getRoot()
    {
        return this.root;
    }

    public Node getCurrent()
    {
        return this.current;
    }

}