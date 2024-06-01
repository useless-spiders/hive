package Model;

import Global.Configuration;
import Model.Ai.Ai;
import Model.Insect.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Player implements Cloneable, Serializable {

    private int color;
    private ArrayList<Insect> stock;
    private String name;
    private int turn;
    private boolean beePlaced = false;
    private boolean isAi;
    private Ai ai;

    /**
     * Constructeur
     *
     * @param name Nom du joueur
     */
    public Player(String name) {
        this.stock = initBank();
        this.turn = 1;
        this.name = name;
        this.isAi = false;
        this.ai = null;
    }

    /**
     * Initialise la banque d'insectes
     *
     * @return ArrayList
     */
    private ArrayList<Insect> initBank() {
        ArrayList<Insect> s = new ArrayList<>();

        for (int i = 0; i < Configuration.MAX_ANT; i++) {
            s.add(new Ant(this));
        }
        for (int i = 0; i < Configuration.MAX_GRASSHOPPER; i++) {
            s.add(new Grasshopper(this));
        }
        for (int i = 0; i < Configuration.MAX_SPIDER; i++) {
            s.add(new Spider(this));
        }
        for (int i = 0; i < Configuration.MAX_BEETLE; i++) {
            s.add(new Beetle(this));
        }
        for (int i = 0; i < Configuration.MAX_BEE; i++) {
            s.add(new Bee(this));
        }

        return s;
    }

    /**
     * Réinitialise le joueur
     */
    public void reset() {
        this.stock = initBank();
        this.turn = 1;
        this.beePlaced = false;
    }

    /**
     * Définit si le joueur est une IA
     *
     * @param ai IA
     */
    public void setAi(Ai ai) {
        this.isAi = true;
        this.ai = ai;
    }

    /**
     * Renvoie si le joueur est une IA
     *
     * @return boolean
     */
    public boolean isAi() {
        return this.isAi;
    }

    /**
     * Renvoie l'IA
     *
     * @return Ai
     */
    public Ai getAi() {
        return this.ai;
    }

    /**
     * Renvoie le nom du joueur
     *
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Définit le nom du joueur
     *
     * @param name Nom du joueur
     */
    public void setName(String name) {
        if(name != null && !name.isEmpty()){
            if(name.length() > Configuration.PLAYER_MAX_NAME_LENGTH){
                this.name = name.substring(0, Configuration.PLAYER_MAX_NAME_LENGTH);
            } else {
                this.name = name;
            }
        }
    }

    /**
     * Renvoie le tour du joueur
     *
     * @return int
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * Renvoie la banque d'insectes
     *
     * @return ArrayList
     */
    public ArrayList<Insect> getStock() {
        return this.stock;
    }

    /**
     * Renvoie les types d'insectes restants
     *
     * @return ArrayList
     */
    public ArrayList<Class<? extends Insect>> getTypes() {
        ArrayList<Class<? extends Insect>> remainingClass = new ArrayList<>();
        for (Insect i : this.stock) {
            if (!remainingClass.contains(i.getClass())) {
                remainingClass.add(i.getClass());
            }
        }
        return remainingClass;
    }

    /**
     * Incrémente le tour
     */
    public void incrementTurn() {
        this.turn++;
    }

    /**
     * Décrémente le tour
     */
    public void decrementTurn() {
        this.turn--;
    }

    /**
     * Renvoie si l'abeille est placée
     *
     * @return boolean
     */
    public boolean isBeePlaced() {
        return this.beePlaced;
    }

    /**
     * Définit si l'abeille est placée
     *
     * @param beePlaced boolean
     */
    public void setBeePlaced(boolean beePlaced) {
        this.beePlaced = beePlaced;
    }

    /**
     * Renvoie la couleur du joueur
     *
     * @return int
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Définit la couleur du joueur
     *
     * @param color Couleur
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Renvoie le nombre d'insectes restants
     *
     * @return int
     */
    public int getInsectCount(Class<? extends Insect> insectClass) {
        int count = 0;
        for (int i = 0; i < stock.size(); i++) {
            Insect insect = stock.get(i);
            if (insectClass.isInstance(insect)) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Renvoie si l'insecte peut être ajouté
     *
     * @param insectClass Classe de l'insecte
     * @return boolean
     */
    public boolean canAddInsect(Class<? extends Insect> insectClass) {
        for (int i = 0; i < stock.size(); i++) {
            Insect insect = stock.get(i);
            if (insectClass.isInstance(insect)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Renvoie l'insecte si toujours présent dans la banque
     *
     * @param insectClass Classe de l'insecte
     * @return Insect
     */
    public Insect getInsect(Class<? extends Insect> insectClass) {
        for (int i = 0; i < stock.size(); i++) {
            Insect insect = stock.get(i);
            if (insectClass.isInstance(insect)) {
                return insect;
            }
        }
        return null;
    }

    /**
     * Joue un insecte et supprime de la banque
     *
     * @param insectClass Classe de l'insecte
     */
    public void playInsect(Class<? extends Insect> insectClass) {
        for (int i = 0; i < stock.size(); i++) {
            Insect insect = stock.get(i);
            if (insectClass.isInstance(insect)) {
                stock.remove(i);
                return;
            }
        }
    }

    /**
     * Ajoute un insecte à la banque
     */
    public void unplayInsect(Insect insect) {
        stock.add(insect);
    }

    /**
     * Renvoie si l'insecte peut être déplacé
     *
     * @param insect Insecte
     * @return boolean
     */
    public boolean checkBeePlacement(Insect insect) {
        return this.getTurn() != 4 || this.isBeePlaced() || insect instanceof Bee;
    }

    /**
     * Vérifie si deux joueurs sont égaux
     *
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Player player = (Player) obj;
        return Objects.equals(color, player.color) && Objects.equals(name, player.name);
    }

    /**
     * Renvoie le hashcode du joueur
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(color, name);
    }

    /**
     * Clone le joueur
     *
     * @return Player
     */
    @Override
    public Player clone() {
        try {
            Player clone = (Player) super.clone();
            clone.stock = new ArrayList<>(this.stock);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Renvoie le nom du joueur
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
