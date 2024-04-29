package Structure;

public class Arbre 
{
    Noeud racine;
    public Noeud courant;

    public Arbre()
    {
        this.racine = new Noeud();
        this.courant = racine;
    }

    public Noeud getRacine()
    {
        return this.racine;
    }

    public Noeud getCourant()
    {
        return this.courant;
    }

}