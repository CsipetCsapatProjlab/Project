package model.players;

import model.enums.Move;
import model.gameobjects.Rovar;
import model.grid.Grid;
import model.grid.TektonElem;

import java.util.ArrayList;
import java.util.List;

public class Rovarasz extends Jatekos {
    int szerzettTapanyag;
    List<Rovar> rovarok;

    /**
     * Letrehozza a rovaraszt
     * @param grid Rovaranak kezdo mezoje
     * @param nev Identifikacios nev
     */
    public Rovarasz(TektonElem grid, String nev) {
        super(nev);
        rovarok=new ArrayList<>();
        rovarok.add(new Rovar(grid,this));
        szerzettTapanyag=0;
    }

    /**
     * Visszaadja a rovarjat
     * @return
     */
    public List<Rovar> getRovarok(){return rovarok;}

    public Rovarasz(List<Rovar> r){
        super("");
        rovarok= r;}

    /**
     * Lepes a rovarral kezdo mezorol cel mezore, megadott modon
     */
    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) {
        // TODO
    }
}
