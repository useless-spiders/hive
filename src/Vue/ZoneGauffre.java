package Vue;

import Modele.Case;
import Modele.Grille;

import java.awt.*;

public class ZoneGauffre {
    Image gauffreEmpoisonne = Affichage.charge("res/Images/gauffre_empoisonne.png");
    Image gauffreNormal = Affichage.charge("res/Images/gauffre.png");
    private Grille grille;
    private int taille;

    public ZoneGauffre(Grille grille) {
        this.grille = grille;
    }

    public void paintGauffre(Graphics g) {
        int largeurCase = this.taille / grille.getColonnes();
        int hauteurCase = this.taille / grille.getLignes();

        if (grille.getFin()) {
            g.drawString("Fin de la partie", this.taille / 2, this.taille / 2);
        } else {
            for (Case c : grille.getCases()) {
                if (!c.getEstMange()) {
                    if (c.getEstPoisson()) {
                        g.drawImage(gauffreEmpoisonne, c.getX() * largeurCase, c.getY() * hauteurCase, largeurCase, hauteurCase, null);
                    } else {
                        g.drawImage(gauffreNormal, c.getX() * largeurCase, c.getY() * hauteurCase, largeurCase, hauteurCase, null);
                    }
                }
            }
        }
    }

    public void setTaille(int largeurGauffre, int hauteurGauffre) {
        this.taille = Math.min(largeurGauffre, hauteurGauffre);
    }

    public int getTaille() {
        return this.taille;
    }
}
