package Model.Ai;

import Global.Configuration;
import Model.HexGrid;
import Model.Move;
import Model.Player;
import Model.Insect.Bee;
import Pattern.GameActionHandler;
import Structure.Log;
import Structure.Node;
import Structure.Tree;

import java.util.List;
import java.util.Random;

public class Ai4 extends Ai {

    Player other;
    int simulations;

    public Ai4(GameActionHandler gameActionHandler, Player p) {
        this.gameActionHandler = gameActionHandler;
        this.aiPlayer = p;
        if (this.gameActionHandler.getPlayerController().getPlayer1().equals(aiPlayer)) {
            this.other = this.gameActionHandler.getPlayerController().getPlayer2();
        } else {
            this.other = this.gameActionHandler.getPlayerController().getPlayer1();
        }
    }

    double heuristic(HexGrid g) {
        double result = 0;
        result -= beeNeighbors(this.aiPlayer, g) * 0.9;
        result += beeNeighbors(this.other, g) * 0.9;
        result += insectsCount(this.aiPlayer, g) * 0.1;
        result -= insectsCount(this.other, g) * 0.1;
        result += insectsBlock(aiPlayer, g) * 0.2;
        result += insectFree(aiPlayer, g)*0.01;
        result += isWin(this.aiPlayer, g);
        result -= isWin(this.other, g);
        return result;
    }

    double ucb1(Node node, int totalSimulations) {
        if (node.getVisitCount() == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return node.getValue() / node.getVisitCount() + Math.sqrt(2 * Math.log(totalSimulations) / node.getVisitCount());
    }

    Node select(Node root) {
        Node node = root;
        while (!node.getChilds().isEmpty()) {
            node = node.getChilds().stream().max((a, b) -> Double.compare(ucb1(a, root.getVisitCount()) + heuristic(a.getState())*10,ucb1(b, root.getVisitCount()) + heuristic(b.getState())*10)).get();
        }
        return node;
    }

    void expand(Node node, HexGrid grid, Player currentPlayer) {
        List<Move> moves = this.gameActionHandler.getMoveController().getMoves(grid, currentPlayer);
        for (Move move : moves) {
            HexGrid newState = grid.clone();
            if(!(move.getInsect() instanceof Bee && currentPlayer.getTurn() == 1)){
                newState.applyMove(move, currentPlayer.clone());
                Node childNode = new Node(move, newState);
                node.newChild(childNode);
            }
        }
    }

    double simulate(HexGrid grid, Player currentPlayer) {
        HexGrid gridCopy = grid.clone();
        Player usC = this.aiPlayer.clone();
        Player themC = this.other.clone();
        Player currentPlayerC = currentPlayer.clone();
        Random random = new Random();

        while (!gridCopy.checkLoser(usC) && !gridCopy.checkLoser(themC)) {
            List<Move> moves = this.gameActionHandler.getMoveController().getMoves(gridCopy, currentPlayerC);
            if (moves.isEmpty()) {
                break;
            }
            Move move = moves.get(random.nextInt(moves.size()));
            gridCopy.applyMove(move, currentPlayerC);
            currentPlayerC = (currentPlayerC.equals(usC)) ? themC : usC;
            currentPlayer = currentPlayerC.clone();
        }

        if (gridCopy.checkLoser(usC)) {
            return -1;
        } else if (gridCopy.checkLoser(themC)) {
            return 1;
        } else {
            return heuristic(gridCopy);
        }
    }

    void backpropagate(Node node, double result) {
        while (node != null) {
            node.incrementVisitCount();
            node.addValue(result);
            node = node.getParent();
        }
    }

    public Move chooseMove() {
        Tree tree = new Tree();
        Node root = new Node(null, this.gameActionHandler.getGrid().clone());
        tree.setRoot(root);
        this.simulations = 0;

        long endTime = System.currentTimeMillis() + Configuration.AI_TIME_LIMIT_MS;

        while (System.currentTimeMillis() < endTime) {
            Node selectedNode = select(root);
            expand(selectedNode, selectedNode.getState(), this.aiPlayer);
            for (Node child : selectedNode.getChilds()) {
                HexGrid gridSim = child.getState().clone();
                double result = simulate(gridSim, this.aiPlayer);
                backpropagate(child, result);
                this.simulations++;
            }
        }

        Node bestNode = root.getChilds().stream().max((a, b) -> Double.compare(a.getValue(), b.getValue())).orElse(null);

        Log.addMessage(simulations + " simulations performed");

        return (bestNode != null) ? bestNode.getMove() : null;
    }
}
