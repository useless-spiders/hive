package Modele;

import Modele.Insect.Insect;

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

    public void setName(String name) {
        this.name = name;
    }

    public int getTurn() {
        return this.turn;
    }

    public void incrementTurn() {
        this.turn++;
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

    public boolean canAddInsect(Insect insect) {
        Class<? extends Insect> insectClass = insect.getClass();
        int count = insectsCount.getOrDefault(insectClass, 0);
        if (count >= insect.getMax()) {
            return false;
        }
        insectsCount.put(insectClass, count + 1);
        return true;
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
