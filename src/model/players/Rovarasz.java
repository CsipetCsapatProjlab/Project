package model.players;

import model.enums.Move;
import model.gameobjects.Rovar;
import model.grid.Grid;
import model.grid.TektonElem;

public class Rovarasz extends Jatekos {
    int szerzettTapanyag;
    Rovar rovar;

    /**
     * Letrehozza a rovaraszt
     * @param grid Rovaranak kezdo mezoje
     * @param nev Identifikacios nev
     */
    public Rovarasz(TektonElem grid, String nev) {
        super(nev);
        rovar=new Rovar(grid, this);
        grid.hozzaAd(rovar);
        szerzettTapanyag=0;
    }

    /**
     * Visszaadja a rovarjat
     * @return
     */
    public Rovar getRovar(){return rovar;}

    public Rovarasz(Rovar r){
        super("");
        rovar = r;}

    /**
     * Lepes a rovarral kezdo mezorol cel mezore, megadott modon
     */
    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) {
        // TODO
    }
}
