package model.players;

import model.enums.Move;
import model.exceptions.FailedMoveException;
import model.exceptions.InvalidMoveException;
import model.grid.Grid;

public abstract class Jatekos {
    String nev;
    public static int jatekosokSzama = 0;
    public int melyik;

    public Jatekos(String nev) {
        this.nev = nev;
        melyik = jatekosokSzama;
        jatekosokSzama++;
    }

    public String getNev() {
        return nev;
    }

    /**
     * Implementalando lepes
     * @param kezdo Honnan
     * @param cel Hova
     * @param move Milyen modon
     */
    public abstract void lepes(Grid kezdo, Grid cel, Move move) throws InvalidMoveException, FailedMoveException;

    /**
     * Jatekos megszuntetese
     */
    public void meghal() {}

    public abstract int getPoints();

    @Override
    public String toString() {
        return nev + ": " + getClass().getSimpleName() + "; " + getPoints();
    }

    public String mentes(){
        return nev + ";" + melyik;
    }
}
