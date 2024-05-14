package Modele.Ia;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import Controleur.Game;
import Modele.Player;
import Modele.Insect.*;

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
    }

    @Override
    Move chooseMove() 
	{
        ArrayList<Move> possibleMoves = new ArrayList<>();
        ArrayList<HexCoordinate> possibleCells = new ArrayList<>();
        if(this.us.getTurn() <= 1)
        {
            Insect insect;
            HexCoordinate source = null;
            switch(r.nextInt(5))
            {
                //TODO ca marche pas verifier les compteurs des insectes
                case 0:
                    insect = new Ant(this.us);
                    break;
                case 1:
                    insect = new Bee(this.us);
                    break;
                case 2:
                    insect = new Beetle(this.us);
                    break;
                case 3:
                    insect = new Grasshopper(this.us);
                    break;
                default:
                    insect = new Spider(this.us);
                    break;
            }
            if(this.Grid.getGrid().isEmpty())
            {
                return new Move(insect, source, new HexCoordinate(0, 0));
            }
            else
            {
                //////////////solution temporaire ///////////
                Spider zed = new Spider(this.us);

                possibleCells = zed.getPossibleInsertionCellT1(this.Grid);
                /////////////////////////////////////////////
                HexCoordinate dest = possibleCells.get(r.nextInt(possibleCells.size()));
                return new Move(insect, source, dest);
            }
        }
        else
        {

            Set<HexCoordinate> coordinates = this.Grid.getGrid().keySet();
            for(HexCoordinate source : coordinates)
            {
                Insect ins = this.Grid.getCell(source).getTopInsect();
                if(ins.getPlayer()==this.us)
                {
                    possibleCells = ins.getPossibleMovesCells(source.getX(), source.getY(), this.Grid);
                    for(HexCoordinate dest : possibleCells)
                    {
                        possibleMoves.add(new Move(ins, source, dest));
                    }
                }
            }
            //////////////solution temporaire ///////////
            Spider zed = new Spider(this.us);
            possibleCells = zed.getPossibleInsertionCells(this.Grid);
            /////////////////////////////////////////////

            HexCoordinate source = null;
            Insect insect;
            if (this.us.canAddInsect(insect = new Ant(this.us))) 
            {
                for(HexCoordinate dest : possibleCells)
                {
                    possibleMoves.add(new Move(insect, source, dest));
                }
            }
            if (this.us.canAddInsect(insect = new Bee(this.us))) 
            {
                for(HexCoordinate dest : possibleCells)
                {
                    possibleMoves.add(new Move(insect, source, dest));
                }
            }
            if (this.us.canAddInsect(insect = new Beetle(this.us))) 
            {
                for(HexCoordinate dest : possibleCells)
                {
                    possibleMoves.add(new Move(insect, source, dest));
                }
            }
            if (this.us.canAddInsect(insect = new Grasshopper(this.us))) 
            {
                for(HexCoordinate dest : possibleCells)
                {
                    possibleMoves.add(new Move(insect, source, dest));
                }
            }
            if (this.us.canAddInsect(insect = new Spider(this.us))) 
            {
                for(HexCoordinate dest : possibleCells)
                {
                    possibleMoves.add(new Move(insect, source, dest));
                }
            }

            return possibleMoves.get(r.nextInt(possibleMoves.size()));
        }
	}

    @Override
    public void playMove() 
    {
        Move moveToPlay = chooseMove();
        HexCoordinate from = moveToPlay.getPreviousCoor();
        HexCoordinate to = moveToPlay.getNewCoor();
        if (from != null) 
        {
            if(this.Grid.getCell(from.getX(), from.getY()) != null){
                this.Grid.getCell(from.getX(), from.getY()).removeTopInsect();
            }
            if(this.Grid.getCell(from.getX(), from.getY()) == null || this.Grid.getCell(from.getX(), from.getY()).getInsects().isEmpty()){
                this.Grid.removeCell(from.getX(), from.getY());
            }
        }

        if (this.Grid.getCell(to.getX(), to.getY()) != null) 
        {
            this.Grid.getCell(to.getX(), to.getY()).addInsect(moveToPlay.getInsect());
        } else 
        {
            this.Grid.addCell(to.getX(), to.getY(), moveToPlay.getInsect());
        }
    }
}
