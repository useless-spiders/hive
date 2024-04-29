package Modele;


import java.util.Stack;

public class Historique {

    private Stack<Coup> historiqueCoups;
    private Stack<Coup> refaireCoups;

    public Historique() {
        this.historiqueCoups = new Stack<>();
        this.refaireCoups = new Stack<>();
    }

    public void ajouterCoup(Coup action) {
        historiqueCoups.push(action);
        refaireCoups.clear();
    }

    public Coup annulerCoup() {
        if (peutAnnuler()) {
            Coup coup = historiqueCoups.pop();
            refaireCoups.push(coup);
            return coup;
        }
        return null;
    }

    public boolean peutAnnuler() {
        return !historiqueCoups.isEmpty();
    }

    public boolean peutRefaire() {
        return !refaireCoups.isEmpty();
    }

    public Coup refaireCoup() {
        if (peutRefaire()) {
            Coup coup = refaireCoups.pop();
            historiqueCoups.push(coup);
            return coup;
        }
        return null;
    }

    @Override
    public String toString() {
        String message = "Historique{\n";
        message += "Historique Coups: ";
        for (Coup coup : historiqueCoups) {
            message += "coups= x:" + coup.getX() + " y:" + coup.getY() + "; ";
        }
        message += "\nRefaire Coups: ";
        for (Coup coup : refaireCoups) {
            message += "coups= x:" + coup.getX() + " y:" + coup.getY() + "; ";
        }
        message += "\n}";
        return message;
    }

}
