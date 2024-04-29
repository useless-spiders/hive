package Controleur;
import java.util.ArrayList;
import java.util.Random;

import Modele.Case;
import Modele.Grille;
import Structure.Arbre;
import Structure.Noeud;

public class IADifficile extends IA
{
    Random r;
    private Arbre config;

    IADifficile(Jeu j)
    {
        r = new Random();
        this.grille = j.getGrille();
        this.config = new Arbre();
        System.out.println("on configure l'arbre");
        arbreOu(this.config.getRacine(), this.grille);
        System.out.println("arbre configure");
    }

    /*boolean initConfig(Noeud n, Grille g)
    {
        if(!(n.estFeuille()))
        {
            for (int j = 0; j < g.getLignes(); j++)
            {
                for (int i = 0; i < g.getColonnes(); i++)
                {
                    int indice = j * g.getColonnes() + i;
                    if(!(g.getCases().get(indice).getEstMange()))
                    {
                        Grille grilleC = g.clone();
                        grilleC.mange(i, j);
                        Noeud filsN = new Noeud(i, j);
                        n.creeFils(filsN);
                        initConfig(filsN, grilleC);
                    }
                }
            }
            return n.estCoupGagnant();
        }
        else
        {
            return n.estCoupGagnant();
        }

    }*/

    boolean arbreOu(Noeud n,Grille g)
    {
        if(!(g.getCase(0, 0).getEstMange()))
        {
            boolean estGagnant = false;
            for (int j = 0; j < g.getLignes(); j++)
            {
                for (int i = 0; i < g.getColonnes(); i++)
                {
                    int indice = j * g.getColonnes() + i;
                    if(!(g.getCases().get(indice).getEstMange()))
                    {
                        Grille grilleC = g.clone();
                        grilleC.mange(i, j);
                        Noeud filsN = new Noeud(i, j);
                        n.creeFils(filsN);
                        estGagnant = estGagnant || arbreEt(filsN, grilleC);
                    }
                }
            }
            n.setCoupcoupGagnant(estGagnant);
            return estGagnant;
        }
        else
        {
            //cas ou la case (0,0) a ete mangee par nous
            return false;
        }
    }

    boolean arbreEt(Noeud n,Grille g)
    {
        if(!(g.getCase(0, 0).getEstMange()))
        {
            boolean estGagnant = true;
            for (int j = 0; j < g.getLignes(); j++)
            {
                for (int i = 0; i < g.getColonnes(); i++)
                {
                    int indice = j * g.getColonnes() + i;
                    if(!(g.getCases().get(indice).getEstMange()))
                    {
                        Grille grilleC = g.clone();
                        grilleC.mange(i, j);
                        Noeud filsN = new Noeud(i, j);
                        n.creeFils(filsN);
                        estGagnant = estGagnant && arbreOu(filsN, grilleC);
                    }
                }
            }
            n.setCoupcoupGagnant(estGagnant);
            return estGagnant;
        }
        else
        {
            //cas ou la case (0,0) a ete mangee par l autre joueur
            return true;
        }
    }

    // boolean arbreEt(Noeud n,Grille g)
    // {
    //     if (n.estFeuille())
    //     {
    //         return !n.configurationPerdante();
    //     }
    //     boolean retour = true;
    //     for(Noeud c : n.getFils())
    //     {
    //         retour = retour && arbreOu(c);
    //     }
    //     return retour;
    // }

    // boolean arbreOu(Noeud n,Grille g)
    // {
    //     if (n.estFeuille())
    //     {
    //         return n.estCoupPerdant();
    //     }
    //     for(Noeud c : n.getFils())
    //     {
    //         if(arbreEt(c))
    //         {   
    //             //c est un coup gagnant
    //             if(c.getParent()==config.getCourant())
    //             {
    //                 this.coupsJouable.add(c);
    //             }
    //             retour = true;
    //         }
    //     }
    //     return retour;
    // }
    
    Case estimeCoup()
    {
        int i, caseX, caseY;
        ArrayList<Noeud> coupsJouable = new ArrayList<>();
        for(Noeud n : this.config.getCourant().getFils())
        {
            if(n.estCoupGagnant())
            {
                coupsJouable.add(n);
            }
        }
        if(coupsJouable.isEmpty())  // il n y a pas de coups gagants
        {
            //on joue un coup au hasard parmis les coups jouables
            i = r.nextInt(config.getCourant().getFils().size());

            caseX = config.getCourant().getX();
            caseY = config.getCourant().getY();
        }
        else //il y a au moins 1 coup gagnant
        {
            //on joue un coup au hasard parmis les coups jouables
            i = r.nextInt(coupsJouable.size());

            caseX = coupsJouable.get(i).getX();
            caseY = coupsJouable.get(i).getY();
        }
        //mise a jour du courant
        this.config.courant = coupsJouable.get(i);

        return this.grille.getCase(caseX, caseY);
    }

    boolean trouveCourant(int precedentX, int precedentY)
    {
        for(Noeud n : this.config.getCourant().getFils())
        {
            if(n.getX()==precedentX && n.getY()==precedentY)
            {
                this.config.courant = n;
                return true;
            }
        }
        System.out.println("configuration non trouvee");
        return false;
    }

    @Override
    void joue() //cas l Ia est la premiere a jouer
    {
        Case c = estimeCoup();
        this.grille.mange(c.getX(), c.getY());
        System.out.println("l IA mange la case (" + c.getX() + ", " + c.getY() + ")");
    }

    @Override
	void joue(int precedentX, int precedentY) //on joue après le tour de l'autre joueur
    {
        if(trouveCourant(precedentX, precedentY))
        {
            Case c = estimeCoup();
            this.grille.mange(c.getX(), c.getY());
            System.out.println("l IA mange la case (" + c.getX() + ", " + c.getY() + ")");
        }
        else
        {
            System.out.println("la configuration actuelle n'est pas trouvee");
        }
    }


}
