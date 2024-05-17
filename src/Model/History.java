package Model;


import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

public class History implements Serializable {

    private Stack<Move> history;
    private Stack<Move> redo;

    public History() {
        this.history = new Stack<>();
        this.redo = new Stack<>();
    }

    public void addMove(Move move) {
        this.history.push(move);
        this.redo.clear();
    }

    public Move cancelMove() {
        if (this.canCancel()) {
            Move move = this.history.pop();
            this.redo.push(move);
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
        if (this.canRedo()) {
            Move move = this.redo.pop();
            this.history.push(move);
            return move;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        History history = (History) obj;
        return Objects.equals(this.history, history.history) && Objects.equals(this.redo, history.redo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.history, this.redo);
    }
}
