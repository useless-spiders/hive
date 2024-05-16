package Modele.Ia;

import java.util.ArrayList;
import Controleur.Game;
import Modele.HexGrid;
import Modele.Move;
import Modele.Player;
import Modele.Insect.Ant;
import Modele.Insect.Bee;
import Modele.Insect.Beetle;
import Modele.Insect.Grasshopper;
import Modele.Insect.Insect;
import Modele.Insect.Spider;
import Structures.HexCoordinate;

public class Ia1 extends Ia{

    ArrayList<Class<? extends Insect>> insectclass;
    
    public Ia1(Game g, Player p)
    {
        this.Grid = g.getGrid();
        insectclass.add(Bee.class);
        insectclass.add(Beetle.class);
        insectclass.add(Ant.class);
        insectclass.add(Spider.class);
        insectclass.add(Grasshopper.class);
        this.us = p;
    }


    static int Heuristique(HexGrid g){
        return 1;
    }

    private ArrayList<Move> getMoves(){
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<HexCoordinate> possiblecells = new ArrayList<>();
        Insect insect;

        if(this.Grid.getGrid().isEmpty()){ //1er tour, 1er joueur
            for(Class<? extends Insect> i : insectclass){
                if(this.us.canAddInsect(i)){
                    insect = this.us.getInsect(i);
                    moves.add(new Move(insect,null,new HexCoordinate(0, 0)));
                }
            }
            return moves;
        }

        if(this.us.getTurn() == 1 && !this.Grid.getGrid().isEmpty()){ //1er tour, 2eme joueur
            for(Class<? extends Insect> i : insectclass){
                if(this.us.canAddInsect(i)){
                    insect = this.us.getInsect(i);
                    possiblecells = insect.getPossibleInsertionCoordinatesT1(Grid);
                    for(HexCoordinate h: possiblecells){
                        moves.add(new Move(insect,null,h));
                    }
                }
            }
            return moves;
        }

        if(!this.us.isBeePlaced() && this.us.getTurn() == 4){ //placements abeille obligatoire
            insect = this.us.getInsect(Bee.class);
            possiblecells = insect.getPossibleInsertionCoordinates(Grid);
            for(HexCoordinate h: possiblecells){
                moves.add(new Move(insect,null,h));
            }
            return moves;
        }

        if(!this.us.isBeePlaced()){ //placements uniquements
            for(Class<? extends Insect> i : insectclass){
                if(this.us.canAddInsect(i)){
                    insect = this.us.getInsect(i);
                    possiblecells = insect.getPossibleInsertionCoordinates(Grid);
                    for(HexCoordinate h: possiblecells){
                        moves.add(new Move(insect,null,h));
                    }
                }
            }
            return moves;
        }

        if(this.us.isBeePlaced()){ //abeille placé
            for(HexCoordinate h1 : Grid.getGrid().keySet()){
                if(Grid.getCell(h1).getTopInsect().getPlayer() == this.us){
                    insect = Grid.getCell(h1).getTopInsect();
                    possiblecells = insect.getPossibleMovesCoordinates(h1, Grid);
                    for(HexCoordinate h2: possiblecells){
                        moves.add(new Move(insect,h1,h2));
                    }
                }
            }

            for(Class<? extends Insect> i : insectclass){
                if(this.us.canAddInsect(i)){
                    insect = this.us.getInsect(i);
                    possiblecells = insect.getPossibleInsertionCoordinates(Grid);
                    for(HexCoordinate h: possiblecells){
                        moves.add(new Move(insect,null,h));
                    }
                }
            }
            return moves;
        }
        return null;
    }

    Move chooseMove(ArrayList<Move> moves){
        HexGrid g = this.Grid.clone();
        Move toPlay = null;
        int score;
        int score_max = 0;
        for(Move m : moves){
            g.applyMove(m, us);
            score = Heuristique(g);
            if(score > score_max){
                score_max = score;
                toPlay = m;
            }
            g.unapplyMove(m, us);
        }
        return toPlay;
    }

    @Override
    public void playMove(){
        Move move = chooseMove(getMoves());
    }
}
