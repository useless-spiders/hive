package Model.Ai;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import Model.Player;
import Model.Insect.*;

import Model.Move;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;

public class AiRandom extends Ai {

    Random r;

    public AiRandom(GameActionHandler gameActionHandler, Player p) {
        this.grid = gameActionHandler.getGrid();
        this.history = gameActionHandler.getHistory();
        this.r = new Random();
        this.aiPlayer = p;
    }

    @Override
    public Move chooseMove() {
        if (this.aiPlayer.getTurn() == 4 && this.aiPlayer.getInsectCount(Bee.class) > 0) {
            ArrayList<HexCoordinate> possibleCells = new ArrayList<>();
            Insect insect = this.aiPlayer.getInsect(Bee.class);
            possibleCells = insect.getPossibleInsertionCoordinates(this.grid);
            HexCoordinate dest = possibleCells.get(r.nextInt(possibleCells.size()));
            return new Move(insect, null, dest);

        } else {
            ArrayList<Move> possibleMoves = new ArrayList<>();
            ArrayList<HexCoordinate> possibleCells = new ArrayList<>();
            if (this.aiPlayer.getTurn() <= 1) {
                Insect insect;
                HexCoordinate source = null;
                switch (r.nextInt(5)) {
                    case 0:
                        insect = this.aiPlayer.getInsect(Ant.class);
                        break;
                    case 1:
                        insect = this.aiPlayer.getInsect(Bee.class);
                        break;
                    case 2:
                        insect = this.aiPlayer.getInsect(Beetle.class);
                        break;
                    case 3:
                        insect = this.aiPlayer.getInsect(Grasshopper.class);
                        break;
                    default:
                        insect = this.aiPlayer.getInsect(Spider.class);
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
                    if (ins.getPlayer() == this.aiPlayer) {
                        possibleCells = ins.getPossibleMovesCoordinates(source, this.grid);
                        for (HexCoordinate dest : possibleCells) {
                            possibleMoves.add(new Move(ins, source, dest));
                        }
                    }
                }
                //////////////solution temporaire ///////////
                Spider zed = new Spider(this.aiPlayer);
                possibleCells = zed.getPossibleInsertionCoordinates(this.grid);
                /////////////////////////////////////////////

                HexCoordinate source = null;
                Insect insect;
                if (this.aiPlayer.canAddInsect(Ant.class)) {
                    insect = this.aiPlayer.getInsect(Ant.class);
                    for (HexCoordinate dest : possibleCells) {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.aiPlayer.canAddInsect(Bee.class)) {
                    insect = new Bee(this.aiPlayer);
                    for (HexCoordinate dest : possibleCells) {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.aiPlayer.canAddInsect(Beetle.class)) {
                    insect = new Beetle(this.aiPlayer);
                    for (HexCoordinate dest : possibleCells) {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.aiPlayer.canAddInsect(Grasshopper.class)) {
                    insect = new Grasshopper(this.aiPlayer);
                    for (HexCoordinate dest : possibleCells) {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }
                if (this.aiPlayer.canAddInsect(Spider.class)) {
                    insect = new Spider(this.aiPlayer);
                    for (HexCoordinate dest : possibleCells) {
                        possibleMoves.add(new Move(insect, source, dest));
                    }
                }

                return possibleMoves.get(r.nextInt(possibleMoves.size()));
            }
        }

    }
}
