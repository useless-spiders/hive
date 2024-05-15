package Modele.Ia;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import Controleur.Game;
import Modele.Player;
import Modele.Insect.*;

import Modele.Move;
import Structures.HexCoordinate;
import Structures.Log;

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
        if(this.us.getTurn()==4 && this.us.getInsectCount(Bee.class)>0)
        {
            ArrayList<HexCoordinate> possibleCells = new ArrayList<>();
            Insect insect = this.us.getInsect(Bee.class);
            possibleCells = insect.getPossibleInsertionCells(this.Grid);
            HexCoordinate dest = possibleCells.get(r.nextInt(possibleCells.size()));
            return new Move(insect, null, dest);

        }
        else
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
                        insect = this.us.getInsect(Ant.class);
                        break;
                    case 1:
                        insect = this.us.getInsect(Bee.class);
                        break;
                    case 2:
                        insect = this.us.getInsect(Beetle.class);
                        break;
                    case 3:
                        insect = this.us.getInsect(Grasshopper.class);
                        break;
                    default:
                        insect = this.us.getInsect(Spider.class);
                        break;
                }
                if(this.Grid.getGrid().isEmpty())
                {
                    return new Move(insect, source, new HexCoordinate(0, 0));
                }
                else
                {
                    possibleCells = insect.getPossibleInsertionCellT1(this.Grid);
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
                        possibleCells = ins.getPossibleMovesCells(source, this.Grid);
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
                if (this.us.canAddInsect(Ant.class))
                {
                    insect = this.us.getInsect(Ant.class);
                    for(HexCoordinate dest : possibleCells)
                    {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.us.canAddInsect(Bee.class))
                {
                    insect = new Bee(this.us);
                    for(HexCoordinate dest : possibleCells)
                    {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.us.canAddInsect(Beetle.class))
                {
                    insect = new Beetle(this.us);
                    for(HexCoordinate dest : possibleCells)
                    {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.us.canAddInsect(Grasshopper.class))
                {
                    insect = new Grasshopper(this.us);
                    for(HexCoordinate dest : possibleCells)
                    {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.us.canAddInsect(Spider.class))
                {
                    insect = new Spider(this.us);
                    for(HexCoordinate dest : possibleCells)
                    {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
    
                return possibleMoves.get(r.nextInt(possibleMoves.size()));
            }
        }
        
	}

    @Override
    public void playMove() 
    {
        Move moveToPlay = chooseMove();
        HexCoordinate from = moveToPlay.getPreviousCoor();
        HexCoordinate to = moveToPlay.getNewCoor();
        if (from != null) //cas deplacement insecte
        {
            if(this.Grid.getCell(from) != null){
                this.Grid.getCell(from).removeTopInsect();
            }
            if(this.Grid.getCell(from) == null || this.Grid.getCell(from).getInsects().isEmpty()){
                this.Grid.removeCell(from);
            }
            if (this.Grid.getCell(to) != null)
            {
                this.Grid.getCell(to).addInsect(moveToPlay.getInsect());
            } else 
            {
                this.Grid.addCell(to, moveToPlay.getInsect());
            }
        }
        else//cas placement insecte
        {
            if (this.Grid.getCell(to) == null)
            {
                this.us.playInsect(moveToPlay.getInsect().getClass());
                this.Grid.addCell(to, moveToPlay.getInsect());
            }
            else
            {
                Log.addMessage("cas impossible on ne peux pas placer sur une case deja remplie");
            }
        }
    }
}
