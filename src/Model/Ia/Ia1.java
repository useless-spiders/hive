package Model.Ia;

import java.util.ArrayList;
import java.util.Random;

import Controller.Game;
import Model.HexCell;
import Model.HexGrid;
import Model.Move;
import Model.Player;
import Model.Insect.Ant;
import Model.Insect.Bee;
import Model.Insect.Beetle;
import Model.Insect.Grasshopper;
import Model.Insect.Insect;
import Model.Insect.Spider;
import Structure.HexCoordinate;
import Structure.Log;

public class Ia1 extends Ia {

    ArrayList<Class<? extends Insect>> insectclass;
    Player other;

    public Ia1(Game g, Player p) {
        this.grid = g.getGrid();
        initInsectClass();
        this.us = p;
        if(g.getPlayer1() == us){
            this.other = g.getPlayer2();
        }
        else{
            this.other = g.getPlayer1();
        }
    }

    private void initInsectClass(){
        insectclass = new ArrayList<>();
        insectclass.add(Bee.class);
        insectclass.add(Beetle.class);
        insectclass.add(Ant.class);
        insectclass.add(Spider.class);
        insectclass.add(Grasshopper.class);
    }


    int Heuristique(HexGrid g) {
        int result = 0;
        for(HexCoordinate h : g.getGrid().keySet()){
            HexCell cell = g.getCell(h);
            Insect insect = cell.getTopInsect();

            if(insect instanceof Ant && insect.getPlayer() == this.us){
                result += 3;
            }
            if(insect instanceof Ant && insect.getPlayer() != this.us){
                result -= 3;
            }

            if(insect instanceof Beetle && insect.getPlayer() == this.us){
                result += 2;
            }
            if(insect instanceof Beetle && insect.getPlayer() != this.us){
                result -= 2;
            }

            if(insect instanceof Grasshopper && insect.getPlayer() == this.us){
                result += 2;
            }
            if(insect instanceof Grasshopper && insect.getPlayer() != this.us){
                result -= 2;
            }

            if(insect instanceof Spider && insect.getPlayer() == this.us){
                result += 1;
            }
            if(insect instanceof Spider && insect.getPlayer() != this.us){
                result -= 1;
            }
            if(insect instanceof Bee){
                if(insect.getPlayer() == this.us){
                    result -= g.getNeighborsCoordinates(h).size()*20;
                }
                else{
                    result += g.getNeighborsCoordinates(h).size()*20;
                }
            }
        }
        return result;
    }

    private ArrayList<Move> getMoves(Player p) {
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<HexCoordinate> possiblecells = new ArrayList<>();
        Insect insect;

        if (this.grid.getGrid().isEmpty()) { //1er tour, 1er joueur
            for (Class<? extends Insect> i : insectclass) {
                if (p.canAddInsect(i)) {
                    insect = p.getInsect(i);
                    moves.add(new Move(insect, null, new HexCoordinate(0, 0)));
                }
            }
            return moves;
        }

        if (p.getTurn() == 1 && !this.grid.getGrid().isEmpty()) { //1er tour, 2eme joueur
            for (Class<? extends Insect> i : insectclass) {
                if (p.canAddInsect(i)) {
                    insect = p.getInsect(i);
                    possiblecells = insect.getPossibleInsertionCoordinatesT1(grid);
                    for (HexCoordinate h : possiblecells) {
                        moves.add(new Move(insect, null, h));
                    }
                }
            }
            return moves;
        }

        if (!(p.isBeePlaced()) && p.getTurn() == 4) { //placements abeille obligatoire
            insect = p.getInsect(Bee.class);
            possiblecells = insect.getPossibleInsertionCoordinates(grid);
            for (HexCoordinate h : possiblecells) {
                moves.add(new Move(insect, null, h));
            }
            return moves;
        }

        if (!p.isBeePlaced()) { //placements uniquements
            for (Class<? extends Insect> i : insectclass) {
                if (p.canAddInsect(i)) {
                    insect = p.getInsect(i);
                    possiblecells = insect.getPossibleInsertionCoordinates(grid);
                    for (HexCoordinate h : possiblecells) {
                        moves.add(new Move(insect, null, h));
                    }
                }
            }
            return moves;
        }

        if (p.isBeePlaced()) { //abeille placé
            for (HexCoordinate h1 : grid.getGrid().keySet()) {
                if (grid.getCell(h1).getTopInsect().getPlayer() == p) {
                    insect = grid.getCell(h1).getTopInsect();
                    possiblecells = insect.getPossibleMovesCoordinates(h1, grid);
                    for (HexCoordinate h2 : possiblecells) {
                        moves.add(new Move(insect, h1, h2));
                    }
                }
            }

            for (Class<? extends Insect> i : insectclass) {
                if (p.canAddInsect(i)) {
                    insect = p.getInsect(i);
                    possiblecells = insect.getPossibleInsertionCoordinates(grid);
                    for (HexCoordinate h : possiblecells) {
                        moves.add(new Move(insect, null, h));
                    }
                }
            }
            return moves;
        }
        return moves;
    }

    Move chooseMove(ArrayList<Move> moves) {
        HexGrid g = this.grid.clone();
        ArrayList<Move> toPlay = new ArrayList<>();
        int score;
        int score_max = -999999;
        Random randomNumbers = new Random();
        Player us_c = this.us.clone();
        for (Move m : moves) {
            g.applyMove(m, us_c);
            score = Heuristique(g);
            if (score > score_max) {
                score_max = score;
                toPlay.clear();
                toPlay.add(m);
            }
            if(score == score_max){
                toPlay.add(m);
            }
            g.unapplyMove(m, us_c);
        }

        return toPlay.get(randomNumbers.nextInt(toPlay.size()));
    }

    @Override
    public void playMove() {
        Move move = chooseMove(getMoves(this.us));
        if(move == null){
            Log.addMessage("aucun coup jouable");
            return;
        }
        if(move.getInsect() instanceof Bee){
        }
        this.grid.applyMove(move, us);
    }
}
