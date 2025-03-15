package model.players;

import interfaces.IRovarasz;
import model.enums.Move;
import model.gameobjects.Rovar;
import model.grid.Grid;

public class Rovarasz extends Jatekos implements IRovarasz {
    int szerzettTapanyag;
    Rovar rovar;

    public Rovarasz(Rovar r){rovar = r;}
    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) {
        // TODO
    }
}
