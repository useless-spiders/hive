package Model.Ai;

import Global.Configuration;
import Model.HexGrid;
import Model.Move;
import Model.Player;
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
            node = node.getChilds().stream()
                    .max((a, b) -> Double.compare(
                            ucb1(a, root.getVisitCount()) + heuristic(a.getState()),
                            ucb1(b, root.getVisitCount()) + heuristic(b.getState())
                    )).get();
        }
        return node;
    }

    void expand(Node node, HexGrid grid, Player currentPlayer) {
        List<Move> moves = this.gameActionHandler.getMoveController().getMoves(grid, currentPlayer);
        for (Move move : moves) {
            HexGrid newState = grid.clone();
            newState.applyMove(move, currentPlayer);
            Node childNode = new Node(move, newState);
            node.newChild(childNode);
        }
    }

    double simulate(HexGrid grid, Player currentPlayer) {
        HexGrid gridCopy = grid.clone();
        Player usC = this.aiPlayer.clone();
        Player themC = this.other.clone();
        Random random = new Random();

        while (!gridCopy.checkLoser(usC) && !gridCopy.checkLoser(themC)) {
            List<Move> moves = this.gameActionHandler.getMoveController().getMoves(gridCopy, usC);
            if (moves.isEmpty()) {
                Log.addMessage("pas de move possible");
                break;
            }
            Move move = moves.get(random.nextInt(moves.size()));
            gridCopy.applyMove(move, usC);
            currentPlayer = (currentPlayer.equals(usC)) ? themC : usC;
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
        Player usC = this.aiPlayer.clone();

        long endTime = System.currentTimeMillis() + Configuration.AI_TIME_LIMIT_MS;

        while (System.currentTimeMillis() < endTime) {
            Node selectedNode = select(root);
            expand(selectedNode, selectedNode.getState(), usC);
            for (Node child : selectedNode.getChilds()) {
                HexGrid gridSim = child.getState().clone();
                double result = simulate(gridSim, usC);
                backpropagate(child, result);
                this.simulations++;
            }
        }

        Node bestNode = root.getChilds().stream().max((a, b) -> Double.compare(a.getValue(), b.getValue())).orElse(null);

        Log.addMessage(simulations + " simulations performed");

        return (bestNode != null) ? bestNode.getMove() : null;
    }
}
