package model.players;

import model.enums.Move;
import model.gameobjects.Rovar;
import model.grid.Grid;
import model.grid.TektonElem;

public class Rovarasz extends Jatekos {
    int szerzettTapanyag;
    Rovar rovar;

    public Rovarasz(TektonElem grid, String nev) {
        super(nev);
        rovar=new Rovar(grid);
        grid.hozzaAd(rovar);
        szerzettTapanyag=0;
    }

    public Rovar getRovar(){return rovar;}

    public Rovarasz(Rovar r){
        super("");
        rovar = r;}
    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) {
        // TODO
    }
}
