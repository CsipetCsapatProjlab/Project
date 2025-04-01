package model.players;

import model.enums.Move;
import model.gameobjects.Rovar;
import model.grid.Grid;

public class Rovarasz extends Jatekos {
    int szerzettTapanyag;
    Rovar rovar;

    public Rovarasz(Grid grid, String nev) {
        super(nev);
        rovar=new Rovar(grid);
        szerzettTapanyag=0;
    }

    public Rovarasz(Rovar r){
        super("");
        rovar = r;}
    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) {
        // TODO
    }
}
