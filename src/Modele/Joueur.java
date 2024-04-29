package Modele;

public class Joueur {
    public static final int TYPEHUMAIN = 0;
    public static final int TYPEIIAALEATOIRE = 1;
    private int type;
    private boolean joueurActif;

    public Joueur(int type){
        switch (type){
            case 0:
                this.type = TYPEHUMAIN;
                break;
            case 1:
                this.type = TYPEIIAALEATOIRE;
                break;
            default:
                System.out.println("type d'utilisateur pas encore implementé");
        }
        this.joueurActif = false;
    }
    public Joueur(int type, boolean estActif){
        switch (type){
            case 0:
                this.type = TYPEHUMAIN;
                break;
            case 1:
                this.type = TYPEIIAALEATOIRE;
                break;
            default:
                System.out.println("type d'utilisateur pas encore implementé");
        }
        this.joueurActif = estActif;
    }
    
    public void rendreActif(){
        this.joueurActif = true;
    }

    public void changerJoueurActif() {
        joueurActif = !joueurActif;
    }

    public boolean estJoueurActif(){
        return this.joueurActif;
    }

    public boolean estTypeHumain(){
        return this.type == TYPEHUMAIN;
    }

    public boolean estTypeIa(){
        return  this.type == TYPEIIAALEATOIRE;
    }

}
