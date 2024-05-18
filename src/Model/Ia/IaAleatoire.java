package Model.Ia;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import Controller.Game;
import Model.Player;
import Model.Insect.*;

import Model.Move;
import Structure.HexCoordinate;

public class IaAleatoire extends Ia {

    Random r;

    public IaAleatoire(Game g, Player p) {
        this.grid = g.getGrid();
        this.history = g.getHistory();
        this.r = new Random();
        this.us = p;
    }

    @Override
    public Move chooseMove() {
        if (this.us.getTurn() == 4 && this.us.getInsectCount(Bee.class) > 0) {
            ArrayList<HexCoordinate> possibleCells = new ArrayList<>();
            Insect insect = this.us.getInsect(Bee.class);
            possibleCells = insect.getPossibleInsertionCoordinates(this.grid);
            HexCoordinate dest = possibleCells.get(r.nextInt(possibleCells.size()));
            return new Move(insect, null, dest);

        } else {
            ArrayList<Move> possibleMoves = new ArrayList<>();
            ArrayList<HexCoordinate> possibleCells = new ArrayList<>();
            if (this.us.getTurn() <= 1) {
                Insect insect;
                HexCoordinate source = null;
                switch (r.nextInt(5)) {
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
                if (this.grid.getGrid().isEmpty()) {
                    //TODO placer la premiere piece au milieu de l ecran
                    return new Move(insect, source, new HexCoordinate(0, 0));
                } else {
                    possibleCells = insect.getPossibleInsertionCoordinatesT1(this.grid);
                    HexCoordinate dest = possibleCells.get(r.nextInt(possibleCells.size()));
                    return new Move(insect, source, dest);
                }
            } else {

                Set<HexCoordinate> coordinates = this.grid.getGrid().keySet();
                for (HexCoordinate source : coordinates) {
                    Insect ins = this.grid.getCell(source).getTopInsect();
                    if (ins.getPlayer() == this.us) {
                        possibleCells = ins.getPossibleMovesCoordinates(source, this.grid);
                        for (HexCoordinate dest : possibleCells) {
                            possibleMoves.add(new Move(ins, source, dest));
                        }
                    }
                }
                //////////////solution temporaire ///////////
                Spider zed = new Spider(this.us);
                possibleCells = zed.getPossibleInsertionCoordinates(this.grid);
                /////////////////////////////////////////////

                HexCoordinate source = null;
                Insect insect;
                if (this.us.canAddInsect(Ant.class)) {
                    insect = this.us.getInsect(Ant.class);
                    for (HexCoordinate dest : possibleCells) {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.us.canAddInsect(Bee.class)) {
                    insect = new Bee(this.us);
                    for (HexCoordinate dest : possibleCells) {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.us.canAddInsect(Beetle.class)) {
                    insect = new Beetle(this.us);
                    for (HexCoordinate dest : possibleCells) {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.us.canAddInsect(Grasshopper.class)) {
                    insect = new Grasshopper(this.us);
                    for (HexCoordinate dest : possibleCells) {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.us.canAddInsect(Spider.class)) {
                    insect = new Spider(this.us);
                    for (HexCoordinate dest : possibleCells) {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }

                return possibleMoves.get(r.nextInt(possibleMoves.size()));
            }
        }

    }
}
