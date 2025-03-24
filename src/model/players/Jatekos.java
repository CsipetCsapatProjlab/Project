package model.players;

import model.enums.Move;
import model.grid.Grid;

public abstract class Jatekos {
    String nev;

    /**
     * Letrehozza a jatekost
     * @param nev
     */
    public Jatekos(String nev) {
        this.nev = nev;
    }

    /**
     * Nev beallitasa
     * @return
     */
    public String getNev() {
        return nev;
    }

    /**
     * Implementalando lepes
     * @param kezdo Honnan
     * @param cel Hova
     * @param move Milyen modon
     */
    public abstract void lepes(Grid kezdo, Grid cel, Move move);

    /**
     * Jatekos megszuntetese
     */
    public void meghal() {
        // TODO
    }
}
