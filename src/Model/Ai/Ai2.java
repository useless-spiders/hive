package Model.Ai;

import java.util.ArrayList;
import java.util.Random;

import Model.HexCell;
import Model.HexGrid;
import Model.Move;
import Model.Player;
import Model.Tree;
import Model.Node;
import Model.Insect.Ant;
import Model.Insect.Bee;
import Model.Insect.Beetle;
import Model.Insect.Grasshopper;
import Model.Insect.Insect;
import Model.Insect.Spider;
import Pattern.GameActionHandler;
import Structure.HexCoordinate;
import Structure.Log;

public class Ai2 extends Ai
{

    Player other;
    private Tree config;

    public Ai2(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayer1() == aiPlayer) {
            this.other = this.gameActionHandler.getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayer1();
        }
    }


    int heuristique(HexGrid g) {
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

    int maxTree(Node n, HexGrid g, int level)
    {
        Log.addMessage(level + "!");
        if(level >= 2)
        {
            Player us_c = this.aiPlayer.clone();
            HexGrid gridC = g.clone();
            Log.addMessage("move : " + n.getMove());
            if(n.getMove() == null)
            {
                Log.addMessage("message null");
            }
            gridC.applyMove(n.getMove(), us_c);
            int heuristique = heuristique(gridC);
            n.setValue(heuristique);
            return heuristique;
        }
        else
        {
            int max = -9999;
            level++;
            for (Move m : getMoves(this.aiPlayer)) 
            {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                Player us_c = this.aiPlayer.clone();
                HexGrid gridC = g.clone();
                gridC.applyMove(m, us_c);
                int currentH = minTree(nextMove, gridC, level);
                if(currentH  > max)
                {
                    max = currentH;
                }
            }
            n.setValue(max);
            return max;
        }

    }

    int minTree(Node n, HexGrid g, int level)
    {
        Log.addMessage(level + "!");
        if(level >= 2)
        {
            Player us_c = this.aiPlayer.clone();
            HexGrid gridC = g.clone();
            gridC.applyMove(n.getMove(), us_c);
            int heuristique = heuristique(gridC);
            n.setValue(heuristique);
            return heuristique;
        }
        else
        {
            int min = 9999;
            level++;
            for (Move m : getMoves(this.aiPlayer)) 
            {
                Node nextMove = new Node(m);
                n.newChild(nextMove);
                Player us_c = this.aiPlayer.clone();
                HexGrid gridC = g.clone();
                gridC.applyMove(m, us_c);
                int currentH = maxTree(nextMove, gridC, level);
                if(currentH < min)
                {
                    min = currentH;
                }
            }
            n.setValue(min);
            return min;
        }

    }


    public Move chooseMove() 
    {
        this.config = new Tree();
        maxTree(this.config.getCurrent(),this.gameActionHandler.getGrid(), 0);
        int max = -9999;
        Move returnMove = null;
        for(Node child : this.config.getCurrent().getChilds())
        {
            if(child.getValue() > max)
            {
                max = child.getValue();
                returnMove = child.getMove();
            }
        }
        return returnMove;
    }
    
}
