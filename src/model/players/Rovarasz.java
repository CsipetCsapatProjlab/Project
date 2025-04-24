package model.players;

import model.enums.Move;
import model.exceptions.IncompatibleGameObjectException;
import model.exceptions.InvalidMoveException;
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

    public void hozzaAd(Rovar rovarúj){
        rovarok.add(rovarúj);
    }
    public void torol(Rovar regi){
        rovarok.remove(regi);
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
    public void lepes(Grid kezdo, Grid cel, Move move) throws InvalidMoveException {
        boolean OK=false;
        for (Rovar r : rovarok) {
            if(r.getPosition()==kezdo){
                switch (move){
                    case Rovar_vag, Rovar_eszik -> {
                        r.consume();
                        OK=true;
                    }
                    case Rovar_mozog -> {
                        r.move(cel);
                        OK=true;
                    }
                    default->{}
                }
            }
        }
        if(!OK){
            throw new InvalidMoveException("Nincs olyan rovar a kezdő griden.",kezdo,cel,move);
        }
    }

    @Override
    public int getPoints() {
        return szerzettTapanyag;
    }
}
