package Model.Ia;

import java.util.Random;

import Controller.Game;

public class IaAleatoire extends Ia
{
    
    Random r;
    public IaAleatoire(Game g)
    {
        this.Grid = g.getGrid();
        this.r = new Random();
    }
}
