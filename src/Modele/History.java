package Modele;


import java.util.Stack;

public class History {

    private Stack<Move> history;
    private Stack<Move> redo;

    public History() {
        this.history = new Stack<>();
        this.redo = new Stack<>();
    }

    public void addMove(Move move) {
        history.push(move);
        redo.clear();
    }

    public Move cancelMove() {
        if (canCancel()) {
            Move move = history.pop();
            redo.push(move);
            return move;
        }
        return null;
    }

    public Stack<Move> getHistory() {
        return history;
    }

    public boolean canCancel() {
        return !history.isEmpty();
    }

    public boolean canRedo() {
        return !redo.isEmpty();
    }

    public Move redoMove() {
        if (canRedo()) {
            Move move = redo.pop();
            history.push(move);
            return move;
        }
        return null;
    }
}
