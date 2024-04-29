package Modele;

public class Case {
    final private int x;
    final private int y;
    private boolean estPoison;
    private boolean estMange;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.estMange = false;
        this.estPoison = false;
    }

    public void mangerCase() {
        if (!this.estMange) {
            this.estMange = true;
        }
    }

    public void empoisonnerCase() {
        this.estPoison = true;
    }

    public void resetCase(){
        this.estMange = false;
        this.estPoison = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getEstMange() {
        return this.estMange;
    }

    public boolean getEstPoisson() {
        return this.estPoison;
    }

    @Override
    public String toString() {
        return "Case{" +
                "x=" + x +
                ", y=" + y +
                ", estPoison=" + estPoison +
                ", estMange=" + estMange +
                '}';
    }
}
