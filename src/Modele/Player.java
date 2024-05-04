package Modele;

import Modele.Insect.Insect;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player {
    private String color;
    private Map<Class<? extends Insect>, Integer> insectsCount;

    public Player(String color) {
        this.color = color;
        this.insectsCount = new HashMap<>();
    }

    public String getColor() {
        return color;
    }

    public boolean canAddInsect(Insect insect) {
        System.out.println(insectsCount);
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
