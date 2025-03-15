package model.players;

import java.util.List;

import interfaces.IGombasz;
import model.enums.Move;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Spora;
import model.grid.Grid;

public class Gombasz extends Jatekos implements IGombasz {
    int kinottGombatest;
    List<Spora> sporak;
    List<Fonal> fonalak;
    List<GombaTest> gombaTestek;

    public Gombasz(List<Spora> s, List<Fonal> f, List<GombaTest> g){
        sporak = s;
        fonalak = f;
        gombaTestek = g;
    }
    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) {
        
    }
    public void addTest(GombaTest g){
        kinottGombatest++;
        gombaTestek.add(g);
    }
    public void addFonal(Fonal f){
        fonalak.add(f);
    }
    public List<Spora> getSporas(){return sporak;}
}
