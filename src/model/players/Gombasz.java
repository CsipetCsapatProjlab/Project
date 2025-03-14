package model.players;

import interfaces.IRovarasz;
import model.enums.Move;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Spora;
import model.grid.Grid;

public class Gombasz extends Jatekos implements IRovarasz {
    int kinottGombatest;
    Spora[] sporak;
    Fonal[] fonalak;
    GombaTest[] gombaTestek;

    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) {
        // TODO
    }
}
