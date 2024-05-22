package Structure;

import Model.Move;

import java.util.ArrayList;

public class Node 
{
    Node parent;
    ArrayList<Node> childs;

    Move move;

    int value;

    public Node(Move m)
    {
        this.move = m;
        this.childs = new ArrayList<>();
    }

    // constructeur racine
    public Node()
    {
        this.move = null;
        this.childs = new ArrayList<>();
    }

    public Move getMove()
    {
        return this.move;
    }

    public void setMove(Move m)
    {
        this.move = m;
    }

    public int getValue()
    {
        return this.value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public void newChild(Node n)
    {
        n.parent = this;
        this.childs.add(n);
    }

    public Node getParent()
    {
        return this.parent;
    }

    public ArrayList<Node> getChilds()
    {
        return this.childs;
    }


    public boolean isLeaf()
    {
        return this.childs.isEmpty();
    }
}
