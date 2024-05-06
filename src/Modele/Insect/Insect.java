package Modele.Insect;

import Modele.HexCell;
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

    public abstract ArrayList<HexCoordinate> getPossibleMovesCells(int x, int y, HexGrid g);

    public abstract ArrayList<HexCoordinate> getPossibleInsertionCells(HexGrid g);

    public String getImageName() {
        return this.getClass().getSimpleName() + "_" + this.player.getColor() + ".png";
    }

    public Player getPlayer() {
        return this.player;
    }

    /* public boolean isStillConnected(int x, int y, HexGrid h)
    {
        //TODO verifie si le deplacement de l insecte ne casse pas la ruche
    } */

    // can't move any insect if the Bee is not in the grid
    protected boolean canMoveInsect(HexGrid g, Player player){
        for(HexCell cell : g.getGrid().values()){
            for(Insect insect : cell.getInsects()){
                if(insect.getPlayer() == player && insect.getClass() == Bee.class){
                    return true;
                }
            }
        }
        System.out.println("Aucun déplacement autorisé car l'abeille n'est pas sur le plateau");
        return false;
    }
}