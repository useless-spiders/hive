package Modele;


import java.util.Stack;

public class History {

    private Stack<Action> history;
    private Stack<Action> redo;

    public History() {
        this.history = new Stack<>();
        this.redo = new Stack<>();
    }

    public void addAction(Action action) {
        history.push(action);
        redo.clear();
    }

    public Action cancelAction() {
        if (canCancel()) {
            Action action = history.pop();
            redo.push(action);
            return action;
        }
        return null;
    }

    public Stack<Action> getHistory() {
        return history;
    }

    public boolean canCancel() {
        return !history.isEmpty();
    }

    public boolean canRedo() {
        return !redo.isEmpty();
    }

    public Action redoAction() {
        if (canRedo()) {
            Action action = redo.pop();
            history.push(action);
            return action;
        }
        return null;
    }
}
