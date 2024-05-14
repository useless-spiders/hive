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
                for(HexCoordinate dest : possibleCells)
                {
                    possibleMoves.add(new Move(insect, source, dest));
                }
            }
            if (this.us.canAddInsect(Bee.class))
            {
                for(HexCoordinate dest : possibleCells)
                {
                    possibleMoves.add(new Move(insect, source, dest));
                }
            }
            if (this.us.canAddInsect(Beetle.class))
            {
                for(HexCoordinate dest : possibleCells)
                {
                    possibleMoves.add(new Move(insect, source, dest));
                }
            }
            if (this.us.canAddInsect(Grasshopper.class))
            {
                for(HexCoordinate dest : possibleCells)
                {
                    possibleMoves.add(new Move(insect, source, dest));
                }
            }
            if (this.us.canAddInsect(Spider.class))
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
            if(this.Grid.getCell(from) != null){
                this.Grid.getCell(from).removeTopInsect();
            }
            if(this.Grid.getCell(from) == null || this.Grid.getCell(from).getInsects().isEmpty()){
                this.Grid.removeCell(from);
            }
        }

        if (this.Grid.getCell(to) != null)
        {
            this.Grid.getCell(to).addInsect(moveToPlay.getInsect());
        } else 
        {
            this.Grid.addCell(to, moveToPlay.getInsect());
        }
    }
}
