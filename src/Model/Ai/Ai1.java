package Model.Ai;

import java.util.ArrayList;
import java.util.Random;

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
import Pattern.GameActionHandler;
import Structure.HexCoordinate;

public class Ai1 extends Ai {

    Player other;

    public Ai1(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayer1() == aiPlayer) {
            this.other = this.gameActionHandler.getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayer1();
        }
    }


    int Heuristique(HexGrid g) {
        int result = 0;
        for (HexCoordinate h : g.getGrid().keySet()) {
            HexCell cell = g.getCell(h);
            Insect insect = cell.getTopInsect();

            if (insect instanceof Ant && insect.getPlayer() == this.aiPlayer) {
                result += 3;
            }
            if (insect instanceof Ant && insect.getPlayer() != this.aiPlayer) {
                result -= 3;
            }

            if (insect instanceof Beetle && insect.getPlayer() == this.aiPlayer) {
                result += 2;
            }
            if (insect instanceof Beetle && insect.getPlayer() != this.aiPlayer) {
                result -= 2;
            }

            if (insect instanceof Grasshopper && insect.getPlayer() == this.aiPlayer) {
                result += 2;
            }
            if (insect instanceof Grasshopper && insect.getPlayer() != this.aiPlayer) {
                result -= 2;
            }

            if (insect instanceof Spider && insect.getPlayer() == this.aiPlayer) {
                result += 1;
            }
            if (insect instanceof Spider && insect.getPlayer() != this.aiPlayer) {
                result -= 1;
            }
            if (insect instanceof Bee) {
                if (insect.getPlayer() == this.aiPlayer) {
                    result -= g.getNeighborsCoordinates(h).size() * 20;
                } else {
                    result += g.getNeighborsCoordinates(h).size() * 20;
                }
            }
        }
        return result;
    }

    public Move chooseMove() {
        HexGrid g = this.gameActionHandler.getGrid().clone();
        ArrayList<Move> toPlay = new ArrayList<>();
        int score;
        int score_max = -999999;
        Random randomNumbers = new Random();
        Player us_c = this.aiPlayer.clone();
        for (Move m : getMoves(g, this.aiPlayer)) {
            g.applyMove(m, us_c);
            score = Heuristique(g);
            if (score > score_max) {
                score_max = score;
                toPlay.clear();
                toPlay.add(m);
            }
            if (score == score_max) {
                toPlay.add(m);
            }
            g.unapplyMove(m, us_c);
        }
        if (toPlay.isEmpty()) {
            return null;
        }

        return toPlay.get(randomNumbers.nextInt(toPlay.size()));
    }

}
