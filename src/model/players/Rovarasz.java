package model.players;

import static model.enums.Move.*;

import java.util.ArrayList;
import java.util.List;

import model.enums.Move;
import model.exceptions.InvalidMoveException;
import model.gameobjects.Rovar;
import model.grid.Grid;
import model.grid.TektonElem;

public class Rovarasz extends Jatekos {
    int szerzettTapanyag;
    List<Rovar> rovarok;

    @Override
    public int getPoints() {return szerzettTapanyag;}
    public void setTapanyagok(int s){szerzettTapanyag = s;}
    public List<Rovar> getRovarok(){return rovarok;}
    /**
     * Letrehozza a rovaraszt
     * @param grid Rovaranak kezdo mezoje
     * @param nev Identifikacios nev
     */
    public Rovarasz(TektonElem grid, String nev) {
        super(nev);
        rovarok=new ArrayList<>();
        firstMove(grid);
        szerzettTapanyag=0;
    }

    public Rovarasz(String nev) {
        super(nev);
        rovarok=new ArrayList<>();
        szerzettTapanyag=0;
    }
    
    public void hozzaAd(Rovar newRovar){
        rovarok.add(newRovar);
    }
    public void torol(Rovar regi){
        rovarok.remove(regi);
    }
    public void tapanyagHozzaad(int ujSzerzettTapanyag){szerzettTapanyag+=ujSzerzettTapanyag;}

    public Rovarasz(List<Rovar> r){
        super("");
        rovarok= r;}

    private void firstMove(TektonElem from){
        rovarok.add(new Rovar(from,this));
    }

    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) throws InvalidMoveException {
       var rovar=rovarok.stream().filter(x->x.isAt(kezdo)).findFirst();

        if(move==Move.Kezdo_lepes){
            if(kezdo instanceof TektonElem e) firstMove(e);
            else throw new InvalidMoveException("Csak TektonElemre lehet lerakni gombatestet!",kezdo,cel,move);
            return;
        }
       if(rovar.isPresent()){
           Rovar act=rovar.get();
           switch (move){
               case Rovar_vag -> act.vag(cel);
               case Rovar_eszik -> act.eszik(cel);
               case Rovar_mozog -> act.mozog(cel);
               default -> throw new InvalidMoveException("Hibas move, "+move.name(),kezdo,cel,move);
           }
       }
       else{
           throw new InvalidMoveException("Hibas kezdo grid, "+move.name(),kezdo,cel,move);
       }
    }
    @Override
    public String mentes(){return nev + " ; " + melyik + " ; " + szerzettTapanyag + " ; " + "Rovarasz";}

    @Override
    public Move[] getMoveTypes() {
        List<Move> arr=new ArrayList<>(List.of(Rovar_mozog, Rovar_eszik, Rovar_vag));
        if(rovarok.isEmpty())
            arr.add(Kezdo_lepes);
        return arr.toArray(new Move[arr.size()]);
    }

}
