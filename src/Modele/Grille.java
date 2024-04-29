package Modele;

import java.util.ArrayList;

public class Grille implements Cloneable{
    private int lignes;
    private int colonnes;
    private ArrayList<Case> cases;
    private boolean fin;

    public Grille(int lignes, int colonnes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.ajouterCases();
        this.fin = false;
    }

    public Grille(Grille g) {
        this.lignes = g.getLignes();
        this.colonnes = g.getColonnes();
        this.cases = g.getCases();
        this.fin = g.getFin();
    }

    public void ajouterCases() {
        this.cases = new ArrayList<>();
        for (int j = 0; j < this.lignes; j++) {
            for (int i = 0; i < this.colonnes; i++) {
                Case c = new Case(i, j);
                if(i == 0 && j == 0){
                    c.empoisonnerCase();
                }
                this.cases.add(c);
            }
        }
    }

    public void mange(int x, int y) {
        for (int j = y; j < lignes; j++) {
            for (int i = x; i < colonnes; i++) {
                Case c = getCase(i, j);
                if (!c.getEstMange()) {
                    c.mangerCase();
                }
                if(c.getEstPoisson()){
                    this.fin = true;
                }
            }
        }
    }

    public void annuler(int x, int y) {
        for (int j = y; j < lignes; j++) {
            for (int i = x; i < colonnes; i++) {
                Case c = getCase(i, j);
                c.resetCase();
                if (i == 0 && j == 0) {
                    c.empoisonnerCase();
                }
            }
        }
    }

    public int getLignes() {
        return this.lignes;
    }

    public int getColonnes() {
        return this.colonnes;
    }

    public Case getCase(int x, int y) {
        return this.cases.get(y * colonnes + x);
    }

    public ArrayList<Case> getCases() {
        return this.cases;
    }

    public boolean getFin() {
        return this.fin;
    }

    @Override
    public Grille clone()
    {
        Grille grilleC = new Grille(this.getLignes(), this.getColonnes());

        for (int j = 0; j < this.getLignes(); j++) {
            for (int i = 0; i < this.getColonnes(); i++) {
                Case caseIJ = this.getCase(i, j);
                Case caseC = new Case(caseIJ.getX(), caseIJ.getY()); 
                if(i==0 && j==0)
                {
                    caseC.empoisonnerCase();
                }
                if(caseIJ.getEstMange())
                {
                    caseC.mangerCase(); 
                }

                grilleC.cases.set((j*this.getColonnes())+i, caseC);
            }
        }

        return grilleC;
    }
}
