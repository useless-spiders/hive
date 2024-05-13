package Modele;


import java.util.Stack;

public class History {

    private Stack<Move> history;
    private Stack<Move> redo;

    public History() {
        this.history = new Stack<>();
        this.redo = new Stack<>();
    }

    public void addAction(Move move) {
        history.push(move);
        redo.clear();
    }

    public Move cancelAction() {
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

    public Move redoAction() {
        if (canRedo()) {
            Move move = redo.pop();
            history.push(move);
            return move;
        }
        return null;
    }
}
