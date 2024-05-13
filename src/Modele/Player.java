package Modele;

import Modele.Insect.Insect;
import Structures.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player {
    private String color;
    private Map<Class<? extends Insect>, Integer> insectsCount;
    private String name;
    private int turn;
    private boolean beePlaced = false;

    public Player(String color, String name) {
        this.color = color;
        this.insectsCount = new HashMap<>();
        this.turn = 1;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getTurn() {
        return this.turn;
    }

    public void incrementTurn() {
        this.turn++;
    }

    public void decrementTurn() {
        this.turn--;
    }

    public boolean isBeePlaced() {
        return beePlaced;
    }

    public void setBeePlaced(boolean beePlaced) {
        this.beePlaced = beePlaced;
    }

    public String getColor() {
        return color;
    }

    public int getInsectCount(Class<? extends Insect> insectClass) {
        return insectsCount.getOrDefault(insectClass, 0);
    }

    public boolean canAddInsect(Insect insect) {
        Class<? extends Insect> insectClass = insect.getClass();
        int count = insectsCount.getOrDefault(insectClass, 0);
        return count < insect.getMax();
    }

    public void addInsect(Insect insect) {
        Class<? extends Insect> insectClass = insect.getClass();
        int count = insectsCount.getOrDefault(insectClass, 0);
        insectsCount.put(insectClass, count + 1);
    }

    public void removeInsect(Insect insect) {
        Class<? extends Insect> insectClass = insect.getClass();
        if(insectsCount.containsKey(insectClass)){
            int count = insectsCount.get(insectClass);
            if(count > 1){
                insectsCount.put(insectClass, count - 1);
            } else {
                insectsCount.remove(insectClass);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Player player = (Player) obj;
        return Objects.equals(color, player.color);
    }

}
