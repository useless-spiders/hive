package Structure;

import java.util.ArrayList;

public class Noeud 
{
    Noeud parent;
    // Grille configuration;
    // Coup coup;
    ArrayList<Noeud> fils;

    int x;
    int y;

    boolean coupGagnant;

    public Noeud(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.fils = new ArrayList<>();
        if(this.x == 0 && this.y== 0)
        {
            this.coupGagnant = false;
        }
        else
        {
            this.coupGagnant = true;
        }

    }

    // constructeur racine
    public Noeud()
    {
        this.x = -1;
        this.y = -1;
        this.fils = new ArrayList<>();
        this.coupGagnant = false;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public void creeFils(Noeud n)
    {
        n.parent = this;
        this.fils.add(n);
    }

    public void setCoupcoupGagnant(boolean b)
    {
        this.coupGagnant = b;
    }

    public boolean estCoupGagnant()
    {
        return this.coupGagnant;
    }

    // public Noeud(Grille g)
    // {
    //     this.parent = null;
    //     this.configuration = g;
    //     this.fils = new ArrayList<>();
    //     this.coup = null;
    // }

    // public Noeud(Grille g, Coup c)
    // {
    //     this.parent = null;
    //     this.configuration = g;
    //     this.fils = new ArrayList<>();
    //     this.coup = new Coup(c.getX(), c.getY());
    // }

    // public void ajouteFils(Grille g, Coup c)
    // {
    //     Noeud n = new Noeud(g, c);
    //     n.parent=this;
    //     this.fils.add(n);
    // }

    public Noeud getParent()
    {
        return this.parent;
    }

    // public Coup getCoup()
    // {
    //     return this.coup;
    // }

    // public Grille getConfig()
    // {
    //     return this.configuration;
    // }

    public ArrayList<Noeud> getFils()
    {
        return this.fils;
    }

    // public boolean configurationPerdante()
    // {
    //     return this.configuration.getCase(0, 0).getEstMange();
    // }

    public boolean estFeuille()
    {
        return this.fils.isEmpty();
    }
}
