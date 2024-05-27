package Model;


import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

/**
 * Classe pour l'historique
 */
public class History implements Serializable {

    private Stack<Move> history;
    private Stack<Move> redo;

    /**
     * Constructeur
     */
    public History() {
        this.history = new Stack<>();
        this.redo = new Stack<>();
    }

    /**
     * Ajoute un mouvement à l'historique
     */
    public void addMove(Move move) {
        this.history.push(move);
        this.redo.clear();
    }

    /**
     * Annule le dernier mouvement
     * @return Move
     */
    public Move cancelMove() {
        if (this.canCancel()) {
            Move move = this.history.pop();
            this.redo.push(move);
            return move;
        }
        return null;
    }

    /**
     * Renvoie l'historique
     * @return Stack<Move>
     */
    public Stack<Move> getHistory() {
        return history;
    }

    /**
     * Vérifie si un mouvement peut être annulé
     * @return boolean
     */
    public boolean canCancel() {
        return !history.isEmpty();
    }

    /**
     * Vérifie si un mouvement peut être refait
     * @return boolean
     */
    public boolean canRedo() {
        return !redo.isEmpty();
    }

    /**
     * Refait le dernier mouvement annulé
     * @return Move
     */
    public Move redoMove() {
        if (this.canRedo()) {
            Move move = this.redo.pop();
            this.history.push(move);
            return move;
        }
        return null;
    }

    /**
     * Test si deux historiques sont égaux
     * @return boolean
     */
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

    /**
     * Renvoie le hashcode de l'historique
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.history, this.redo);
    }
}
