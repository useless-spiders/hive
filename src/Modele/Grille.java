package Modele;

import java.util.ArrayList;

public class Grille implements Cloneable{
    private Pion[] plateau;
    private boolean fin;

    public Grille() {
        this.plateau = new Pion[1500];
        this.fin = false;
    }

    public Grille(Grille g) {
        this.indices = getPlateau();
        this.fin = getFin();
    }

    public int[] getPlateau() {
        return this.Plateau;
    }

    public boolean getFin() {
        return this.fin;
    }

    public int get

    @Override
    public Grille clone()
    {
        
    }
}
