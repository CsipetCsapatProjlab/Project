package model.players;

import model.enums.Move;
import model.exceptions.IncompatibleGameObjectException;
import model.exceptions.InvalidMoveException;
import model.grid.Grid;

public abstract class Jatekos {
    String nev;
    public static int jatekosokSzama = 0;
    public int meik;
    /**
     * Letrehozza a jatekost
     * @param nev
     */
    public Jatekos(String nev) {
        this.nev = nev;
        meik = jatekosokSzama;
        jatekosokSzama++;
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
    public abstract void lepes(Grid kezdo, Grid cel, Move move) throws IncompatibleGameObjectException, InvalidMoveException;

    /**
     * Jatekos megszuntetese
     */
    public void meghal() {
        // TODO
    }

    public abstract int getPoints();

    @Override
    public String toString() {
        return nev + ": " + getClass().getSimpleName() + "; " + getPoints();
    }

    public String mentes(){
        return nev + ";" + Integer.toString(meik);
    }
}
