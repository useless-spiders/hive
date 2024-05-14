package Modele.Ia;

import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import Controleur.Game;
import Modele.Player;
import Modele.Insect.Insect;
import Modele.Move;
import Structures.HexCoordinate;

public class IaAleatoire extends Ia
{
    
    Random r;
    public IaAleatoire(Game g, Player p)
    {
        this.Grid = g.getGrid();
        this.r = new Random();
        this.us = p;
        initPieces();
    }

    @Override
    void joue() 
	{
        if(this.us.getTurn() <= 1)
        {
            if(this.Grid.getGrid().isEmpty())
            {
                
            }
        }
        else
        {
            ArrayList<Move> possibleActions = new ArrayList<>();
            ArrayList<HexCoordinate> possibleMoves = new ArrayList<>();
            Set<HexCoordinate> coordinates = this.Grid.getGrid().keySet();
            for(HexCoordinate h : coordinates)
            {
                Insect ins = this.Grid.getCell(h).getTopInsect();
                if(ins.getPlayer()==this.us)
                {
                    possibleMoves = ins.getPossibleMovesCells(h.getX(), h.getY(), this.Grid);
                    for(HexCoordinate x : possibleMoves)
                    {
                        possibleActions.add(new Move(ins, h, x));
                    }
                }
            }
        }
	}
}
