package model.players;

import model.enums.Move;
import model.grid.Grid;

public abstract class Jatekos {
    String nev;

    public Jatekos(String nev) {
        this.nev = nev;
    }

    public String getNev() {
        return nev;
    }

    public abstract void lepes(Grid kezdo, Grid cel, Move move);
    public void meghal() {
        // TODO
    }
}
