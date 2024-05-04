package Modele.Insect;

import Modele.HexGrid;
import Structures.HexCoordinate;
import Modele.Player;

import java.util.ArrayList;

public abstract class Insect {
    private Player player;

    public Insect(Player player) {
        this.player = player;
    }

    public abstract int getMax();

    public abstract ArrayList<HexCoordinate> playableCells(int x, int y, HexGrid g);

    public String getImageName() {
        return this.getClass().getSimpleName() + "_" + this.player.getColor() + ".png";
    }

    public Player getPlayer() {
        return this.player;
    }
}