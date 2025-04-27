package model.players;

import model.enums.Move;
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

    public Rovarasz(String nev) {
        super(nev);
        rovarok=new ArrayList<>();
        szerzettTapanyag=0;
    }
    
    public void hozzaAd(Rovar rovarúj){
        rovarok.add(rovarúj);
    }
    public void torol(Rovar regi){
        rovarok.remove(regi);
    }
    public void tapanyagHozzaad(int ujSzerzettTapanyag){szerzettTapanyag+=ujSzerzettTapanyag;}

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
       var rovar=rovarok.stream().filter(x->x.isAt(kezdo)).findFirst();

       if(rovar.isPresent()){
           Rovar act=rovar.get();
           switch (move){
               case Rovar_vag -> act.vag();
               case Rovar_eszik -> act.eszik();
               case Rovar_mozog -> act.mozog(cel);
           }
       }
    }
    @Override
    public String mentes(){
        return nev + " ; " + Integer.toString(melyik) + " ; " + Integer.toString(szerzettTapanyag) + " ; " + "Rovarasz";
    }

    @Override
    public int getPoints() {
        return szerzettTapanyag;
    }

    public void setTapanyagok(int s){
        szerzettTapanyag = s;
    }
}
