package Model;

import Global.Configuration;
import Model.Ai.Ai;
import Model.Insect.Ant;
import Model.Insect.Bee;
import Model.Insect.Beetle;
import Model.Insect.Grasshopper;
import Model.Insect.Insect;
import Model.Insect.Spider;

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

    public Player(String name) {
        this.stock = initBank();
        this.turn = 1;
        this.name = name;
        this.isAi = false;
        this.ai = null;
    }

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

    public void reset() {
        this.stock = initBank();
        this.turn = 1;
        this.beePlaced = false;
    }

    public void setAi(Ai ai) {
        this.isAi = true;
        this.ai = ai;
    }

    public boolean isAi() {
        return this.isAi;
    }

    public Ai getAi() {
        return this.ai;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTurn() {
        return this.turn;
    }

    public ArrayList<Insect> getStock() {
        return this.stock;
    }

    public ArrayList<Class<? extends Insect>> getTypes() {
        ArrayList<Class<? extends Insect>> remainingClass = new ArrayList<>();
        for (Insect i : this.stock) {
            if (!remainingClass.contains(i.getClass())) {
                remainingClass.add(i.getClass());
            }
        }
        return remainingClass;
    }

    public void incrementTurn() {
        this.turn++;
    }

    public void decrementTurn() {
        this.turn--;
    }

    public boolean isBeePlaced() {
        return this.beePlaced;
    }

    public void setBeePlaced(boolean beePlaced) {
        this.beePlaced = beePlaced;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

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

    public boolean canAddInsect(Class<? extends Insect> insectClass) {
        for (int i = 0; i < stock.size(); i++) {
            Insect insect = stock.get(i);
            if (insectClass.isInstance(insect)) {
                return true;
            }
        }
        return false;
    }

    public Insect getInsect(Class<? extends Insect> insectClass) {
        for (int i = 0; i < stock.size(); i++) {
            Insect insect = stock.get(i);
            if (insectClass.isInstance(insect)) {
                return insect;
            }
        }
        return null;
    }

    public void playInsect(Class<? extends Insect> insectClass) {
        for (int i = 0; i < stock.size(); i++) {
            Insect insect = stock.get(i);
            if (insectClass.isInstance(insect)) {
                stock.remove(i);
                return;
            }
        }
    }

    public void unplayInsect(Insect insect) {
        stock.add(insect);
    }

    public boolean checkBeePlacement(Insect insect) {
        return this.getTurn() != 4 || this.isBeePlaced() || insect instanceof Bee;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(color, name);
    }

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

    @Override
    public String toString() {
        return this.getName();
    }
}
