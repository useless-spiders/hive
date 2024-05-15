package Modele.Insect;

import Modele.HexCell;
import Modele.HexGrid;
import Modele.Player;
import Structures.HexCoordinate;

import java.util.ArrayList;
import java.util.HashSet;

public class Spider extends Insect {


    public Spider(Player player) {
        super(player);
    }

    @Override
    public ArrayList<HexCoordinate> getPossibleMovesCells(HexCoordinate current, HexGrid g) {
        ArrayList<HexCoordinate> coordinates = new ArrayList<>();
        HashSet<HexCoordinate> visited = new HashSet<>();
        if (this.canMoveInsect(g, this.getPlayer())) {
            this.getPossibleMovesCellsHelper(current, g, 3, coordinates, current, visited);
        }
        return coordinates;
    }

    private void getPossibleMovesCellsHelper(HexCoordinate current, HexGrid g, int steps, ArrayList<HexCoordinate> coordinates, HexCoordinate original, HashSet<HexCoordinate> visited) {
        int x = current.getX();
        int y = current.getY();
        if (steps == 0 && !current.equals(original)) {
            coordinates.add(current);
            return;
        }

        if (steps > 0) {
            String[] directions = {"NO", "NE", "E", "SE", "SO", "O"};
            int[] dx = {0, 1, 1, 0, -1, -1};
            int[] dy = {-1, -1, 0, 1, 1, 0};
            

            for (int i = 0; i < directions.length; i++) {
                HexCoordinate next = new HexCoordinate(x + dx[i], y + dy[i]);
                if (!visited.contains(next) && g.getCell(g.getNeighbor(current, directions[i])) == null && g.isHiveConnectedAfterMove(original, next)) {
                    //il faut qu il y ait un cote non vide pour glisser et un cote non vide sinon on va dans un trou
                    String dir = directions[((((i - 1) % directions.length) + directions.length) % directions.length)];
                    HexCell adj = g.getCell(g.getNeighbor(current, dir));
                    HexCell adj2 = g.getCell(g.getNeighbor(current, directions[((i + 1) % directions.length)]));

                    if(original.getX() == x + dx[((i-1)+dx.length)%dx.length] & original.getY() == y + dy[((i-1)+dy.length)%dy.length]){
                        adj = null;
                    }
                    if(original.getX() == x + dx[(i+1)%dx.length] & original.getY() == y + dy[(i+1)%dy.length]){
                        adj2 = null;
                    }

                    if ((adj == null && adj2 != null) || (adj != null && adj2 == null)) {
                        visited.add(next);
                        this.getPossibleMovesCellsHelper(next, g, steps - 1, coordinates, original, visited);
                    }
                }
            }
        }
    }

}